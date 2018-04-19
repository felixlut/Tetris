public class Box extends Tetrominoe {

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
