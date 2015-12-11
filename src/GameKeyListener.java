/**
 * Authors: Kalli Buchanan
 *		   Kamil Bujnarowski
 *		   Aleksas Kateiva
 *		   Daniel Mihăltan
 *		   Alexander Steckelberg
 *		   Carla Wrede
 *
 * Version: 3
 * 
 * Date:    5 December 2015
 *
 * Summary: Listen to events and pass them to the GameStates when appropriate
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameKeyListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				Pentetris.onEvent(EventType.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				Pentetris.onEvent(EventType.RIGHT);
				break;
			case KeyEvent.VK_UP:
				Pentetris.onEvent(EventType.UP);
				break;
			case KeyEvent.VK_DOWN:
				Pentetris.onEvent(EventType.DOWN);
				break;
			case KeyEvent.VK_ENTER:
				Pentetris.onEvent(EventType.ENTER);
				break;
			case KeyEvent.VK_SPACE:
				Pentetris.onEvent(EventType.SPACE);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}