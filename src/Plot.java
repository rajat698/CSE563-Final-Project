import java.util.*;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Plot extends JFrame{

    public Plot() {}

    @Override
    public void paint(Graphics g) {   
    super.paint(g);
    g.fillRect (160, 100, 10, 200);
    g.fillRect (260, 200, 10, 100);
}
    
}
