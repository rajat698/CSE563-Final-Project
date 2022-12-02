import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Viewer {


    public static void aboutDialogBox() {
        
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "About");
        JLabel label = new JLabel("Team Members: Rajat Yadav, Abhishek Massetty, Sahil Jambhulkar, Konark Bhad, Vinita Maloo, Harshita Jain");
        dialog.add(label);

        dialog.setSize(900, 100);
        dialog.setVisible(true);
    }
}
