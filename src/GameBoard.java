public class GameBoard {
    private int[][] m_board;
    private int m_boardWidth;
    private int m_boardHeight;


    private Pentomino m_activePentomino;
    private Pentomino m_nextPentomino;

    GameBoard(int width, int height){
        m_board = new int[height][width];
        m_boardWidth = width;
        m_boardHeight = height;
    }

    public Pentomino getActivePentomino() {
        return m_activePentomino;
    }

    public void setActivePentomino(Pentomino activePentomino) {
        this.m_activePentomino = activePentomino;
    }

    public Pentomino getNextPentomino() {
        return m_nextPentomino;
    }

    public void setNextPentomino(Pentomino NextPentomino) {
        this.m_nextPentomino = NextPentomino;
    }

    /**
     * @return int[][] matrix of the board
     */
    public int[][] getBoardMatrix(){
        return m_board;
    }

    /**
     * Moves the active pentomino left by 1 cell, but only if the new position will fit.
     */
    public void moveActiveLeft(){
        if(m_activePentomino.getX() - 1 >= 0 && m_activePentomino.fits(m_board, m_activePentomino.getX() - 1, m_activePentomino.getY())){
            m_activePentomino.setX(m_activePentomino.getX() - 1);
        }
    }

    /**
     * Moves the active pentomino right by 1 cell, but only if the new position will fit.
     */
    public void moveActiveRight(){
        if(m_activePentomino.fits(m_board, m_activePentomino.getX() + 1, m_activePentomino.getY())){
            m_activePentomino.setX(m_activePentomino.getX() + 1);
        }
    }

    /**
     * Rotates the active pentomino clockwise only if it will fit within the board.
     *
     * The method rotates the pentomino clockwise, then checks if it does fit. If it doesn't, it reverts the pentomino back.
     */
    public void rotateActive(){
        m_activePentomino.rotateCW();
        if(!m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY())){
            m_activePentomino.rotateCCW();
        }
    }

    /**
     * Performs one move down as well as checking for any cleared lines
     */
    public void onThink(){
        if(m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY() + 1)){
            m_activePentomino.setY(m_activePentomino.getY() + 1);
        }else{
            m_activePentomino.insert(m_board);
            m_activePentomino = m_nextPentomino;
            m_nextPentomino = null;
        }
        checkLines();
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
        for(int i = line; i > 0; i--){
            System.arraycopy(m_board, i-1, m_board, i, 1);
        }
        m_board[0] = new int[m_boardWidth];
    }

    /**
     * This method checks all rows from top to bottom looking for filled lines and clearing them
     */
    private void checkLines() {
        //go thru all possible rows from top to bottom
        for (int i = 0; i < m_boardHeight; i++) {
            if (lineFilled(i)) {
                clearLine(i);
            }
        }
    }
}
