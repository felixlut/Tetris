public class Tetris {

    // The display and board of the tetris game
    private Display display;
    private Board board;

    // The metadata of the display
    private final String title = "Tetris";
    private final int width;
    private final int height;

    // The metadata of the display
    private final int columns = 10;
    private final int rows = 20;

    //
    private float speedMod = 1;

    public Tetris () {
        board = new Board(columns, rows);
        width = board.getBoard()[0][0].getTileLength() * 10;
        height = board.getBoard()[0][0].getTileLength() * 20;
        display = new Display(title, width, height, board);
    }

    public void runTetris() {
        final int FPS = 5;
        final long time = 1000/FPS;

        boolean alive = true;
        boolean movingShape = false;
        int score = 0;

        while (alive) {
            if (!movingShape) {
                if (!board.createNewTetrominoe()) {
                    alive = false;
                } else {
                    movingShape = true;
                }
            }
            if (alive) {
                long startTime = System.currentTimeMillis();
                display.render();

                // Add score and remove full lines
                if (!board.moveDownActiveTetrominoe()) {
                    int newScore = board.checkForScore();
                    score += newScore;
                    movingShape = false;
                    board.setScore(score);
                }
                long spentTime = System.currentTimeMillis() - startTime;
                // Attempts to keep a constant FPS.
                try {
                    Thread.sleep(time - spentTime);
                } catch (Exception e) {
                }
            }
        }
    }
}
