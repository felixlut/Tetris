import java.awt.event.KeyEvent;
import java.util.Random;

public class Board {

    private Tile[][] board;
    private Tetromino activeTetromino;
    private int score;

    // The number of rows and columns
    private final int columns;
    private final int rows;

    // Indicate whether the game is paused or not
    private boolean paused;

    public Board(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        score = 0;
        activeTetromino = null;
        board = new Tile[this.columns][this.rows];
        for (int i = 0; i < this.columns; i++) {
            for (int j = 0; j < this.rows; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
        paused = false;
    }

    /**
     * Attempt to create a new active Tetromino. If any tile on the 2 highest rows are occupied, reject the attempt
     *
     * @return false if there is a square occupying any tile on either the highest or second highest row, otherwise true
     */
    public boolean createNewTetromino() {
        // If any tile of the 2 highest rows are occupied, return false
        for (int i = 0; i < 10; i++) {
            if (board[i][0].isFull() || board[i][1].isFull()) {
                return false;
            }
        }
        // Create a random tetromino
        Random random = new Random();
        switch (random.nextInt(7)) {
            case 0:
                activeTetromino = new Line(new Coordinate(4, 0), this);
                break;
            case 1:
                activeTetromino = new Box(new Coordinate(4, 0), this);
                break;
            case 2:
                activeTetromino = new Pyramid(new Coordinate(4, 0), this);
                break;
            case 3:
                activeTetromino = new RightL(new Coordinate(5, 0), this);
                break;
            case 4:
                activeTetromino = new LeftL(new Coordinate(3, 0), this);
                break;
            case 5:
                activeTetromino = new RightSnake(new Coordinate(4, 0), this);
                break;
            case 6:
                activeTetromino = new LeftSnake(new Coordinate(4, 0), this);
                break;
        }
        return true;
    }

    /**
     * Fill each coordinate with a square of the active tetromino. The array provided should always be of size 4, since
     * there are 4 squares in a tetromino
     *
     * @param coordinates array with 4 coordinates
     */
    public void fillSquares(Coordinate[] coordinates) {
        if (coordinates.length == 4) {
            int i = 0;
            for (Coordinate cord : coordinates) {
                board[cord.getxPos()][cord.getyPos()].setSquare(activeTetromino.getSquares()[i]);
                i++;
            }
        }
    }

    /**
     * Checks whether a coordinate is on the board or not
     *
     * @param cord the coordinate to check
     * @return true if the coordinate is on the board, false otherwise
     */
    public boolean isCordOnBoard(Coordinate cord) {
        return cord.getxPos() >= 0 && cord.getxPos() < columns && cord.getyPos() >= 0 && cord.getyPos() < rows;
    }

    /**
     * Check each row to see if it's full. If a row is, remove it and add a point to the score
     *
     * @return The number of rows removed
     */
    public int checkForScore() {
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
     *
     * @param row The specified row
     * @return true if a row was removed, false otherwise
     */
    private boolean removeRow(int row) {
        if (fullRow(row)) {
            // Remove all squares from the row
            for (int i = 0; i < columns; i++) {
                board[i][row].setSquare(null);
            }
            // Move down all rows above the current row one step
            moveDownOver(row);
            return true;
        }
        return false;
    }

    /**
     * Move down every row over a specified row.
     *
     * @param row The row of which every above lying row should be moved down
     */
    private void moveDownOver(int row) {
        for (int i = row - 1; i >= 0; i--) {
            moveDownRow(i);
        }
    }

    /**
     * Move down all the squares of a specific row
     *
     * @param row The row to move down
     */
    private void moveDownRow(int row) {
        for (int i = 0; i < columns; i++) {
            board[i][row + 1].setSquare(board[i][row].getSquare());
            board[i][row].setSquare(null);
        }
    }

    /**
     * Check if every tile in a row is occupied by a square
     *
     * @param row row to check
     * @return true if every tile in a row is occupied by a square, false otherwise
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
            paused = true;
        } else {
            paused = false;
        }
    }

    /**
     * Perform a KeyAction
     * Supported keys are the following:
     * UP - Rotate the tetromino
     * DOWN - Accelerate the downfall of the tetromino
     * RIGHT - Move the tetromino to the right
     * LEFT - Move the tetromino to the left
     * SPACE - Pause the game
     */
    public void performKeyAction(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case (KeyEvent.VK_UP):
                activeTetromino.rotate();
                break;
            case (KeyEvent.VK_DOWN):
                activeTetromino.moveDown();
                break;
            case (KeyEvent.VK_RIGHT):
                activeTetromino.moveHorizontal(true);
                break;
            case (KeyEvent.VK_LEFT):
                activeTetromino.moveHorizontal(false);
                break;
            case (KeyEvent.VK_SPACE):
                pause();
                break;
        }
    }

    /**
     * Checks whether or not a specific coordinate on the board is occupied by a square
     *
     * @param coordinate The specified coordinate
     * @return True if occupied, false otherwise
     */
    public boolean occupiedCoordinate(Coordinate coordinate) {
        return board[coordinate.getxPos()][coordinate.getyPos()].isFull();
    }

    /**
     * Attempt to move down the active tetromino
     *
     * @return true if the tetromino was moved, false otherwise
     */
    public boolean moveDownTetromino() {
        return activeTetromino.moveDown();
    }

    /**
     * Check if the game is paused
     *
     * @return true if the game is paused, false otherwise
     */
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

    public Tetromino getActiveTetromino() {
        return activeTetromino;
    }
}
