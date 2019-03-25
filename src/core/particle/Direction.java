package core.particle;

public enum Direction {
    //TODO remove symbol~

    LEFT(0, -1, 0, '<'), UP(1, 0, -1, 'ʌ'),
    RIGHT(2, 1, 0, '>'), DOWN(3, 0, 1, 'v');

    private final int index;
    private final int x;
    private final int y;
    private final char symbol;
    private static final int noDirections = 4;

    Direction(int index, int x, int y, char symbol) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public char getSymbol() {
        return symbol;
    }

    public static int getNoDirections() {
        return noDirections;
    }
}
