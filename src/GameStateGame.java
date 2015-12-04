import java.awt.*;
import java.util.*;

/**
 * This high-level class handles most of the game code
 */
public class GameStateGame extends GameState{
	int m_difficulty;

	final int m_boardWidth = 10;
	final int m_boardHeight = 22;
	GameBoard m_gameBoard;

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

		m_nextPentomino = randomPentomino();
		m_activePentomino = randomPentomino();

		m_gameBoard = new GameBoard(m_boardWidth, m_boardHeight);
		m_gameBoard.setNextPentomino(m_nextPentomino);
		m_gameBoard.setActivePentomino(m_activePentomino);
	}

	/**
	 * Whenever the game ending condition has been detected, this is called
	 */
	private void gameOver(){
		m_finished = true;
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
				m_gameBoard.rotateActive();
				break;
			case LEFT:
				m_gameBoard.moveActiveLeft();
				break;
			case RIGHT:
				m_gameBoard.moveActiveRight();
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
		m_gameBoard.onThink();

		//Check if the nextPentomino has been used
		if(m_gameBoard.getNextPentomino() == null){
			//If the board has locked in the active pentomino update our record and generate the nextPentomino
			m_activePentomino = m_nextPentomino;
			m_nextPentomino = randomPentomino();
			m_gameBoard.setNextPentomino(m_nextPentomino);
		}
		Pentetris.revalidate();
	}

	/**
	 * Every time something is changed on screen, this is called to re-draw the graphics
	 * @param g	The graphics object
     */
	public void paint(Graphics g){
		final int cellSize = 25;

		//TODO: FIX THE M_. M_BOARD IS NOT A MEMBER VARIABLE!!
		//TODO: maybe even move this whole thing to DrawHelper
		int[][] m_board = m_gameBoard.getBoardMatrix();
		//Draw the board with locked-in pentominoes
		for(int i = 0; i != m_boardHeight; i++){
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