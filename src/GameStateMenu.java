import javax.swing.*;
import java.awt.*;
import java.util.*;
//this is the menu gamestate. the user selects the difficulty using arrow keys, and once done presses RETURN
//we draw stuff accordingly on the screen. this is as simple as it gets.
public class GameStateMenu extends GameState{
	Deque<String> m_difficulties;
	public GameStateMenu(){
		m_difficulties = new LinkedList<String>();
		m_difficulties.add("Easy");
		m_difficulties.add("Medium");
		m_difficulties.add("Hard");
		m_difficulties.add("Insane");
	}
	public void onEvent(EventType e){
		switch(e){
			case LEFT:
				m_difficulties.addFirst(m_difficulties.removeLast());
				break;
			case RIGHT:
				m_difficulties.addLast(m_difficulties.removeFirst());
				break;
			case ENTER:
					switch(m_difficulties.getFirst()){
						case "Easy":
							Pentetris.startGame(0);
							break;
						case "Medium":
							Pentetris.startGame(1);
							break;
						case "Hard":
							Pentetris.startGame(2);
							break;
						case "Insane":
							Pentetris.startGame(3);
							break;
					}
				break;
			default:

				break;
		}

		//Because when the difficulty is changed, the text on the screen must be also re-drawn. In order that to happen, we must invalidate the current one by calling reValidate() on our game panel
		Pentetris.revalidate();
	}
	public void onThink(){
		//For this game this is where the logic happens

	}
	public void paint(Graphics g){
        g.drawString("(Use arrow keys to choose your difficulty. RETURN to start.)",100,350);

        g.setColor(Color.BLACK);

 
        Font newFont = g.getFont().deriveFont(g.getFont().getSize() * 3f);
        g.setFont(newFont);

        g.drawString("PENTETRIS",100,100);//hope this does not slip through to the final release and presentation lol

        g.drawString("DIFFICULTY:",100,150);

        g.drawString(m_difficulties.getFirst(),100,200);
	}


}