package core.particle;

import core.Symbol;

/**
 * Enumeration representing symbols with coordinates.
 */
public enum Direction {
    LEFT(Symbol.LEFT, -1, 0, '<'), UP(Symbol.UP, 0, -1, 'ʌ'),
    RIGHT(Symbol.RIGHT, 1, 0, '>'), DOWN(Symbol.DOWN, 0, 1, 'v');

    /**
     * Symbol representation.
     */
    private final Symbol symbol;

    /**
     * Progress in x axis.
     */
    private final int x;

    /**
     * Progress in y axis.
     */
    private final int y;

    /**
     * Character representation.
     */
    private final char character;

    /**
     * Number of symbols.
     */
    private static final int numberOfDirections = 4;

    /**
     * Creates new {@code Direction}.
     * @param symbol symbol representation.
     * @param x progress in x axis.
     * @param y progress in y axis.
     * @param character character representation.
     */
    Direction(Symbol symbol, int x, int y, char character) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
        this.character = character;
    }

    /**
     * Progress in x axis.
     * @return progress in x axis.
     */
    public int getX() {
        return x;
    }

    /**
     * Progress in y axis.
     * @return progress in y axis.
     */
    public int getY() {
        return y;
    }

    /**
     * Index of symbol in {@code Symbol} enum.
     * @return index of symbol in {@code Symbol} enum.
     */
    public int getIndex() {
        return symbol.ordinal();
    }

    /**
     * Returns {@code Symbol} representation of direction.
     * @return {@code Symbol} representation of direction.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Returns character representation of direction.
     * @return Character representation of direction.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Number of symbols.
     * @return number of symbols.
     */
    public static int getNumberOfDirections() {
        return numberOfDirections;
    }
}
