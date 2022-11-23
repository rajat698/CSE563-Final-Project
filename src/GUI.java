import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class GUI extends JFrame{


    public GUI(){
        loadGUI();
    }

    public void loadGUI() {
        setTitle("CSE563 Final Project");
        setBounds(300, 90, 600, 600   );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        // Container container = getContentPane();
        // container.setLayout(null);

        JMenuItem loadRoster = new JMenuItem("Load a Roster");
        JMenuItem addAttendance = new JMenuItem("Add Attendance");
        JMenuItem saveRoster = new JMenuItem("Save");
        JMenuItem plotData = new JMenuItem("Plot Data");

        JMenu file = new JMenu("File");
        file.add(loadRoster);
        file.add(addAttendance);
        file.add(saveRoster);
        file.add(plotData);

        JMenu about = new JMenu("About");
        JMenuBar menuBar = new JMenuBar();
        
        menuBar.add(file);
        menuBar.add(about);

        add(menuBar);
        setJMenuBar(menuBar);

        this.setVisible(true);
    }


}