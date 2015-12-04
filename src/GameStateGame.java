import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This high-level class handles most of the game code
 */
public class GameStateGame extends GameState{
	int m_difficulty;

	final int m_boardWidth = 10;
	final int m_boardHeigth = 22;
	int m_board[][];

	boolean m_finished;
	int m_score;


	Pentomino m_nextPentomino;
	Pentomino m_activePentomino;

	/**
	 * Initializes the gamestate
	 * @param difficulty The difficulty that was selected in the menu
     */
	public GameStateGame(int difficulty) {
		m_score = 0;
		m_difficulty = difficulty;
		m_finished = false;
		m_board = new int[m_boardHeigth][m_boardWidth];
		m_nextPentomino = randomPentomino();
		m_activePentomino = randomPentomino();
	}

	/**
	 * Whenever the game ending condition has been detected, this is called
	 */
	private void gameOver(){
		m_finished = true;
	}

	/**
	 * @param line The number of the line within the board
	 * @return True if the given line is filled, false if its not
     */
	private boolean lineFilled(int line){
		for(int i = 0; i < m_boardWidth; i++){
			if(m_board[line][i] == 0){
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes the given line from the board and updates the score
	 * @param line The number of the line within the board
     */
	private void clearLine(int line){
		m_score++;
		for(int i = line; i > 0; i--){
			System.arraycopy(m_board, i-1, m_board, i, 1);
		}
		m_board[0] = new int[m_boardWidth];
	}

	/**
	 * This method checks all rows from top to bottom looking for filled lines and clearing them
	 */
	private void checkLines(){
		//go thru all possible rows from top to bottom
		for(int i = 0; i < m_boardHeigth; i++){
			if(lineFilled(i)){
				clearLine(i);
			}
		}
	}

	/**
	 * Generates a random pentomino
	 * @return Pentomino
     */
	private Pentomino randomPentomino(){
		Pentomino tmp;
		Random ran = new Random();
		tmp = new Pentomino(ran.nextInt(12), ran.nextBoolean());
		tmp.setX(4);
		tmp.setY(0);
		return tmp;
	}

	/**
	 * Every event that has been detected will be passed to this method
	 * @param e The type of key press
	 * @see EventType
     */
	public void onEvent(EventType e){
		//if the difficulty is AI, ignore player input
		if(m_difficulty == -1)
			return;

		switch(e){
			case UP:
				m_activePentomino.rotateCW();
				if(!m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY())){
					m_activePentomino.rotateCCW();
				}
				break;
			case LEFT:
				if(m_activePentomino.getX() - 1 >= 0 && m_activePentomino.fits(m_board, m_activePentomino.getX() - 1, m_activePentomino.getY())){
					m_activePentomino.setX(m_activePentomino.getX() - 1);
				}
				break;
			case RIGHT:
				if(m_activePentomino.fits(m_board, m_activePentomino.getX() + 1, m_activePentomino.getY())){
					m_activePentomino.setX(m_activePentomino.getX() + 1);
				}
				break;
			case DOWN:
				Pentetris.forceThink();
				break;
			case ENTER:
				if(m_finished){
					Pentetris.startMenu();
				}
				break;
			default:

				break;
		}
		Pentetris.revalidate();
	}

	/**
	 * This is called by the application at a fixed interval ( determined by the difficulty that was selected.
	 */
	public void onThink(){
		if(m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY() + 1)){
			m_activePentomino.setY(m_activePentomino.getY() + 1);
		}else{
			m_activePentomino.insert(m_board);
			if(!m_finished){
				m_activePentomino = m_nextPentomino;
				m_nextPentomino = randomPentomino();
				if(!m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY())){
					gameOver();
				}
			}
		}
		checkLines();
		Pentetris.revalidate();
	}

	/**
	 * Every time something is changed on screen, this is called to re-draw the graphics
	 * @param g	The graphics object
     */
	public void paint(Graphics g){
		final int cellSize = 25;

		//Draw the board with locked-in pentominoes
		for(int i = 0; i != m_boardHeigth; i++){
			for(int j = 0; j != m_boardWidth; j++){
				g.drawRect(250 + j*cellSize, i*cellSize - 50, cellSize, cellSize);
				if(m_board[i][j] != 0){
					g.setColor(new Color(m_board[i][j]));
				}else{
					g.setColor(Color.WHITE);
				}
				g.fillRect(250 + j*cellSize, i*cellSize - 50, cellSize, cellSize);
				g.setColor(Color.BLACK);
			}
		}


		DrawHelper.drawPentomino(g, m_activePentomino, 250 + m_activePentomino.getX()*cellSize, 0 + m_activePentomino.getY()*cellSize, cellSize);

		DrawHelper.drawString(g, "SCORE: " + m_score, 125, 150, 3);

		DrawHelper.drawString(g, "NEXT PENTOMINO", 125, 200, 1);
		DrawHelper.drawPentomino(g, m_nextPentomino, 100, 275, 10);

		if(m_finished) {
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRect(0, 0, 750, 500);
			DrawHelper.drawString(g, "GAME OVER", 375, 200, 4);
			DrawHelper.drawString(g, "YOUR SCORE: " + m_score, 375, 250, 2);
			DrawHelper.drawString(g, "press enter to return to the menu", 375, 300, 2);
		}
	}

}