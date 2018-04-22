import java.awt.*;

public class Tile {

    private final int xPos;
    private final int yPos;
    private final Color COLOR = Color.BLACK;
    private Square square;
    private final int TILE_LENGTH = 45;

    public Tile (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        square = null;
    }

    /**
     * Set the square of the Tile. If the square provided is null, the the tile will remove it's current square
     * @param square    the square to add
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Check if the Tile has a non-null square
     * @return      true if the Tile is occupied by a square, false otherwise
     */
    public boolean isFull() {
        return square != null;
    }

    /**
     * Get the COLOR of either the square occupying the Tile or the COLOR of the Tile itself
     * @return      Square-color if occupied, Tile-color otherwise
     */
    public Color getColor() {
        if (square == null) {
            return COLOR;
        } else {
            return square.getColor();
        }
    }

    public Square getSquare() {
        return square;
    }

    public int getTile_Length() {
        return TILE_LENGTH;
    }
}
