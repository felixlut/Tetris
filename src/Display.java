import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Display {

    // KeyCodes
    private static final int UP = 38;
    private static final int DOWN = 40;
    private static final int RIGHT = 39;
    private static final int LEFT = 37;
    private static final int SPACE = 32;

    private JFrame frame;
    private Canvas canvas;
    private Board board;

    // Basic information for the display
    private String title;
    private int width, height;

    public Display(String title, int width, int height, Board board) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.board = board;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                board.addKeyAction(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case (UP):
                        board.rotateActiveTetrominoe();
                        break;
                    case (DOWN):
                        board.moveDownActiveTetrominoe();
                        break;
                    case (RIGHT):
                        board.moveHorizActiveTetrominoe(true);
                        break;
                    case (LEFT):
                        board.moveHorizActiveTetrominoe(false);
                        break;
                    case (SPACE):
                        board.pause();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
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
                Font font = new Font("Sans serif", Font.BOLD, 18);
                graphics.setFont(font);

                // Draw the squares
                for (int i = 0; i < board.getColumns(); i++) {
                    for (int j = 0; j < board.getRows(); j++) {
                        int tileLength = board.getBoard()[i][j].getTileLength();
                        graphics.setColor(board.getBoard()[i][j].getColor());
                        graphics.fillRect(i * tileLength, j * tileLength, tileLength, tileLength);
                        if (board.getBoard()[i][j].isActive()) {
                            graphics.setColor(Color.black);
                            graphics.drawString(String.valueOf(board.getBoard()[i][j].getSquare().getNumberInOrder()),
                                    i * tileLength + tileLength/2, j * tileLength + tileLength/2);
                        }
                    }
                }
                frame.setTitle("Score: " + board.getScore());

                graphics.dispose();


            } while (bs.contentsRestored());

            bs.show();

        } while (bs.contentsLost());
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
