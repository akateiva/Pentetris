public class GameBoard{
    private int[][] m_board;
    private int m_boardWidth;
    private int m_boardHeight;
    private boolean m_valid = true;
    private int m_score = 0;
    public int m_scoreDelta = 0; //used by the bot. not well understood.
    int m_multiplier;

    private Pentomino m_activePentomino;
    private Pentomino m_nextPentomino;

    GameBoard(int width, int height){
        m_board = new int[height][width];
        m_boardWidth = width;
        m_boardHeight = height;

    }

    public boolean isValid(){
        return m_valid;
    }

    public void invalidate(){
        m_valid = false;
    }

    public int getScore() {
        return m_score;
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

    public GameBoard deepCopy(){
        GameBoard copy = new GameBoard(m_boardWidth, m_boardHeight);

        //Retrieve the address of the copy's matrix, then copy the contents of this over to the copy
        //NOTE: Since the matrix is a 2-dimensional array and we want to perform a deep-copy we must copy each row (otherwise you'll be copying addresses)
        int[][] copy_matrix = copy.getBoardMatrix();
        for(int i = 0; i < m_boardHeight; i++) {
            System.arraycopy(m_board[i], 0, copy_matrix[i], 0, m_board[i].length);
        }

        //Create copies of the pentomino objects
        Pentomino activePentomino = m_activePentomino.deepCopy();
        Pentomino nextPentomino = m_nextPentomino.deepCopy();

        copy.setActivePentomino(activePentomino);
        copy.setNextPentomino(nextPentomino);
        copy.m_score = m_score;

        return copy;

    }

    /**
     * @return int[][] matrix of the board
     */
    public int[][] getBoardMatrix(){
        return m_board;
    }


    private boolean isCovered(int x, int y){
        for(int i = y-1; i >= 0; i--){
            if(m_board[i][x] != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Computes various heuristics of the current locked-in board layout
     * @return the heuristic value
     */
    private int getColumnHeight(int x) {
        for(int i = 0; i < m_boardHeight; i++) {
            if(m_board[i][x] != 0){
                return m_boardHeight-i;
            }
        }
        return 0;
    }
    public float computeHeuristic() {


        int height = 0;
        /*
        //Peak height computing
        for(int i = m_boardHeight-1; i >= 0; i--){
            for(int j = 0; j < m_boardWidth; j++){
                if(m_board[i][j] != 0){
                    height++;
                    break;
                }
            }
        }
        */
        //Total height computing ( seems to work better )
        for(int i = 0; i < m_boardWidth; i++){
            height += getColumnHeight(i);
        }
        //Compute how many holes exist
        int holes = 0;
        for(int i = 0; i < m_boardWidth; i++){
            for(int j = 1; j < m_boardHeight; j++){
                if(isCovered(i,j) && m_board[j][i] == 0){
                    holes++;
                }
            }
        }
        //Compute complete lines
        int lines = m_scoreDelta;
        for(int i = 0; i < m_boardHeight; i++){
            if(lineFilled(i)) {
                lines++;
            }
        }
        //Compute the bumpiness ( the sum of all slopes )
        int bumpiness = 0;
        for(int i = 0; i < m_boardWidth-1; i++){
            bumpiness += Math.abs(getColumnHeight(i) - getColumnHeight(i+1));
        }
        //TODO: FINE-TUNE THE MULTIPLIERS
        return 0.76f*lines + -0.35f*holes + -0.51f*height + -0.18f*bumpiness;
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
     * Moves the pentomino down by 1 cell only if it fits.
     * @return true if it was possible to move the pentomino; false if the move could not be fitted.
     */
    public boolean moveActiveDown(){
        if(m_activePentomino.fits(m_board, m_activePentomino.getX(), m_activePentomino.getY() + 1)){
            m_activePentomino.setY(m_activePentomino.getY() + 1);
            return true;
        }
        return false;
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
     * Drops the active pentomino
     */
    public void dropActive(){
        while(moveActiveDown()){}
    }

    public void insertActive(){
        m_activePentomino.insert(m_board);
        if(m_nextPentomino != null){
            m_activePentomino = m_nextPentomino;
            m_nextPentomino = null;
        }
    }

    /**
     * Performs one move down as well as checking for any cleared lines
     */
    public void onThink(){
        if(!moveActiveDown()){
            insertActive();
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
    public void checkLines() {
        //go thru all possible rows from top to bottom
        for (int i = 0; i < m_boardHeight; i++) {
            if (lineFilled(i)) {
                clearLine(i);
                m_multiplier++;
            }
        }
        if (m_multiplier>0) {
        	m_score += 1 + ((m_multiplier-1)*2);
        }
        m_multiplier = 0;
    }
}
