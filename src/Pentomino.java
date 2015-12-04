import java.awt.Color;

public class Pentomino{

	private int[][] m_matrix;
	private static final int MATRIX_SIZE = 5;
	private Color m_color;
	private int m_x;
	private int m_y;

	/**
	 * @return The color of this pentomino
     */
	public Color getColor(){
		return m_color;
	}

	/**
	 * @return The x position of the pentomino within the board
     */
	public int getX(){
		return m_x;
	}

	/**
	 * Sets x position of the pentomino of the board
	 * @param x The x position of the pentomino of the board
     */
	public void setX(int x){
		m_x = x;
	}
	/**
	 * @return The y position of the pentomino within the board
	 */
	public int getY(){
		return m_y;
	}

	/**
	 * Sets y position of the pentomino of the board
	 * @param y The x position of the pentomino of the board
	 */
	public void setY(int y){
		m_y = y;
	}

	/**
	 * @return int[][] matrix of this pentomino
     */
	public int[][] getMatrix(){
		return m_matrix;
	}

	private int[] getColumn(int[][] matrix, int column){
		int[] tmp = new int[5];
		for(int i = 0; i != MATRIX_SIZE; i ++){
			tmp[i] = matrix[i][column];
		}
		return tmp;
	}

	private boolean arrayEmpty(int[] row){
		for(int i = 0; i != row.length; i++){
			if (row[i] != 0) {
				return false;
			}
		}
		return true;
	}

	private void fixOrigin(){
		int[][] tmp = new int[5][5];

		int rowOffset = 0;
		while(arrayEmpty(m_matrix[rowOffset])){
			rowOffset++;
		}

		int columnOffset = 0;
		while(arrayEmpty(getColumn(m_matrix, columnOffset))){
			columnOffset++;
		}
		
		//Apply the row offset
		System.arraycopy(m_matrix, rowOffset, tmp, 0, MATRIX_SIZE-rowOffset);

		int[][] tmp2 = new int[5][5];
		
		//Apply the column offset
		for(int i = 0; i < MATRIX_SIZE; i++){
			System.arraycopy(tmp[i], columnOffset, tmp2[i], 0, MATRIX_SIZE-columnOffset);
		}
		
		m_matrix = tmp2;
	}

	private void rotate(){
		int[][] tmp = new int[MATRIX_SIZE][MATRIX_SIZE];
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				tmp[j][MATRIX_SIZE - 1 - i] = m_matrix[i][j];
			}	
		}
		m_matrix = tmp;
		fixOrigin();
	}

	public void flip(){
		int[][] tmp = new int[MATRIX_SIZE][MATRIX_SIZE];
		
		for (int i = 0; i < MATRIX_SIZE; i++){
			tmp[i][0] = m_matrix[i][4]; 
			tmp[i][1] = m_matrix[i][3];
			tmp[i][2] = m_matrix[i][2];
			tmp[i][3] = m_matrix[i][1];
			tmp[i][4] = m_matrix[i][0];
		}

		m_matrix = tmp;
		fixOrigin();
	}

	public void rotateCW(){
		rotate();
	}
	public void rotateCCW(){
		rotate();
		rotate();
		rotate();
	}

	

	Pentomino(int type, boolean flipped){

		switch(type){
			case 0:
				m_matrix = new int[][]{{0,0,0,1,0},{1,1,1,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(26, 188, 156);
				break;
			case 1:
				m_matrix = new int[][]{{1,1,0,0,0},{1,1,0,0,0},{1,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(46, 204, 113);
				break;
			case 2:
				m_matrix = new int[][]{{0,1,0,0,0},{1,1,1,0,0},{0,1,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(52, 152, 219);
				break;
			case 3:
				m_matrix = new int[][]{{0,1,1,0,0},{1,1,0,0,0},{0,1,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(155, 89, 182);
				break;
			case 4:
				m_matrix = new int[][]{{1,0,0,0,0},{1,0,0,0,0},{1,1,1,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(52, 73, 94);
				break;
			case 5:
				m_matrix = new int[][]{{1,0,0,0,0},{1,1,0,0,0},{0,1,1,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(241, 196, 15);
				break;
			case 6:
				m_matrix = new int[][]{{0,1,0,0,0},{1,1,0,0,0},{0,1,0,0,0},{0,1,0,0,0},{0,0,0,0,0}};
				m_color = new Color(230, 126, 34);
				break;
			case 7:
				m_matrix = new int[][]{{1,0,0,0,0},{1,0,0,0,0},{1,0,0,0,0},{1,0,0,0,0},{1,0,0,0,0}};
				m_color = new Color(231, 76, 60);
				break;
			case 8:
				m_matrix = new int[][]{{1,1,1,0,0},{0,1,0,0,0},{0,1,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(149, 165, 166);
				break;
			case 9:
				m_matrix = new int[][]{{1,1,0,0,0},{0,1,0,0,0},{0,1,1,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(243, 156, 18);
				break;
			case 10:
				m_matrix = new int[][]{{1,0,1,0,0},{1,1,1,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(211, 84, 0);
				break;
			case 11:
				m_matrix = new int[][]{{1,1,0,0,0},{0,1,1,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(192, 57, 43);
				break;
			default:
				m_matrix = new int[][]{{0,0,0,1,0},{1,1,1,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
				m_color = new Color(189, 195, 199);
				break;
		}
		if(flipped){
			flip();
		}
	}


	public boolean fits(int[][] board, int x, int y){
		for(int i = 0; i < MATRIX_SIZE; i++){
			for(int j = 0; j < MATRIX_SIZE; j++){
				//Check if a piece of a pentomino is out of the bounds, making it not fit
				if(m_matrix[i][j] != 0 && (y + i >= board.length  || x + j >= board[0].length)){
					return false;
				}
				//If we got here, it means we are working within the bounds of the board
				//Check the current piece of the pentomino (m_matrix[x][y]) is not overlapping with an existing pentomino within the board
				if(y + i < board.length  || x + j < board[0].length){
					if(m_matrix[i][j] != 0 && board[y+i][x+j] != 0)
						return false;
				}
			}
		}
		return true;
	} 
	
	public void insert(int[][] board){
		for(int i = 0; i < MATRIX_SIZE; i++){
			for(int j = 0; j < MATRIX_SIZE; j++){
				if(m_y + i < board.length || m_x + j < board[0].length){
					try{
						if(m_matrix[i][j] != 0){
							board[m_y+i][m_x+j] = m_color.getRGB();
						}
					}
					catch(Exception e){

					}
				}
			}
		}
	} 

}