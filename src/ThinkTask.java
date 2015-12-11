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
 * Date:    8 December 2015
 *
 * Summary: This clas calls the onThink method of any GameState
 *
 */

import java.util.*;

class ThinkTask extends TimerTask{
	public void run() {
		Pentetris.onThink();
	}
}