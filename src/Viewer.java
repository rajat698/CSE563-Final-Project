import javax.swing.*;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JDialog;

/**
* This Viewer class consists of dialog box for team
* information.
*/
public class Viewer {

    /**
    * This method displays a dialog box consisting of team
    * information.
    */
    public static void aboutDialogBox() {
        JDialog dialogBox = new JDialog(new JFrame(), "About");

        JLabel label1 = new JLabel("Rajat Yadav", SwingConstants.CENTER);
        JLabel label2 = new JLabel("Abhishek Massetty", SwingConstants.CENTER);
        JLabel label3 = new JLabel("Sahil Jambhulkar", SwingConstants.CENTER);
        JLabel label4 = new JLabel("Konark Bhad", SwingConstants.CENTER);
        JLabel label5 = new JLabel("Vinita Maloo", SwingConstants.CENTER);
        JLabel label6 = new JLabel("Harshita Jain", SwingConstants.CENTER);


        dialogBox.add(label1);
        dialogBox.add(label2);
        dialogBox.add(label3);
        dialogBox.add(label4);
        dialogBox.add(label5);
        dialogBox.add(label6);

        dialogBox.setLayout(new GridLayout(2,3));
        dialogBox.setLocationRelativeTo(null);
        dialogBox.setSize(600, 200);
        dialogBox.setVisible(true);
        dialogBox.setAlwaysOnTop(true);
    }
}
