import javax.swing.*;
import java.awt.*;
import java.util.*;


public class GameStateGame extends GameState{
	int m_difficulty;

	final int m_boardWidth = 10;
	final int m_boardHeigth = 20;
	int m_board[][];

	boolean m_finished;
	int m_score;


	Pentomino m_nextPentomino;
	Pentomino m_activePentomino;

	public GameStateGame(int difficulty) {
		m_score = 0;
		m_difficulty = difficulty;
		m_finished = false;
		m_board = new int[m_boardHeigth][m_boardWidth];
		m_nextPentomino = randomPentomino();
		m_activePentomino = randomPentomino();
	}
	private void gameOver(){
		m_finished = true;
	}
	private boolean lineFilled(int line){
		for(int i = 0; i < m_boardWidth; i++){
			if(m_board[line][i] == 0){
				return false;
			}
		}
		return true;
	}
	private void clearLine(int line){
		m_score++;
		for(int i = line; i > 0; i--){
			System.arraycopy(m_board, i-1, m_board, i, 1);
		}
		m_board[0] = new int[m_boardWidth];
	}
	private void checkLines(){
		//go thru all possible rows from top to bottom
		for(int i = 0; i < m_boardHeigth; i++){
			if(lineFilled(i)){
				clearLine(i);
			}
		}
	}
	private Pentomino randomPentomino(){
		Pentomino tmp;
		Random ran = new Random();
		tmp = new Pentomino(ran.nextInt(12), ran.nextBoolean());
		tmp.setX(3);
		tmp.setY(0);
		if(!tmp.fits(m_board, tmp.getX(), tmp.getY())){
			gameOver();
		}
		return tmp;
	}
	public void onEvent(EventType e){
		switch(e){
			case UP:
				//attempt to fit it in
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
				//try fitting it
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
	public void onThink(){
		if(m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY() + 1)){
			m_activePentomino.setY(m_activePentomino.getY() + 1);
		}else{
			m_activePentomino.insert(m_board);
			if(!m_finished){
				m_activePentomino = m_nextPentomino;
				m_nextPentomino = randomPentomino();
			}
		}
		checkLines();
		Pentetris.revalidate();
	}
	public void paint(Graphics g){
		final int startX = 0;
		final int startY = 0;
		final int cellSize = 25;

		//Draw the board with locked-in pentominoes
		for(int i = 0; i != m_boardHeigth; i++){
			for(int j = 0; j != m_boardWidth; j++){
				g.drawRect(250 + j*cellSize, i*cellSize, cellSize, cellSize);
				if(m_board[i][j] != 0){
					g.setColor(new Color(m_board[i][j]));
				}else{
					g.setColor(Color.WHITE);
				}
				g.fillRect(250 + j*cellSize, i*cellSize, cellSize, cellSize);
				g.setColor(Color.BLACK);
			}
		}
		//Draw the active pentomino
		for(int i = 0; i < 5; i ++){
			for(int j = 0; j < 5; j++){
				g.setColor(m_activePentomino.getColor());
				if(m_activePentomino.getMatrix()[i][j] != 0){
					g.fillRect(250 + j*cellSize + m_activePentomino.getX()*cellSize, i*cellSize + m_activePentomino.getY()*cellSize, cellSize, cellSize);
				}
				g.setColor(Color.BLACK);
			}
		}
		//Draw the next pentomino
		g.drawString("NEXT PENTOMINO:", 75, 75);
		for(int i = 0; i < 5; i ++){
			for(int j = 0; j < 5; j++){
				g.setColor(m_nextPentomino.getColor());
				if(m_nextPentomino.getMatrix()[i][j] != 0){
					g.fillRect(75 + j*10, 75+i*10, 10, 10);
				}
				g.setColor(Color.BLACK);
			}
		}
		//Draw the score
		g.drawString("SCORE: " + m_score, 75, 50);

		//draw the game over text
		if(m_finished) {
			Font newFont = g.getFont().deriveFont(g.getFont().getSize() * 4f);
			g.setFont(newFont);

			g.drawString("GAME OVER", 300, 200);
			newFont = g.getFont().deriveFont(g.getFont().getSize() * 0.5f);
			g.setFont(newFont);
			g.drawString("YOUR SCORE: " + m_score, 300, 250);

			g.drawString("press enter to return to the menu", 300, 300);
		}
	}

}