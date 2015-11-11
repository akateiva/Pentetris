import javax.swing.*;
import java.awt.*;

public class Pentetris {
    private static GameState m_gameState;
    private static GamePanel m_gamePanel;
    private static java.util.Timer m_timer;
    private static int m_timerDelay;

    public static void main(String[] args) {

        m_gameState = new GameStateMenu();

        m_timer = new java.util.Timer();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("PENTETRIS");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(750,500);

                m_gamePanel = new GamePanel();
                f.add(m_gamePanel);

                f.pack();
                f.setVisible(true);
            }
        });
    }
    public static void forceThink(){
        m_timer.cancel();
        m_timer.purge();
        m_timer = new java.util.Timer();
        m_timer.schedule(new ThinkTask(), 0, m_timerDelay);
        revalidate();
    }
    public static void onEvent(EventType e){
        m_gameState.onEvent(e);
    }
    public static void onThink(){
        m_gameState.onThink();
    }
    public static void paint(Graphics g){
        m_gameState.paint(g);
    }
    public static void revalidate(){
        m_gamePanel.revalidate();
        m_gamePanel.repaint();
    }
    public static void startMenu(){
        m_gameState =  new GameStateMenu();
    }
    public static void startGame(int difficulty){
        m_gameState = new GameStateGame(difficulty);
        switch(difficulty){
            case 0:
                m_timerDelay = 500;
            break;
            case 1:
                m_timerDelay = 300;
            break;
            case 2:
                m_timerDelay = 200;
            break;
            case 3:
                m_timerDelay = 100;
            break;
        }
        forceThink();
    }
}