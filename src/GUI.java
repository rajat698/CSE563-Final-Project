import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import java.util.Iterator;
import java.util.List;
import java.util.LinkedHashMap;
import java.awt.*;
import java.util.Map;

/**
* This GUI class loads the graphical user interface for the
user. It consists of Menu bar, Menu Items and action listeners.
*/
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
    String[][] displayData;
    String[] headers;
    boolean drawPlot = false;

    public GUI(){
        loadGUI();
    }
    
    /**
     * This method draws the bar plot of attendance.
     * @param g object for Graphics class of java.awt.Graphics
     */

    @Override
    public void paint(Graphics g) {   
        super.paint(g);
        if(drawPlot) {

            Iterator Iterator = attendance.getDatewiseStudents().entrySet().iterator();
            
            System.out.println(attendance.getDatewiseStudents());

            if (attendance.getDatewiseStudents().entrySet().size() != 0) {
                int x = 140;
                int y = 300;
                while (Iterator.hasNext()) {

                    Map.Entry mapElement = (Map.Entry)Iterator.next();

                    int height = (int) mapElement.getValue();

                    g.fillRect (x + 30, 500 - height * 2, 30, height * 2);

                    g.drawString((String)(mapElement.getKey()), x, 520);
                    g.drawString(Integer.toString((int)mapElement.getValue()),120, 507 - height * 2);

                    x = x + 100;
                    y = y - height;

            }
        }
            else {
                g.drawString("Please add attndance first", 100, 200);
            }

        }
    }
    /**
     * Method to draw a base UI consisting of menu bar and its items
     */
    
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
        saveRoster.addActionListener(this);
        plotData = new JMenuItem("Plot Data");
        plotData.addActionListener(this);

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
        this.setLocationRelativeTo(null);
    }
        
    /**
     * This method controls the menu items and their respective functionalities.
     * @param e Object to control action performed by the menu items
     */
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
                panel.setBorder(LineBorder.createBlackLineBorder());   
                add(panel);

                String[] columnNames = { "ID", "First Name", "Last Name","ASURITE"};
                JScrollPane sp = roster.visualizeRoster(studentRoster, columnNames.length, columnNames, columnMap);
                attendance = new Attendance(studentRoster);

                panel.add(sp);
                panel.updateUI();
            }
        } // Action listener for adding attendance data
        else if(e.getSource() == addAttendance && rosterAdded) {
            try {
                attendance.loadAttendanceData(columnMap);

                panel.removeAll();
                panel.setLayout(new BorderLayout());
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
                String[][] data = roster.getData();
                displayData = data;
                String[] h = roster.getHeaders();
                headers = h;
                panel.add(sp);
                panel.updateUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /** 
         * Saves headers as well as its data in the CSV file.
         * If roster is not added, it will show error message.
         */
        else if(e.getSource() == saveRoster) {
            
            if (rosterAdded == false) {
                JPanel jPanleOBject = new JPanel();   
                JLabel errorMessage = new JLabel("Error : Load the roster first");
                jPanleOBject.add(errorMessage);
                
                JDialog displayDialog = new JDialog();
                displayDialog.add(jPanleOBject);
                displayDialog.setVisible(true);
                displayDialog.setTitle("ERROR");
                displayDialog.setSize(500, 100);
                displayDialog.setAlwaysOnTop(true);
                displayDialog.setBackground(getForeground());
                displayDialog.setName("ERROR WINDOW");
                displayDialog.setLocation(350, 300);
            } else {
                Save save = new Save();
                save.saveFile(displayData, headers);
            } 

        }
        // Displays a bar plot for the student attendance data
        else if(e.getSource() == plotData) {
            panel.removeAll();
            drawPlot = true;
            repaint();
        }
    }
    
}