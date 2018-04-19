import java.util.TimerTask;
import java.util.Timer;


public class Tetris {

    // Framerate
    final int FPS = 60;
    final long renderPeriodTime = 1000/FPS;
    final long updatePeriod = 1000/1;


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
        display = new Display(title, width, height, board);
        timer = new Timer();
        alive = true;
        movingShape = false;
        score = 0;
        paused = false;
    }

    public void runTetris() {
        timer.scheduleAtFixedRate(new RenderTask(), 0, renderPeriodTime);

        while (alive) {
            long startTime = System.currentTimeMillis();

            tic();

            long spentTime = System.currentTimeMillis() - startTime;
            try {
                Thread.sleep(updatePeriod - spentTime);
            } catch (Exception e) {}
        }
    }

    private synchronized void render() {
        display.render();
    }

    private synchronized void tic() {
        paused = board.isPaused();
        if (!paused) {
            if (!movingShape) {
                if (!board.createNewTetrominoe()) {
                    alive = false;
                } else {
                    movingShape = true;
                }

            }
            if (alive) {

                // Add score and remove full lines
                if (!board.moveDownActiveTetrominoe()) {
                    int newScore = board.checkForScore();
                    score += newScore;
                    movingShape = false;
                    board.setScore(score);
                }
            }
        }
    }

    private class RenderTask extends TimerTask {
        @Override
        public void run() {
            render();
        }
    }
}
