import java.awt.*;
import java.util.Random;

public class Tetrominoe {

    private final int shapeSize = 4;
    private Square[] squares;
    private Shape shape;
    private Color color;

    /**
     * All 7 available Tetrominoe shapes and their rotated variations
     */
    public enum Shape {
        LINE0, LINE1,
        BOX,
        PYRAMID0, PYRAMID1, PYRAMID2, PYRAMID3,
        RIGHTL0, RIGHTL1, RIGHTL2, RIGHTL3,
        LEFTL0, LEFTL1, LEFTL2, LEFTL3,
        RIGHTSNAKE0, RIGHTSNAKE1,
        LEFTSNAKE0, LEFTSNAKE1
    }

    /**
     * Create a Tetrominoe of a random shape
     */
    public Tetrominoe() {
        Random random = new Random();
        squares = new Square[shapeSize];
        switch (random.nextInt(7)) {
            case 0:
                shape = Shape.LINE0;
                createTetrominoe(Color.BLUE);
                break;
            case 1:
                shape = Shape.BOX;
                createTetrominoe(Color.GREEN);
                break;
            case 2:
                shape = Shape.PYRAMID0;
                createTetrominoe(Color.RED);
                break;
            case 3:
                shape = Shape.RIGHTL0;
                createTetrominoe(Color.PINK);
                break;
            case 4:
                shape = Shape.LEFTL0;
                createTetrominoe(Color.MAGENTA);
                break;
            case 5:
                shape = Shape.RIGHTSNAKE0;
                createTetrominoe(Color.YELLOW);
                break;
            case 6:
                shape = Shape.LEFTSNAKE0;
                createTetrominoe(Color.CYAN);
                break;
        }

    }

    /**
     * Create a Tetrominoe consisting of 4 squares of the specified color
     * @param color
     */
    private void createTetrominoe(Color color) {
        this.color = color;
        for (int i = 0; i < shapeSize; i++) {
            squares[i] = new Square(color, i);
        }
    }

    public Shape getShape() {
        return shape;
    }

    public Square[] getSquares() {
        return squares;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
