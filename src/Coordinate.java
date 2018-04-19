public class Coordinate {

    private final int xPos;
    private final int yPos;

    public Coordinate (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    @Override
    public String toString() {
        return "(" + xPos + ", " + yPos + ")";
    }
}
