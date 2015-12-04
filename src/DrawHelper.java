import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Simplifies some of the drawing tasks
 */

public class DrawHelper {
    /**
     * A method to draw center-aligned strings simple and easy
     * @param g        The graphics object
     * @param string   String to be drawn
     * @param x        X position of the string's center
     * @param y        Y position of the string
     * @param size     Font size
     */
    public static void drawString(Graphics g, String string, int x, int y, float size){

        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        Font newFont = g.getFont().deriveFont(oldFont.getSize() * size);
        g.setFont(newFont);

        int strlen = (int)g.getFontMetrics().getStringBounds(string, g).getWidth();
        g.drawString(string, x - strlen/2, y);

        g.setFont(oldFont);
    }

    /**
     * A method to draw Pentomino objects on the screen
     * @param g         The graphics object
     * @param pentomino The pentomino object
     * @param x         X position of where the pentomino has to be drawn
     * @param y         Y position of where the pentomino has to be drawn
     * @param cellSize  Size of each pentomino's square
     */
    public static void drawPentomino(Graphics g, Pentomino pentomino, int x, int y, int cellSize){
        for(int i = 0; i < 5; i ++){
            for(int j = 0; j < 5; j++){
                g.setColor(pentomino.getColor());
                if(pentomino.getMatrix()[i][j] != 0){
                    g.fillRect(x  + j*cellSize, y + i*cellSize - 50, cellSize, cellSize);
                }
                g.setColor(Color.BLACK);
            }
        }
    }
}
