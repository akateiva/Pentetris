import java.awt.*;
import java.util.*;

/**
 * This class handles the starting screen, during which the player chooses his difficulty or whether he wants to see the bot play
 */
public class GameStateMenu extends GameState{
	Deque<String> m_difficulties;
	public GameStateMenu(){
		m_difficulties = new LinkedList<>();
		m_difficulties.add("Easy");
		m_difficulties.add("Medium");
		m_difficulties.add("Hard");
		m_difficulties.add("Insane");
		m_difficulties.add("AI");
		m_difficulties.add("Simulation");
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
					case "AI":
						Pentetris.startGame(-1);
						break;
					case "Simulation":
						Pentetris.startSimulation();
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

	}
	public void paint(Graphics g){

		DrawHelper.drawString(g, "PENTETRIS", 375, 100, 4);
		DrawHelper.drawString(g, "MODE:", 375, 150, 2);
		DrawHelper.drawString(g, m_difficulties.getFirst(), 375, 200, 2);
		DrawHelper.drawString(g, "(Use arrow keys to choose your difficulty. RETURN to start.)", 375, 350, 1);

	}


}