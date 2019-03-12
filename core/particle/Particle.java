package core.particle;

import java.awt.Point;

public class Particle {
    private Direction direction;

    private static final Direction[] directions = Direction.values();
    private static final int noDirections = Direction.getNoDirections();

    public Particle(int index) {
        this.direction = directions[index];
    }

    public Point destination(Point point) {
        Point destination = new Point(point);
        destination.translate(direction.getX(), direction.getY());
        return destination;
    }

    // turn 90 degrees clockwise
    public void turn() {
        direction = directions[(direction.getIndex() + 1) % noDirections];
    }

    public void bounce() {
        direction = directions[(direction.getIndex() + 2) % noDirections];
        //TUTAJ BYŁOBY MUSICBOX.PLAY();
    }

    public String getDirectionName() {
        return direction.name();
    }

    public char getSymbol() {
        return direction.getSymbol();
    }
}