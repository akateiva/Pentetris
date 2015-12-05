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

	Pentomino m_nextPentomino;
	Pentomino m_activePentomino;

	/**
	 * Initializes the gamestate
	 * @param difficulty The difficulty that was selected in the menu
     */
	public GameStateGame(int difficulty) {
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
	 * This loop plays as an AI
	 */
	private void AILoop(){
		//Generate all the candidate boards
		GameBoard candidates[][][][] =  new GameBoard[m_boardWidth][m_boardWidth][4][4];

		for(int i = 0; i < m_boardWidth; i++) {
			for(int j = 0; j < m_boardWidth; j++) {
				for(int k = 0; k < 3; k++) {
					for(int l = 0; l < 3; l++) {
						//These horrible nested loops iterate through 2 turn deep games, trying every possible X position and rotation combination
						candidates[i][j][k][l] = m_gameBoard.deepCopy();
						Pentomino activePentomino = candidates[i][j][k][l].getActivePentomino();
						Pentomino nextPentomino = candidates[i][j][k][l].getNextPentomino();

						//

						//Set the combination's positions
						activePentomino.setX(i);
						nextPentomino.setX(j);

						//Apply the given amount of rotation actions

						for(int m = 0; m < k; m++){
							activePentomino.rotateCW();
						}
						for(int n = 0; n < l; n++){
							nextPentomino.rotateCW();
						}
						//If either of the pentominoes doesnt fit on the board after the following combinations, mark that candidate invalid
						if(activePentomino.fits(candidates[i][j][k][l].getBoardMatrix(), i, 0) ||
								nextPentomino.fits(candidates[i][j][k][l].getBoardMatrix(), j, 0)){

								//Drop the active pentomino and lock it in
								candidates[i][j][k][l].dropActive();
								candidates[i][j][k][l].insertActive();
								int scoreBeforeCheckingLines = candidates[i][j][k][l].getScore();
								candidates[i][j][k][l].checkLines();
								candidates[i][j][k][l].m_scoreDelta = candidates[i][j][k][l].getScore() - scoreBeforeCheckingLines;

								//By now the activePentomino has changed to the one that has been assigned as next
								//Drop the active pentomino and lock it in
								candidates[i][j][k][l].dropActive();
								candidates[i][j][k][l].insertActive();
						}else{
							candidates[i][j][k][l].invalidate();
						}
					}
				}
			}
		}

		//Now iterate through all the simulations of GameBoard and find the one with the highest heuristic value
		GameBoard mostRational = candidates[0][0][0][0];
		float mostRationalHeuristic = candidates[0][0][0][0].computeHeuristic();
		for(int i = 0; i < m_boardWidth; i++) {
			for (int j = 0; j < m_boardWidth; j++) {
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						float candidateHeuristic = candidates[i][j][k][l].computeHeuristic();
						if(candidateHeuristic > mostRationalHeuristic && candidates[i][j][k][l].isValid()){
							mostRational = candidates[i][j][k][l];
							mostRationalHeuristic = candidateHeuristic;
						}
					}
				}
			}
		}
		mostRational.checkLines();
		m_gameBoard = mostRational;
	}

	/**
	 * Every event that has been detected will be passed to this method
	 * @param e The type of key press
	 * @see EventType
     */
	public void onEvent(EventType e){
		//if the difficulty is AI, ignore player input
		if(m_difficulty == -1){
			if(m_finished && e == EventType.ENTER){
				Pentetris.startMenu();
				Pentetris.revalidate();
			}
			return;
		}


		switch(e){
			case UP:
				if(!m_finished) {
					m_gameBoard.rotateActive();
				}
				break;
			case LEFT:
				if(!m_finished) {
					m_gameBoard.moveActiveLeft();
				}
				break;
			case RIGHT:
				if(!m_finished) {
					m_gameBoard.moveActiveRight();
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
			case SPACE:
				if(!m_finished) {
					m_gameBoard.dropActive();
					Pentetris.forceThink();
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
		if(m_difficulty == -1 && !m_finished){
			AILoop();
			m_activePentomino = randomPentomino();
			m_nextPentomino = randomPentomino();
			m_gameBoard.setActivePentomino(m_activePentomino);
			m_gameBoard.setNextPentomino(m_nextPentomino);
			if(!m_activePentomino.fits(m_gameBoard.getBoardMatrix(), m_activePentomino.getX(), m_activePentomino.getY())){
				gameOver();
			}
			Pentetris.revalidate();
			return;
		}

		if(!m_finished) {
			m_gameBoard.onThink();
		}

		//Check if the nextPentomino has been used
		if(m_gameBoard.getNextPentomino() == null){
			//If the board has locked in the active pentomino update our record and generate the nextPentomino
			m_activePentomino = m_nextPentomino;
			//Check if it fits
			if(!m_activePentomino.fits(m_gameBoard.getBoardMatrix(), m_activePentomino.getX(), m_activePentomino.getY())){
				gameOver();
			}
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

		DrawHelper.drawString(g, "SCORE: " + m_gameBoard.getScore(), 125, 150, 3);

		DrawHelper.drawString(g, "NEXT PENTOMINO", 125, 200, 1);
		DrawHelper.drawPentomino(g, m_nextPentomino, 100, 275, 10);

		if(m_finished) {
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRect(0, 0, 750, 500);
			DrawHelper.drawString(g, "GAME OVER", 375, 200, 4);
			DrawHelper.drawString(g, "YOUR SCORE: " + m_gameBoard.getScore(), 375, 250, 2);
			DrawHelper.drawString(g, "press enter to return to the menu", 375, 300, 2);
		}
	}

}