import java.awt.*;

public abstract class Tetromino {

    protected final int SHAPE_SIZE = 4;
    protected Square[] squares = new Square[4];
    protected Color color;
    protected PieceImage[] images = new PieceImage[4];
    protected PieceImage activeImage;
    protected int imageNumber;
    protected Coordinate referenceCoordinate;
    protected Board board;
    protected boolean active;

    /**
     * Try to move down the tetromino
     * @return      true if the tetromino was moved down, false otherwise
     */
    public boolean moveDown() {
        if (active) {
            // Save the potential coordinate
            Coordinate potentialReferenceCord = new Coordinate(
                    referenceCoordinate.getxPos(),
                    referenceCoordinate.getyPos()
            );

            // Move the potential coordinate down a row
            potentialReferenceCord.incrementYPos();

            // Check if the active image can be moved down with the potential coordinate
            if (validImageCoordinate(activeImage, potentialReferenceCord)) {
                referenceCoordinate = potentialReferenceCord;
                return true;
            } else {
                board.fillSquares(getCurrentCoordinates());
                active = false;
                return false;
            }
        }
        return false;
    }

    /**
     * Make a horizontal move for the tetromino
     * @param right     specify if it's a right or left move
     * @return          true if the tetromino was moved down, false otherwise
     */
    public boolean moveHorizontal(boolean right) {
        // Save the potential coordinate
        Coordinate potentialReferenceCord = new Coordinate(
                referenceCoordinate.getxPos(),
                referenceCoordinate.getyPos()
        );

        // Move the potential coordinate horizontally a column
        // Check if it's a right or a left move
        if (right) {
            potentialReferenceCord.incrementXPos();
        } else {
            potentialReferenceCord.decrementXPos();

        }

        // Check if the active image can be moved horizontally with the potential coordinate
        if (validImageCoordinate(activeImage, potentialReferenceCord)) {
            referenceCoordinate = potentialReferenceCord;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to rotate the tetromino
     */
    public void rotate() {
        // Save the potential image and imageNumber
        PieceImage potentialImage = null;
        int potentialImageNumber = imageNumber;

        // Set the potential image to the next rotated stage and alter the potentialImageNumber to the next stage
        switch (potentialImageNumber) {
            case 0:
                potentialImage = images[potentialImageNumber + 1];
                potentialImageNumber++;
                break;
            case 1:
                potentialImage = images[potentialImageNumber + 1];
                potentialImageNumber++;
                break;
            case 2:
                potentialImage = images[potentialImageNumber + 1];
                potentialImageNumber++;
                break;
            case 3:
                potentialImage = images[0];
                potentialImageNumber = 0;
                break;
        }
        // If the potential image with the current referenceCoordinate yields a valid state, go through with the rotation
        if (validImageCoordinate(potentialImage, referenceCoordinate)) {
            activeImage = potentialImage;
            imageNumber = potentialImageNumber;
        }
    }

    private boolean validImageCoordinate(PieceImage pieceImage, Coordinate coordinate) {
        for (Coordinate cord : pieceImage.getImage()) {
            Coordinate potentialCord = new Coordinate(
                    coordinate.getxPos() + cord.getxPos(),
                    coordinate.getyPos() + cord.getyPos()
            );
            if (!validCoordinate(potentialCord)) {
                return false;
            }
        }
        return true;
    }

    private boolean validCoordinate (Coordinate potentialCord) {
        return board.isCordOnBoard(potentialCord) && !board.occupiedCoordinate(potentialCord);
    }

    public Coordinate[] getCurrentCoordinates() {
        Coordinate[] currentCoordinates = new Coordinate[4];
        int i = 0;
        for (Coordinate cord : activeImage.getImage()) {
            currentCoordinates[i] = new Coordinate(
                    referenceCoordinate.getxPos() + cord.getxPos(),
                    referenceCoordinate.getyPos() + cord.getyPos()
            );
            i++;
        }

        return currentCoordinates;
    }

    public Square[] getSquares() {
        return squares;
    }

    public Color getColor() {
        return color;
    }

    public boolean isActive() {
        return active;
    }
}
