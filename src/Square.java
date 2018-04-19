import java.awt.*;

public class Square {

    private Color color;
    private int xPos;
    private int yPos;
    private int numberInOrder;

    public Square (Color color, int numberInOrder) {
        xPos = -1;
        yPos = -1;
        this.numberInOrder = numberInOrder;
        this.color = color;
    }

    public Square (Color color, int numberInOrder, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.numberInOrder = numberInOrder;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPos() {
        return xPos;
    }

    /**
     * Only for test purposes
     * @return the number this Square have in the Tetromino
     */
    public int getNumberInOrder() {
        return numberInOrder;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}

