public class Pyramid extends Tetromino {


    private final Coordinate[] LINE0 = {
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
    };

    private final Coordinate[] LINE1 = {
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
    };

    private final Coordinate[] LINE2 = {
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
    };

    private final Coordinate[] LINE3 = {
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
            new Coordinate(0, 0),
    };

    public Pyramid() {
        Coordinate[] coordinates0 = {
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
        };

        image = new PieceImage(coordinates0);
    }
}
