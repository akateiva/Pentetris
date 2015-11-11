import javax.swing.*;
import java.awt.*;
//this is as abstract as a class can get. usually games have very different states
//IE the menu screen, the loading screen and the game screen itself
//each game state is responsible for itself, that is handling the events and drawing schtuff on screen
public class GameState{
	public GameState(){
		//Pretty much do nothing
	}
	public void onEvent(EventType e){
		//Here we poll the events ( key presses, etc.)
	}
	public void onThink(){
		//this is probably obsolete
	}
	public void paint(Graphics g){

	}


}