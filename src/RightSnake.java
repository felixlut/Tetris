import java.awt.*;

public class RightSnake extends Tetromino {

    // The coordinates relative to (0, 0) of each stage in a rotation
    private final Coordinate[] STAGE_0 = {
            new Coordinate(-1, 0),
            new Coordinate(0, 0),
            new Coordinate(0, -1),
            new Coordinate(1, -1),

    };
    private final Coordinate[] STAGE_1 = {
            new Coordinate(0, -1),
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
    };
    private final Coordinate[] STAGE_2 = {
            new Coordinate(-1, 1),
            new Coordinate(0, 1),
            new Coordinate(0, 0),
            new Coordinate(1, 0),
    };
    private final Coordinate[] STAGE_3 = {
            new Coordinate(-1, -1),
            new Coordinate(-1, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 1),
    };

    public RightSnake(Coordinate coordinate, Board board) {
        this.board = board;
        images[0] = new PieceImage(STAGE_0);
        images[1] = new PieceImage(STAGE_1);
        images[2] = new PieceImage(STAGE_2);
        images[3] = new PieceImage(STAGE_3);
        activeImage = images[0];
        referenceCoordinate = coordinate;
        color = Color.YELLOW;
        for (int i = 0; i < SHAPE_SIZE; i++) {
            squares[i] = new Square(color);
        }
        active = true;
    }
}



