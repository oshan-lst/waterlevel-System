import javax.swing.*;
import java.awt.*;
import java.util.Random;

// 1. Superclass 
class WaterLevelObserver {
    public void update(int waterLevel) {
        // Base implementation
    }
}

// 2. Alarm GUI Device 
class Alarm extends WaterLevelObserver {
    private String id;
    private JFrame frame;
    private JLabel statusLabel;

    Alarm(String id) {
        this.id = id;
        
        // JFrame for Alarm
        frame = new JFrame("Alarm Device [" + id + "]");
        frame.setSize(280, 150);
        frame.setLocation(100, 100);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Alarm Control Unit", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 13));

        statusLabel = new JLabel(id + " --> Alarm OFF ", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(46, 204, 113));
        statusLabel.setForeground(Color.WHITE);

        panel.add(title, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void update(int waterLevel) {
        if (waterLevel > 50) {
            statusLabel.setText(id + " Alarm ON");
            statusLabel.setBackground(new Color(231, 76, 60)); // Red
        } else {
            statusLabel.setText(id + " Alarm OFF");
            statusLabel.setBackground(new Color(46, 204, 113)); // Green
        }
    }
}

// 3. Display GUI Device 
class Display extends WaterLevelObserver {
    private String id;
    private JFrame frame;
    private JLabel displayLabel;

    Display(String id) {
        this.id = id;
        
        // Creating separate JFrame for Display
        frame = new JFrame("Display Device [" + id + "]");
        frame.setSize(280, 150);
        frame.setLocation(400, 100);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Water Level Display Unit", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 13));

        displayLabel = new JLabel(id + " --> Water Level : 0%", JLabel.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        displayLabel.setForeground(new Color(41, 128, 185));

        panel.add(title, BorderLayout.NORTH);
        panel.add(displayLabel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void update(int waterLevel) {
        displayLabel.setText(id + " --> Water Level : " + waterLevel + "%");
    }
}

// 4. SMS Sender GUI Device
class SMSSender extends WaterLevelObserver {
    private String id;
    private JFrame frame;
    private JTextArea smsArea;

    SMSSender(String id) {
        this.id = id;
        
        //  JFrame for SMS Sender
        frame = new JFrame("SMS Sender Device [" + id + "]");
        frame.setSize(320, 180);
        frame.setLocation(700, 100);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("SMS Transmission Log", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 13));

        smsArea = new JTextArea();
        smsArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(smsArea);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void update(int waterLevel) {
        smsArea.append(id + " --> Sending SMS... Level: " + waterLevel + "%\n");
        smsArea.setCaretPosition(smsArea.getDocument().getLength());
    }
}

//splitter class
class Splitter extends WaterLevelObserver{
	private String id;
    private JFrame frame;
    private JLabel statusLabel;
    
    Splitter(String id) {
        this.id = id;
       
		frame = new JFrame("Splitter Device [" + id + "]");
        frame.setSize(280, 150);
        frame.setLocation(500, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Splitter Control Unit", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 13));

        statusLabel = new JLabel(id + " Splitter OFF ", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(46, 204, 113));
        statusLabel.setForeground(Color.WHITE);

        panel.add(title, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true); 
      
	}
	
	@Override
    public void update(int waterLevel) {
        if (waterLevel >= 75) {
            statusLabel.setText(id + " Splitter ON ");
            statusLabel.setBackground(new Color(231, 76, 60)); // Red
            JOptionPane.showMessageDialog(null, "Warninng! Splitter Open","High Water Level",JOptionPane.WARNING_MESSAGE);
        } else {
            statusLabel.setText(id + " Splitter OFF");
            statusLabel.setBackground(new Color(46, 204, 113)); // Green
        }
    }
    
}

// 5. Control Room 
class ControlRoom {
    private WaterLevelObserver[] waterLevelObserverArray = new WaterLevelObserver[0];
    private int waterLevel;

    public void addWaterLevelObserver(WaterLevelObserver waterLevelObserver) {
        extendArray();
        waterLevelObserverArray[waterLevelObserverArray.length - 1] = waterLevelObserver;
    }

    public void setWaterLevel(int waterLevel) {
        if (this.waterLevel != waterLevel) {
            this.waterLevel = waterLevel;
            notifyDevices();
        }
    }

    private void notifyDevices() {
        for (WaterLevelObserver ob : waterLevelObserverArray) {
            ob.update(waterLevel);
        }
    }

    private void extendArray() {
        WaterLevelObserver[] tempWaterLevelObserverArray = new WaterLevelObserver[waterLevelObserverArray.length + 1];
        for (int i = 0; i < waterLevelObserverArray.length; i++) {
            tempWaterLevelObserverArray[i] = waterLevelObserverArray[i];
        }
        waterLevelObserverArray = tempWaterLevelObserverArray;
    }
}

// 6. Main Control Window 
class ControlStationGUI extends JFrame {
    private ControlRoom controlRoom;
    private JProgressBar waterProgressBar;

    public ControlStationGUI(ControlRoom controlRoom) {
        this.controlRoom = controlRoom;

        setTitle("Main Control Station");
        setSize(320, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 320);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Water Progress Controller", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createTitledBorder("water level progress"));
        centerPanel.setOpaque(false);

        // Vertical Blue Progress Bar
        waterProgressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        waterProgressBar.setValue(40);
        waterProgressBar.setStringPainted(true);
        waterProgressBar.setForeground(new Color(52, 152, 219)); // Blue theme
        waterProgressBar.setFont(new Font("Arial", Font.BOLD, 12));

        // Manual Slider to control water level
        JSlider waterSlider = new JSlider(JSlider.VERTICAL, 0, 100, 40);
        waterSlider.setMajorTickSpacing(10);
        waterSlider.setPaintTicks(true);
        waterSlider.setPaintLabels(true);
        waterSlider.setOpaque(false);

        JPanel progressControlPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        progressControlPanel.setOpaque(false);
        progressControlPanel.add(waterProgressBar);
        progressControlPanel.add(waterSlider);

        centerPanel.add(progressControlPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Slider 
        waterSlider.addChangeListener(e -> {
            int level = waterSlider.getValue();
            waterProgressBar.setValue(level);
            controlRoom.setWaterLevel(level);
        });
    }

    public void updateProgress(int level) {
        waterProgressBar.setValue(level);
    }
}

// 7. Demo Class 
public class Demo {
    public static void main(String[] args) {
        ControlRoom controlRoom = new ControlRoom();
        
        // Registering devices (will create separate JFrames automatically)
        controlRoom.addWaterLevelObserver(new Alarm("A0001"));
        controlRoom.addWaterLevelObserver(new Display("D0001"));
        controlRoom.addWaterLevelObserver(new SMSSender("SM0001"));
        controlRoom.addWaterLevelObserver(new Splitter("SP0001"));
        
        //~ controlRoom.addWaterLevelObserver(new Alarm("A0002"));
        //~ controlRoom.addWaterLevelObserver(new Display("D0002"));
        //~ controlRoom.addWaterLevelObserver(new SMSSender("SM0002"));
        
        // Launching Main GUI 
        ControlStationGUI stationGUI = new ControlStationGUI(controlRoom);
        stationGUI.setVisible(true);
        
        Random r = new Random();
        while (true) {
            int waterLevel = r.nextInt(101); // 0 to 100
            controlRoom.setWaterLevel(waterLevel);
            stationGUI.updateProgress(waterLevel); 
           
           // System.out.println();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
