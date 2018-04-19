import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board {

    private Tile[][] board;
    private Tetromino activeTetromino;
    private int score;

    // The number of rows and columns
    private final int columns;
    private final int rows;

    // Used to store the coordinates of the paused active tetromino
    private ArrayList<Coordinate> pausedCoords;
    private boolean paused;


    public Board (int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        score = 0;
        activeTetromino = null;
        board = new Tile[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
        pausedCoords = new ArrayList<>();
        paused = false;
    }

    /**
     * Create a new active Tetromino if possible.
     * @return false if there is a square occupying any tile on either the highest or second highest row, otherwise true
     */
    public boolean createNewTetromino() {
        // If any tile of the 2 highest rows are occupied, return false
        for (int i = 0; i < 10; i++) {
            if (board[i][0].isFull() || board[i][1].isFull()) {
                return false;
            }
        }
        activeTetromino = new Tetromino();
        Square[] tetrominoParts = activeTetromino.getSquares();

        // TODO: 2018-03-23 Streamline this
        switch (activeTetromino.getShape()) {
            case LINE0:
                for (int i = 0; i < 4; i++) {
                    board[i + 3][0].setAndActiveSquare(tetrominoParts[i]);
                }
                break;
            case BOX:
                for (int i = 0; i < 2; i++) {
                    board[i + 4][0].setAndActiveSquare(tetrominoParts[i]);
                }
                for (int i = 0; i < 2; i++) {
                    board[i + 4][1].setAndActiveSquare(tetrominoParts[i + 2]);
                }
                break;
            case PYRAMID0:
                board[4][0].setAndActiveSquare(tetrominoParts[0]);
                for (int i = 0; i < 3; i++) {
                    board[i + 3][1].setAndActiveSquare(tetrominoParts[i + 1]);
                }
                break;
            case RIGHTL0:
                board[3][0].setAndActiveSquare(tetrominoParts[0]);
                for (int i = 0; i < 3; i++) {
                    board[i + 3][1].setAndActiveSquare(tetrominoParts[i + 1]);
                }
                break;
            case LEFTL0:
                board[5][0].setAndActiveSquare(tetrominoParts[0]);
                for (int i = 0; i < 3; i++) {
                    board[i + 3][1].setAndActiveSquare(tetrominoParts[i + 1]);
                }
                break;
            case RIGHTSNAKE0:
                for (int i = 0; i < 2; i++) {
                    board[i + 4][0].setAndActiveSquare(tetrominoParts[i]);
                }
                for (int i = 0; i < 2; i++) {
                    board[i + 3][1].setAndActiveSquare(tetrominoParts[i + 2]);

                }
                break;
            case LEFTSNAKE0:
                for (int i = 0; i < 2; i++) {
                    board[i + 3][0].setAndActiveSquare(tetrominoParts[i]);
                }
                for (int i = 0; i < 2; i++) {
                    board[i + 4][1].setAndActiveSquare(tetrominoParts[i + 2]);
                }
                break;
        }
        return true;
    }

    /**
     * Tries to move down the active Tetromino. moveDownActiveTetrominoHelper() assists in determining if a
     * move is possible or not. Note: The setup of this method is somewhat different from the helper
     * method since the order of the for each statement won't always yield a valid order of moving the squares
     * down, i.e. there would be a possibility of setting an active square to null before it's moved down
     * @return          true if a move has been made, false otherwise
     */
    public boolean moveDownActiveTetromino() {
        if (moveDownActiveTetrominoHelper()) {
            for (int i = columns - 1; i >= 0; i--) {
                for (int j = rows - 1; j >= 0; j--) {
                    if (board[i][j].isActive()) {
                        board[i][j + 1].setAndActiveSquare(board[i][j].getSquare());
                        board[i][j].setAndActiveSquare(null);
                    }
                }
            }
            return true;
        } else {
            for (int i = columns - 1; i >= 0; i--) {
                for (int j = rows - 1; j >= 0; j--) {
                    if (board[i][j].isActive()) {
                        board[i][j].setActive(false);
                    }
                }
            }
            return false;
        }
    }

    /**
     * Helper for moveDownActiveTetromino(). Basic idea is that this method checks whether or not a move down
     * for the active Tetromino is possible, i.e. no floor or other square in the way, and returns the result
     * @return          true if a move down is possible, false otherwise
     */
    private boolean moveDownActiveTetrominoHelper() {
        for (Square square : activeTetromino.getSquares()) {
            int xPos = square.getxPos();
            int yPos = square.getyPos();
            if (!(yPos + 1 < 20) || !(!board[xPos][yPos + 1].isFull() || board[xPos][yPos + 1].isActive())){
                return false;
            }
        }
        return true;
    }

    /**
     * Makes a horizontal move of the active Tetromino if possible. moveHorizActiveTetrominoHelper() assists
     * in determining if a move is possible Note: The setup of this method is somewhat different from the helper
     * method since the order of the for each statement won't always yield a valid order of moving the squares
     * horizontally, i.e. there would be a possibility of setting an active square to null before it's moved
     * @param right     true if the move should be to the right, false if it's supposed to be to the left
     * @return          true if a move has been made, false otherwise
     */
    public boolean moveHorizActiveTetromino(boolean right) {
        boolean temp = moveHorizActiveTetrominoHelper(right);
        if (temp && right) {
            for (int i = columns - 1; i >= 0; i--) {
                for (int j = rows - 1; j >= 0; j--) {
                    if (board[i][j].isActive()) {
                        board[i + 1][j].setAndActiveSquare((board[i][j].getSquare()));
                        board[i][j].setAndActiveSquare(null);
                    }
                }
            }
            return true;
        } else if(temp) {
            for (int i = 0; i < columns; i++) {
                for (int j = rows - 1; j >= 0; j--) {
                    if (board[i][j].isActive()) {
                        board[i - 1][j].setAndActiveSquare(board[i][j].getSquare());
                        board[i][j].setAndActiveSquare(null);
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Helper for moveHorizActiveTetromino(). Basic idea is that this method checks whether or not a horizontal
     * move for the active Tetromino is possible, i.e. no wall or other square in the way, and returns the result
     * @param right     true if the move should be to the right, false if it's supposed to be to the left
     * @return          true if a move is possible, false otherwise
     */
    private boolean moveHorizActiveTetrominoHelper(boolean right) {
        for (Square square : activeTetromino.getSquares()) {
            int xPos = square.getxPos();
            int yPos = square.getyPos();
            if (right) {
                if (!(xPos + 1 < columns) || !(!board[xPos + 1][yPos].isFull() || board[xPos + 1][yPos].isActive())) {
                    return false;
                }
            } else {
                if (!(xPos - 1 >= 0) || !(!board[xPos - 1][yPos].isFull() || board[xPos - 1][yPos].isActive())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Attempt to rotate the active Tetromino. The rotation will happen as long as the squares of the active
     * Tetromino wouldn't leave the board or "crash" into already filled tiles
     */
    // TODO: 2018-04-18 Streamline this
    public void rotateActiveTetromino() {
        int x0 = activeTetromino.getSquares()[0].getxPos();
        int y0 = activeTetromino.getSquares()[0].getyPos();

        int x1 = activeTetromino.getSquares()[1].getxPos();
        int y1 = activeTetromino.getSquares()[1].getyPos();

        int x2 = activeTetromino.getSquares()[2].getxPos();
        int y2 = activeTetromino.getSquares()[2].getyPos();

        int x3 = activeTetromino.getSquares()[3].getxPos();
        int y3 = activeTetromino.getSquares()[3].getyPos();

        int polarize = 1;
        Tetromino.Shape type = activeTetromino.getShape();
        switch (type) {
            case LINE0:
                polarize = -1;
            case LINE1:
                Coordinate[] coordinates = {
                        new Coordinate(x0 - 1 * polarize, y0 - 1 * polarize),
                        new Coordinate(x2 + 1 * polarize, y2 + 1 * polarize),
                        new Coordinate(x3 + 2 * polarize, y3 + 2 * polarize)
                };
                if (isAllowedMove(coordinates)) {
                    board[x0 - 1 * polarize][y0 - 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[0]);
                    board[x0][y0].setAndActiveSquare(null);

                    board[x2 + 1 * polarize][y2 + 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);


                    board[x3 + 2 * polarize][y3 + 2 * polarize].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);
                    if (type == Tetromino.Shape.LINE0) {
                        activeTetromino.setShape(Tetromino.Shape.LINE1);
                    } else {
                        activeTetromino.setShape(Tetromino.Shape.LINE0);
                    }
                }
                break;
            case BOX:
                // Do nothing
                break;
            case PYRAMID0:
                Coordinate[] coordinates1 = {
                        new Coordinate(x1 + 1, y1 + 1)
                };
                if (isAllowedMove(coordinates1)) {
                    board[x1 + 1][y1 + 1].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);
                    activeTetromino.setShape(Tetromino.Shape.PYRAMID1);
                }
                break;
            case PYRAMID1:
                Coordinate[] coordinates2 = {
                        new Coordinate(x1 - 1, y1 + 1)
                };
                if (isAllowedMove(coordinates2)) {
                    board[x0 - 1][y0 + 1].setAndActiveSquare(activeTetromino.getSquares()[0]);
                    board[x0][y0].setAndActiveSquare(null);
                    activeTetromino.setShape(Tetromino.Shape.PYRAMID2);
                }
                break;
            case PYRAMID2:
                Coordinate[] coordinates3 = {
                        new Coordinate(x3 - 1, y3 - 1)
                };
                if (isAllowedMove(coordinates3)) {
                    board[x3 - 1][y3 - 1].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);
                    activeTetromino.setShape(Tetromino.Shape.PYRAMID3);
                }
                break;
            case PYRAMID3:
                Coordinate[] coordinates4 = {
                        new Coordinate(x3 + 1, y3 + 1),
                        new Coordinate(x0 + 1, y0 - 1),
                        new Coordinate(x1 - 1, y1 - 1)
                };
                if (isAllowedMove(coordinates4)) {
                    board[x3 + 1][y3 + 1].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);

                    board[x0 + 1][y0 - 1].setAndActiveSquare(activeTetromino.getSquares()[0]);
                    board[x0][y0].setAndActiveSquare(null);

                    board[x1 - 1][y1 - 1].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);


                    activeTetromino.setShape(Tetromino.Shape.PYRAMID0);
                }
                break;
            case RIGHTL0:
                Coordinate[] coordinates5 = {
                        new Coordinate(x2, y2 - 1),
                        new Coordinate(x3 - 2, y3 + 1)
                };
                if (isAllowedMove(coordinates5)) {
                    board[x2][y2 - 1].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);

                    board[x3 - 2][y3 + 1].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);

                    activeTetromino.setShape(Tetromino.Shape.RIGHTL1);
                }
                break;
            case RIGHTL1:
                Coordinate[] coordinates6 = {
                        new Coordinate(x2 - 2, y2),
                        new Coordinate(x3 - 2, y3 - 2)
                };
                if (isAllowedMove(coordinates6)) {
                    board[x2 - 2][y2].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);

                    board[x3 - 2][y3 - 2].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);

                    activeTetromino.setShape(Tetromino.Shape.RIGHTL2);
                }
                break;
            case RIGHTL2:
                Coordinate[] coordinates7 = {
                        new Coordinate(x2 + 1, y2 - 1),
                        new Coordinate(x3 + 1, y3 + 1)
                };
                if (isAllowedMove(coordinates7)) {
                    board[x2 + 1][y2 - 1].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);

                    board[x3 + 1][y3 + 1].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);

                    activeTetromino.setShape(Tetromino.Shape.RIGHTL3);
                }
                break;
            case RIGHTL3:
                Coordinate[] coordinates8 = {
                        new Coordinate(x2 + 1, y2 + 2),
                        new Coordinate(x3 + 3, y3)
                };
                if (isAllowedMove(coordinates8)) {
                    board[x2 + 1][y2 + 2].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);

                    board[x3 + 3][y3].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);

                    activeTetromino.setShape(Tetromino.Shape.RIGHTL0);
                }
                break;
            case LEFTL0:
                Coordinate[] coordinates9 = {
                        new Coordinate(x1 + 3, y1),
                        new Coordinate(x2 + 1, y2 - 2)
                };
                if (isAllowedMove(coordinates9)) {

                    board[x1 + 3][y1].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);

                    board[x2 + 1][y2 - 2].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);


                    activeTetromino.setShape(Tetromino.Shape.LEFTL1);
                }
                break;
            case LEFTL1:
                Coordinate[] coordinates10 = {
                        new Coordinate(x1 - 1, y1 + 1),
                        new Coordinate(x2 + 1, y2 + 1)
                };
                if (isAllowedMove(coordinates10)) {

                    board[x1 + 1][y1 - 1].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);

                    board[x2 + 1][y2 + 1].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);


                    activeTetromino.setShape(Tetromino.Shape.LEFTL2);
                }
                break;
            case LEFTL2:
                Coordinate[] coordinates11 = {
                        new Coordinate(x1 - 2, y1 + 2),
                        new Coordinate(x2 - 2, y2)
                };
                if (isAllowedMove(coordinates11)) {

                    board[x1 - 2][y1 + 2].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);

                    board[x2 - 2][y2].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);


                    activeTetromino.setShape(Tetromino.Shape.LEFTL3);
                }
                break;
            case LEFTL3:
                Coordinate[] coordinates12 = {
                        new Coordinate(x1 - 2, y1 - 1),
                        new Coordinate(x2, y2 + 1)
                };
                if (isAllowedMove(coordinates12)) {

                    board[x1 - 2][y1 - 1].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);

                    board[x2][y2 + 1].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);


                    activeTetromino.setShape(Tetromino.Shape.LEFTL0);
                }
                break;
            case RIGHTSNAKE0:
                polarize = -1;
            case RIGHTSNAKE1:
                Coordinate[] coordinates13 = { // TODO: 2018-03-28 Fix name
                        new Coordinate(x1 + 2 * polarize, y1 + 1 * polarize),
                        new Coordinate(x2, y2 + 1 * polarize)
                };

                if (isAllowedMove(coordinates13)) {
                    board[x1 + 2 * polarize][y1 + 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[1]);
                    board[x1][y1].setAndActiveSquare(null);

                    board[x2][y2 + 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[2]);
                    board[x2][y2].setAndActiveSquare(null);
                    if (type == Tetromino.Shape.RIGHTSNAKE0) {
                        activeTetromino.setShape(Tetromino.Shape.RIGHTSNAKE1);
                    } else {
                        activeTetromino.setShape(Tetromino.Shape.RIGHTSNAKE0);
                    }
                }
                break;
            case LEFTSNAKE0:
                polarize = -1;
            case LEFTSNAKE1:
                Coordinate[] coordinates14 = { // TODO: 2018-03-28 Fix name
                        new Coordinate(x0 - 2 * polarize, y0 + 1 * polarize),
                        new Coordinate(x3, y3 + 1 * polarize)
                };
                if (isAllowedMove(coordinates14)) {
                    board[x0 - 2 * polarize][y0 + 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[0]);
                    board[x0][y0].setAndActiveSquare(null);

                    board[x3][y3 + 1 * polarize].setAndActiveSquare(activeTetromino.getSquares()[3]);
                    board[x3][y3].setAndActiveSquare(null);
                    if (type == Tetromino.Shape.LEFTSNAKE0) {
                        activeTetromino.setShape(Tetromino.Shape.LEFTSNAKE1);
                    } else {
                        activeTetromino.setShape(Tetromino.Shape.LEFTSNAKE0);
                    }
                }
                break;
        }
    }

    /**
     * Checks whether every coordinate of the list is both on the board and also isn't already occupied by a square.
     * This is used to check if a potential rotation is possible or not
     * @param coordinates       A list of coordinates to check
     * @return                  True if all the coordinates are on the board and aren't occupied, false otherwise
     */
    private boolean isAllowedMove(Coordinate[] coordinates) {
        for (Coordinate cord : coordinates) {
            if (!isCordOnBoard(cord) || (board[cord.getxPos()][cord.getyPos()].isFull() && !board[cord.getxPos()][cord.getyPos()].isActive())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a coordinate is on the board or not
     * @param cord      the coordinate to check
     * @return          true if the coordinate is on the board, false otherwise
     */
    private boolean isCordOnBoard(Coordinate cord) {
        return cord.getxPos() >= 0 && cord.getxPos() < columns && cord.getyPos() >= 0 && cord.getyPos() < rows;
    }

    /**
     * Check each row to see if it's full. If a row is, remove it and add a point to the score
     * @return      The number of rows removed
     */
    public int checkForScore () {
        int count = 0;
        for (int i = rows - 1; i >= 0; i--) {
           if (removeRow(i)) {
               count++;
               i++;
           }
        }
        return count;
    }

    /**
     * Remove the specified row if it's full and then move down each above row
     * @param row   The specified row
     * @return      true if a row was removed, false otherwise
     */
    private boolean removeRow (int row) {
        if (fullRow(row)) {
            // Remove all squares from the row
            for (int i = 0; i < columns; i++) {
                board[i][row].setAndActiveSquare(null);
            }

            // Move down all rows above
            moveDownOver(row);

            return true;
        }
        return false;
    }

    /**
     * Move down every row over a specified row. Used each renderPeriodTime the player scores
     * @param row       The row of which every above lying row should be moved down
     */
    private void moveDownOver (int row) {
        for (int i = row - 1; i >= 0; i--) {
            moveDownRow(i);
        }
    }

    /**
     * Move down all the squares of a specific row
     * @param row       The row to move down
     */
    private void moveDownRow(int row) {
        for (int i = 0; i < columns; i++) {
            board[i][row + 1].setSquare(board[i][row].getSquare());
            board[i][row].setAndActiveSquare(null);
        }
    }

    /**
     * Check if every tile in a row is occupied by a square
     * @param row       row to check
     * @return          true if every tile in a row is occupied by a square, false otherwise
     */
    private boolean fullRow(int row) {
        for (int i = 0; i < columns; i++) {
            if (!board[i][row].isFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pause the game
     */
    public void pause() {
        if (!paused) {
            for (int i = columns - 1; i >= 0; i--) {
                for (int j = rows - 1; j >= 0; j--) {
                    if (board[i][j].isActive()) {
                        board[i][j].setActive(false);
                        pausedCoords.add(new Coordinate(i, j));
                    }
                }
            }
            paused = true;
        } else {
            for (Coordinate cord : pausedCoords) {
                board[cord.getxPos()][cord.getyPos()].setActive(true);
            }
            pausedCoords.clear();
            paused = false;
        }
    }

    /**
     * Perform every KeyAction
     */
    public void performKeyAction(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case (KeyEvent.VK_UP):
                rotateActiveTetromino();
                break;
            case (KeyEvent.VK_DOWN):
                moveDownActiveTetromino();
                break;
            case (KeyEvent.VK_RIGHT):
                moveHorizActiveTetromino(true);
                break;
            case (KeyEvent.VK_LEFT):
                moveHorizActiveTetromino(false);
                break;
            case (KeyEvent.VK_SPACE):
                pause();
                break;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}
