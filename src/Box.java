public class Box extends Tetromino {

    // TODO: 2018-04-20 remove?
    public Box() {
        Coordinate[] coordinates = {
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
        };

        image = new PieceImage(coordinates);
    }
}
