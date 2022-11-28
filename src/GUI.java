import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener{

    JMenuBar menuBar;
    JMenuItem loadRoster;
    JMenuItem addAttendance;
    JMenuItem saveRoster;
    JMenuItem plotData;
    JMenu file;
    JMenuItem about;

    public GUI(){
        loadGUI();
    }

    public void loadGUI() {

        setTitle("CSE563 Final Project");
        setBounds(300, 90, 600, 600   );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        loadRoster = new JMenuItem("Load a Roster");
        addAttendance = new JMenuItem("Add Attendance");
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
    }

}