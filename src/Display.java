import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
     * Created by Felix Luthman on 2017-05-12.
     */
    public class Display {

        protected static final int UP = 38;
        protected static final int DOWN = 40;
        protected static final int RIGHT = 39;
        protected static final int LEFT = 37;
        protected static final int SPACE = 32;


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
                            board.devFreeze();
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

            frame.pack();
        }

        public void render() {
            BufferStrategy bs = getCanvas().getBufferStrategy();
            if (bs == null) {
                getCanvas().createBufferStrategy(3);
                return;
            }
            Graphics graphics = bs.getDrawGraphics();

            // Set the font
            Font font = new Font("Sans serif", Font.BOLD, 18);
            graphics.setFont(font);

            // Clear the screen
            graphics.clearRect(0, 0, width, height);

            // Draw the squares
            for (int i = 0; i < board.getColumns(); i++) {
                for (int j = 0; j < board.getRows(); j++) {
                    int temp = board.getBoard()[i][j].getTileLength();
                    graphics.setColor(board.getBoard()[i][j].getColor());
                    graphics.fillRect(i * temp, j * temp, temp, temp);
                    if (board.getBoard()[i][j].isActive()) {
                        graphics.setColor(Color.black);
                        graphics.drawString(String.valueOf(board.getBoard()[i][j].getSquare().getNumberInOrder()),
                                i * temp + temp/2, j * temp + temp/2);
                    }
                }
            }

            frame.setTitle("Score: " + board.getScore());


            bs.show();
            graphics.dispose();
        }

        public Canvas getCanvas() {
            return canvas;
        }
    }
