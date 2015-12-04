import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawHelper {

    public static void drawString(Graphics g, String string, int x, int y, float size){

        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        Font newFont = g.getFont().deriveFont(oldFont.getSize() * size);
        g.setFont(newFont);

        int strlen = (int)g.getFontMetrics().getStringBounds(string, g).getWidth();
        g.drawString(string, x - strlen/2, y);

        g.setFont(oldFont);
    }
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
