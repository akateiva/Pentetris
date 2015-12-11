/**
 * Authors: Kalli Buchanan
 *		   Kamil Bujnarowski
 *		   Aleksas Kateiva
 *		   Daniel Mihăltan
 *		   Alexander Steckelberg
 *		   Carla Wrede
 *
 * Version: 2
 * 
 * Date:    4 December 2015
 *
 * Summary: Abstract clas onto which GameStates are built
 *
 */

import javax.swing.*;
import java.awt.*;

public class GameState{
	public GameState(){

	}

	/**
	 * Every time an event is detected, this is called
	 * @param e EventType
     */
	public void onEvent(EventType e){

	}

	/**
	 * This is called by the internal timer that is determined by tge selected difficulty
	 */
	public void onThink(){

	}

	/**
	 * This is called every time the screen has to be updated
	 * @param g The graphics object
     */
	public void paint(Graphics g){

	}


}