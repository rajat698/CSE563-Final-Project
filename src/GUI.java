import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.awt.*;
import java.util.Map;

public class GUI extends JFrame implements ActionListener{

    JMenuBar menuBar;
    JMenuItem loadRoster;
    JMenuItem addAttendance;
    JMenuItem saveRoster;
    JMenuItem plotData;
    JMenu file;
    JMenuItem about;
    boolean rosterAdded = false;
    List<Student> studentRoster = new ArrayList<>();
    JPanel panel = new JPanel();
    JTable table = new JTable();
    LinkedHashMap<String, Integer> columnMap = new LinkedHashMap<>();
    Attendance attendance = new Attendance(null);

    public GUI(){
        loadGUI();
    }

    public void loadGUI() {

        setTitle("CSE563 Final Project");
        setBounds(300, 90, 600, 600   );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        loadRoster = new JMenuItem("Load a Roster");
        loadRoster.addActionListener(this);
        addAttendance = new JMenuItem("Add Attendance");
        addAttendance.addActionListener(this);
        saveRoster = new JMenuItem("Save");
        plotData = new JMenuItem("Plot Data");

        file = new JMenu("File");
        file.add(loadRoster);
        file.add(addAttendance);
        file.add(saveRoster);
        file.add(plotData);

        about = new JMenuItem("About");
        about.addActionListener(this);
        menuBar = new JMenuBar();
        
        menuBar.add(file);
        menuBar.add(about);

        this.add(menuBar);
        this.setJMenuBar(menuBar);

        this.setVisible(true);    
    }
        

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == about) {
            Viewer.aboutDialogBox();
        }
        // Action listener for adding Roster data
        else if(e.getSource() == loadRoster) {
            Roster roster = new Roster();
            studentRoster = roster.loadRosterData(table);
            if(studentRoster != null) {
                rosterAdded = true;
                panel.removeAll();
                panel.setLayout(new BorderLayout());
                Dimension screen = new Dimension();
                screen.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                panel.setSize(screen);
                panel.setBorder(LineBorder.createBlackLineBorder());   
                add(panel);

                String[] columnNames = { "ID", "First Name", "Last Name","ASURITE"};
                JScrollPane sp = roster.visualizeRoster(studentRoster, columnNames.length, columnNames, columnMap);
                attendance = new Attendance(studentRoster);

                panel.add(sp);
                panel.updateUI();
            }
            else {
                panel.removeAll(); 
                panel.updateUI();
            }
        } // Action listener for adding attendance data
        else if(e.getSource() == addAttendance && rosterAdded) {
            try {
                attendance.loadAttendanceData(columnMap);

                panel.removeAll();
                panel.setLayout(new BorderLayout());
                Dimension screen = new Dimension();
                screen.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                panel.setSize(screen);
                panel.setBorder(LineBorder.createBlackLineBorder());
                add(panel);

                String[] columnNames = new String[4 + columnMap.size()];
                columnNames[0] = "ID";
                columnNames[1] = "First Name";
                columnNames[2] = "First Name";
                columnNames[3] = "ASURITE";
                int i = 4;
                for (String str : columnMap.keySet())
                    columnNames[i++] = str;

                Roster roster = new Roster();
                JScrollPane sp = roster.visualizeRoster(studentRoster, columnNames.length, columnNames, columnMap);
                panel.add(sp);
                panel.updateUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
