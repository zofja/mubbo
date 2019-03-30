package core.particle;

import core.Symbol;

public enum Direction {
    //TODO remove character~

    LEFT(Symbol.LEFT, -1, 0, '<'), UP(Symbol.UP, 0, -1, 'ʌ'),
    RIGHT(Symbol.RIGHT, 1, 0, '>'), DOWN(Symbol.DOWN, 0, 1, 'v');

    private final Symbol symbol;
    private final int x;
    private final int y;
    private final char character;
    private static final int noDirections = 4;

    Direction(Symbol symbol, int x, int y, char character) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
        this.character = character;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return symbol.ordinal();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public char getCharacter() {
        return character;
    }

    public static int getNoDirections() {
        return noDirections;
    }
}
