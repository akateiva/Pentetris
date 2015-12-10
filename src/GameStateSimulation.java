import javax.swing.*;
import java.awt.*;
import java.util.*;


public class GameStateSimulation extends GameState{
	int m_difficulty;

	final int m_boardWidth = 5;
	final int m_boardHeigth = 12;
	int m_board[][];
	boolean dropping = true;
	int choice = 1;
	static int counter = 0;
	int movementsX = 0;
	int movementsY = 0;
	int m_multiplier;


	boolean m_finished;
	boolean solved = false;
	int m_score;
	Pentomino[] choices;

	Pentomino m_nextPentomino;
	Pentomino m_activePentomino;


	public GameStateSimulation() {
		m_score = 0;
		m_difficulty = 1;
		m_finished = false;
		m_board = new int[m_boardHeigth][m_boardWidth];
		m_activePentomino = nextPentomino();

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
				m_multiplier++;
			}
		}
		if (m_multiplier>0) {
			m_score += 1 + ((m_multiplier-1) * 2);
		}
		m_multiplier = 0;
	}

	public void onEvent(EventType e){
		switch(e){
			case ENTER:
				Pentetris.startMenu();
				break;
			default:

				break;
		}
		Pentetris.revalidate();
	}
	
	private Pentomino nextPentomino(){
		Pentomino tmp = new Pentomino(4,false);
		//V
		if(counter==0){
			tmp = new Pentomino(4,true);
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//Z
		if(counter==1){
			tmp = new Pentomino(9,false);
			counter++;
			tmp.rotateCW();
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//F
		if(counter==2){
			tmp = new Pentomino(3,false);
			counter++;
			tmp.flip();
			tmp.rotateCCW();
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//N
		if(counter==3){
			tmp = new Pentomino(11,false);
			counter++;
			tmp.rotateCW();
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//T
		if(counter==4){
			tmp = new Pentomino(8,false);
			counter++;
			tmp.rotateCW();
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//U
		if(counter==5){
			tmp = new Pentomino(10,false);
			counter++;
			tmp.rotateCW();
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//X
		if(counter==6){
			tmp = new Pentomino(2,false);
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//P
		if(counter==7){
			tmp = new Pentomino(1,false);
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//I
		if(counter==8){
			tmp = new Pentomino(7,false);
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//Y
		if(counter==9){
			tmp = new Pentomino(6,false);
			tmp.flip();
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//W
		if(counter==10){
			tmp = new Pentomino(5,false);
			tmp.flip();
			tmp.rotateCCW();
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		//L
		if(counter==11){
			tmp = new Pentomino(0,false);
			tmp.rotateCCW();
			counter++;
			tmp.setX(0);
			tmp.setY(0);
			return tmp;
		}
		
		
		if(counter>=12) {
			tmp = null;
			gameOver();
		}
		return tmp;
	}
	
	
	public void onThink(){
		//V
		if (counter==1) {
			if (movementsY<9) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<2) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==9 && m_activePentomino.getX()==2){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//Z
		if (counter==2) {
			if (movementsY<9) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<1) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==9 && m_activePentomino.getX()==1){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//F
		if (counter==3) {
			if (movementsY<7) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<2) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==7 && m_activePentomino.getX()==2){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//N
		if (counter==4) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<0) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==0){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//T
		if (counter==5) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<2) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==2){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//U
		if (counter==6) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==0){
				m_activePentomino.setX(m_activePentomino.getX()+1);
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//X
		if (counter==7) {
			if (movementsY<6) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<2) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==6 && m_activePentomino.getX()==2){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//P
		if (counter==8) {
			if (movementsY<5) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<1) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==5 && m_activePentomino.getX()==1){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//I
		if (counter==9) {
			if (movementsY<7) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<0) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==7 && m_activePentomino.getX()==0){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//Y
		if (counter==10) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==0){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//W
		if (counter==11) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<1) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==1){
				m_activePentomino.insert(m_board);
				movementsY=0;
				movementsX=0;
				m_activePentomino = nextPentomino();
			}
		}
		//L
		if (counter==12) {
			if (movementsY<8) {
				m_activePentomino.setY(m_activePentomino.getY()+1);
				movementsY++;
			}
			if (movementsX<3) {
				m_activePentomino.setX(m_activePentomino.getX()+1);
				movementsX++;
			}
			if (m_activePentomino.getY()==8 && m_activePentomino.getX()==3){
				m_activePentomino.insert(m_board);
				m_activePentomino = new Pentomino(0,false);
				m_activePentomino.setY(100);
			}
		}
		
		
			Pentetris.revalidate();
			checkLines();
	}

	public void paint(Graphics g){
		final int startX = 0;
		final int startY = 0;
		final int cellSize = 42;

		//Draw the board with locked-in pentominoes
		for(int i = 0; i != m_boardHeigth; i++){
			for(int j = 0; j != m_boardWidth; j++){
				g.drawRect(270 + j*cellSize, i*cellSize, cellSize, cellSize);
				if(m_board[i][j] != 0){
					g.setColor(new Color(m_board[i][j]));
				}else{
					g.setColor(Color.WHITE);
				}
				g.fillRect(270 + j*cellSize, i*cellSize, cellSize, cellSize);
				g.setColor(Color.BLACK);
			}
		}
		
		//Draw the active pentomino
		
			for(int i = 0; i < 5; i ++){
				for(int j = 0; j < 5; j++){
					g.setColor(m_activePentomino.getColor());
					if(m_activePentomino.getMatrix()[i][j] != 0){
						g.fillRect(270 + j*cellSize + m_activePentomino.getX()*cellSize, i*cellSize + m_activePentomino.getY()*cellSize, cellSize, cellSize);
					}
					g.setColor(Color.BLACK);
				}
			
		} 

		//Draw the score
		g.drawString("SCORE: " + m_score, 75, 50);

	}

}