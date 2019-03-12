package core;

import core.particle.Particle;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Automaton {
    private HashMap<Point, LinkedHashSet<Particle>> grid;
    private final int gridSize;
    private final int wall;

    public Automaton(int gridSize) {
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        grid = new HashMap<>();
        initMap(grid);
    }

    // czy to jedyne wyjście? trzeba to tak inicjalizować?
    // i to za każdym razem? na pewno nie,
    // przerobić na mądre dodawanie do mapy, sprawdzające czy jest już taki klucz itd., o
    //TODO FIX initMap
    private void initMap(HashMap<Point, LinkedHashSet<Particle>> map) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                map.put(new Point(x, y), new LinkedHashSet<>());
            }
        }
    }

    // TODO FIX insert - ten new Point za każdym razem, tra-ge-dia
    public void insert(int x, int y, int direction) {
        grid.get(new Point(x, y)).add(new Particle(direction));
    }

    // TESTING PURPOSE ONLY
    public boolean taken(int x, int y) {
        return (grid.get(new Point(x, y)).size() > 0);
    }

    public void nextGeneration() {
        HashMap<Point, LinkedHashSet<Particle>> future = new HashMap<>();
        initMap(future);
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                move(x, y, future);
            }
        }
        grid = future;
    }

    private void move(int x, int y, HashMap<Point, LinkedHashSet<Particle>> future) {
        Point current = new Point(x, y);
        boolean collision = (grid.get(current).size() > 1);

        for (Iterator<Particle> iterator = grid.get(current).iterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();

            if (collision) {
                particle.turn();
            }
            Point destination = particle.destination(current);

            if (!isInBoundaries(destination)) {
                particle.bounce();
            }

            //iterator.remove(); // optional I think?
            future.get(destination).add(particle);
        }
    }

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

    // TEST for DEBUG only, delete later
    private static final char border = '□';
    private static final char glow = '⬛';
    private static final char empty = '.';
    private static final char collision = '◯';
    private static final char error = 'X';

    public void printGrid() {
        System.out.print("  ");
        for (int y = 0; y < gridSize; y++) {
            System.out.print(y + " ");
        }
        System.out.println();

        for (int x = 0; x < gridSize; x++) {
            System.out.print(x + " ");
            for (int y = 0; y < gridSize; y++) {
                System.out.print(getSymbol(x, y) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private char getSymbol(int x, int y) {
        Point current = new Point(x, y);
        if (grid.get(current).size() == 1) {
            if (isInBoundaries(current)) {
                return grid.get(current).iterator().next().getSymbol();
            } else {
                return glow;
            }
        } else if (grid.get(current).size() > 1) {
            if (isInBoundaries(current)) {
                return collision;
            } else {
                return error;
            }
        } else {
            if (isInBoundaries(current)) {
                return empty;
            } else {
                return border;
            }
        }
    }
}
