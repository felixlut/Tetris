import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Display {

    private JFrame frame;
    private Canvas canvas;
    private Board board;

    // Basic information for the display
    private int width, height;

    public Display(int width, int height, Board board, KeyPressReceiver keyPressReceiver) {
        this.width = width;
        this.height = height;
        this.board = board;

        frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressReceiver.keyAction(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));

        // Make sure the canvas is of the right size
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        frame.add(canvas);
        canvas.createBufferStrategy(2);
        frame.pack();
    }

    /**
     * Render the screen
     */
    public void render() {
        BufferStrategy bs = getCanvas().getBufferStrategy();
        if (bs == null) {
            getCanvas().createBufferStrategy(2);
            return;
        }
        do {

            do {
                Graphics graphics = bs.getDrawGraphics();

                // Set the font
                Font font = new Font("Sans serif", Font.BOLD, 14);
                graphics.setFont(font);

                // Draw the tiles and stationary squares
                int tileLength = board.getBoard()[0][0].getTile_Length();
                for (int i = 0; i < board.getColumns(); i++) {
                    for (int j = 0; j < board.getRows(); j++) {
                        graphics.setColor(board.getBoard()[i][j].getColor());
                        graphics.fillRect(i * tileLength, j * tileLength, tileLength, tileLength);
                    }
                }

                // Draw the active tetromino
                if (board.getActiveTetromino().isActive()) {
                    for (Coordinate coordinate : board.getActiveTetromino().getCurrentCoordinates()) {
                        graphics.setColor(board.getActiveTetromino().getColor());
                        graphics.fillRect(coordinate.getxPos() * tileLength, coordinate.getyPos() * tileLength, tileLength, tileLength);
                    }
                }

                // Add the score and the paused state to the title of the frame
                StringBuilder scoreTitle = new StringBuilder("Score: " + board.getScore());
                if (board.isPaused()) {
                    scoreTitle.append(" GAME PAUSED");
                }
                frame.setTitle(scoreTitle.toString());

                graphics.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
