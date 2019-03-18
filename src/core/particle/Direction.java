package src.core.particle;

public enum Direction {
    //TODO remove symbol~

    LEFT(0, 0, -1, '<'), UP(1, -1, 0, 'ʌ'),
    RIGHT(2, 0, 1, '>'), DOWN(3, 1, 0, 'v');

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
