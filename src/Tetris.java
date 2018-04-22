import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;


public class Tetris implements KeyPressReceiver{

    // Frame rate
    private final int FPS = 60;
    private final long RENDER_PERIOD_TIME = 1000/FPS;

    // How often the game logic updates
    private double updateGameLogic;
    private double updatePeriod;


    // The display and board of the tetris game
    private Display display;
    private Board board;
    private Timer timer;

    // Game info
    private boolean alive;
    private boolean movingShape;
    private int score;
    private boolean paused;

    // The metadata of the display
    private final int width;
    private final int height;

    // The metadata of the display
    private static final int COLUMNS = 10;
    private static final int ROWS = 20;

    public Tetris () {
        board = new Board(COLUMNS, ROWS);
        width = board.getBoard()[0][0].getTile_Length() * COLUMNS;
        height = board.getBoard()[0][0].getTile_Length() * ROWS;
        display = new Display(width, height, board, this);
        timer = new Timer();
        alive = true;
        movingShape = false;
        score = 0;
        paused = false;
        updateGameLogic = 1.0;
        updatePeriod = 1000/updateGameLogic;
    }

    /**
     * Run the game Tetris
     */
    public void runTetris() {
        // Setup a thread that renders the display
        timer.scheduleAtFixedRate(new RenderTask(), 1, RENDER_PERIOD_TIME);

        // Main game loop
        while (alive) {
            long startTime = System.currentTimeMillis();
            updatePeriod = 1000/updateGameLogic;

            paused = board.isPaused();
            // If the game isn't paused the game logic should proceed
            if (!paused) {
                tic();
            }
            long spentTime = System.currentTimeMillis() - startTime;

            // Keep the update rate at the desired speed
            try {
                Thread.sleep((long) updatePeriod - spentTime);
            } catch (Exception e) {}
        }
    }

    /**
     * Render the display
     */
    private synchronized void render() {
        display.render();
    }

    /**
     * Deal with keyEvents as long as the game isn't paused or the KeyEvent in question meant to alter
     * the paused state. The tetromino must also be active
     * @param keyEvent  The KeyEvent
     */
    public synchronized void keyAction(KeyEvent keyEvent) {
        if (board.getActiveTetromino().isActive() && (!board.isPaused() || keyEvent.getKeyCode() == KeyEvent.VK_SPACE) ) {
            board.performKeyAction(keyEvent);
        }
    }

    /**
     * Game logic for Tetris
     */
    private synchronized void tic() {
        // If there is no moving Tetromino, create a new one
        if (!movingShape) {
            // If it's impossible to create a new Tetromino on the board,
            if (!board.createNewTetromino()) {
                alive = false;
            } else {
                movingShape = true;
            }
        }
        if (alive) {
            // Check if score should be added and lines removed if the active Tetromino can't be moved down further
            if (!board.moveDownTetromino()) {
                int newScore = board.checkForScore();
                // Update the score
                if (newScore > 0) {
                    // Increase the difficulty by 30% each 10 points
                    if (score % 10 + newScore >= 10) {
                        updateGameLogic *= 1.3;
                    }
                    score += newScore;
                    board.setScore(score);
                }
                movingShape = false;
            }
        }
    }

    /**
     * Setup a thread that renders the display on a timer
     */
    private class RenderTask extends TimerTask {
        @Override
        public void run() {
            render();
        }
    }
}
