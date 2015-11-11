import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class GamePanel extends JPanel {

    public GamePanel() {
        KeyListener listener = new GameKeyListener();
        addKeyListener(listener);
        setFocusable(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(750,500);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      
        Pentetris.paint(g);
    }  
}
