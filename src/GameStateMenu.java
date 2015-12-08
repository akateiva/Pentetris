import java.awt.*;
import java.util.*;

/**
 * This class handles the starting screen, during which the player chooses his difficulty or whether he wants to see the bot play
 */
public class GameStateMenu extends GameState{
	LinkedList<String> m_menuOptions;
	int m_menuCursor;
	public GameStateMenu(){
		m_menuOptions = new LinkedList<>();
		setMenu(0);
	}
	public void setMenu(int menu){
		m_menuCursor = 0;
		switch(menu){
			case 0:
				m_menuOptions.clear();
				m_menuOptions.add("Play");
				m_menuOptions.add("AI Player");
				m_menuOptions.add("Simulation");
				m_menuOptions.add("Quit");
				break;
			case 1:
				m_menuOptions.clear();
				m_menuOptions.add("Easy");
				m_menuOptions.add("Medium");
				m_menuOptions.add("Hard");
				m_menuOptions.add("Insane");
				m_menuOptions.add("Back");
				break;
		}
	}
	public void onEvent(EventType e){
		switch(e){
			case UP:
				if(m_menuCursor == 0){
					m_menuCursor = m_menuOptions.size()-1;
				}else{
					m_menuCursor--;
				}
				break;
			case DOWN:
				if(m_menuCursor == m_menuOptions.size()-1){
					m_menuCursor = 0;
				}else{
					m_menuCursor++;
				}
				break;
			case ENTER:
				switch(m_menuOptions.get(m_menuCursor)){
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
					case "AI Player":
						Pentetris.startGame(-1);
						break;
					case "Simulation":
						Pentetris.startSimulation();
						break;
					case "Play":
						setMenu(1);
						break;
					case "Back":
						setMenu(0);
						break;
					default:
						break;
				}
				break;
			default:

				break;
		}

		Pentetris.revalidate();
	}
	public void onThink(){

	}
	public void paint(Graphics g){

		DrawHelper.drawString(g, "PENTETRIS", 375, 100, 4);
		for(int i = 0; i < m_menuOptions.size(); i++) {
			if(i == m_menuCursor){
				DrawHelper.drawString(g, m_menuOptions.get(i), 375, 150+35*i, 2, new Color(255,0,0));
			}else{
				DrawHelper.drawString(g, m_menuOptions.get(i), 375, 150+35*i, 2);
			}

		}
		DrawHelper.drawString(g, "(Use arrow keys to navigate the menu)", 375, 375, 1);

	}


}