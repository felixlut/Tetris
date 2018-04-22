public class Coordinate {

    private int xPos;
    private int yPos;

    public Coordinate (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void incrementXPos() {
        xPos++;
    }

    public void decrementXPos() {
        xPos--;
    }

    public void incrementYPos() {
        yPos++;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
