import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;


public class Tetris implements KeyPressReceiver{

    // Frame rate
    final int FPS = 60;
    final long RENDERPERIODTIME = 1000/FPS;

    // How often the game logic updates
    final int UPDATEGAMELOGIC = 5;
    final long UPDATEPERIOD = 1000/UPDATEGAMELOGIC;

    // The display and board of the tetris game
    private Display display;
    private Board board;
    private Timer timer;

    // Game info
    private boolean alive;
    boolean movingShape;
    int score;
    boolean paused;

    // The metadata of the display
    private final String title = "Tetris";
    private final int width;
    private final int height;

    // The metadata of the display
    private final int columns = 10;
    private final int rows = 20;

    public Tetris () {
        board = new Board(columns, rows);
        width = board.getBoard()[0][0].getTileLength() * 10;
        height = board.getBoard()[0][0].getTileLength() * 20;
        display = new Display(title, width, height, board, this);
        timer = new Timer();
        alive = true;
        movingShape = false;
        score = 0;
        paused = false;
    }

    /**
     * Run the game Tetris
     */
    public void runTetris() {
        // Setup a thread that renders the display
        timer.scheduleAtFixedRate(new RenderTask(), 0, RENDERPERIODTIME);

        // Main game loop
        while (alive) {
            long startTime = System.currentTimeMillis();

            paused = board.isPaused();
            // If the game isn't paused the game logic should proceed
            if (!paused) {
                tic();
            }
            long spentTime = System.currentTimeMillis() - startTime;

            // Keep the update rate at the desired speed
            try {
                Thread.sleep(UPDATEPERIOD - spentTime);
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
     * Deal with keyEvents as long as the game isn't paused or the KeyEvent in question
     * are meant to alter the paused state
     * @param keyEvent  The KeyEvent
     */
    public synchronized void keyAction(KeyEvent keyEvent) {
        if (!board.isPaused() || keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
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
            if (!board.moveDownActiveTetromino()) {
                int newScore = board.checkForScore();
                score += newScore;
                movingShape = false;
                board.setScore(score);
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
