package core.particle;

import core.Symbol;

import java.awt.Point;

/**
 * Class representing object moving on the grid.
 * Movement is specified by {@code Direction} class;
 */
public class Particle {
    /**
     * Specifies way of movement of {@code Particle}.
     */
    private Direction direction;

    /**
     * Creates new {@code Particle}.
     * @param index  index of direction in {@code Direction.values()} array.
     */
    public Particle(int index) {
        this.direction = Direction.values()[index];
    }

    /**
     * Makes a single move in direction
     * in which particle is moving.
     * @param point current coordinates
     * @return coordinates after making move
     */
    public Point destination(Point point) {
        Point destination = new Point(point);
        destination.translate(direction.getX(), direction.getY());
        return destination;
    }

    /**
     * Collision results in 90 degrees clockwise turn of {@code Particle}.
     */
    public void collide() {
        int i = direction.getIndex();
        direction = Direction.values()[(direction.getIndex() + 1) % Direction.getNumberOfDirections()];
    }

    /**
     * Hitting a wall (bouncing) results in 180 degrees turn of {@code Particle}.
     */
    public void bounce() {
        direction = Direction.values()[(direction.getIndex() + 2) % Direction.getNumberOfDirections()];
    }

    /**
     * Returns {@code Symbol} representation of particle's direction.
     * @return {@code Symbol} representation of particle's direction.
     */
    public Symbol getSymbol() {
        return direction.getSymbol();
    }

    /**
     * Returns character representation of particle's direction.
     * @return Character representation of particle's direction.
     */
    public char getCharacter() {
        return direction.getCharacter();
    }
}