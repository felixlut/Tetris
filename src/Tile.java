import java.awt.*;

public class Tile {

    private boolean active;     // True if the tile has an active square, i.e. a square which is "falling"
    private final int xPos;
    private final int yPos;
    private final Color color = Color.BLACK;
    private Square square;
    private final int tileLength = 45;

    public Tile (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        square = null;
        active = false;
    }

    /**
     * Set the square of the Tile and activate it as long as the square isn't already occupied. If the
     * square provided is null, the the tile will remove it's square and also inactive it
     * @param square    the square to add
     */
    public void setAndActiveSquare(Square square) {
        this.square = square;
        if (square != null) {
            square.setxPos(xPos);
            square.setyPos(yPos);
            setActive(true);
        } else {
            setActive(false);
        }
    }

    public void setSquare(Square square) {
        this.square = square;
        if (square != null) {
            square.setxPos(xPos);
            square.setyPos(yPos);
        }
    }

    /**
     * Check if the Tile has a non-null square
     * @return      true if the Tile is occupied by a square, false otherwise
     */
    public boolean isFull() {
        return square != null;
    }

    /**
     * Check if the Tile has an active square
     * @return      true if the Tile is occupied by an active square, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get the color of either the square occupying the Tile or the color of the Tile itself
     * @return      Square-color if occupied, Tile-color otherwise
     */
    public Color getColor() {
        if (square == null) {
            return color;
        } else {
            return square.getColor();
        }
    }

    public Square getSquare() {
        return square;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getTileLength() {
        return tileLength;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
