package src.core;

import src.core.particle.Particle;
import src.sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Grid {
    private final int gridSize;
    private final int wall;

    private /*final*/ MusicBox muBbo;

    private List<Particle>[][] g;
    private List<Particle>[][] n;

    public Grid(int gridSize) {
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        try {
            this.muBbo = new MusicBox(gridSize, gridSize);
        } catch (MidiUnavailableException e) {
            System.err.println("Couldn't load sound module.");
        }

        this.g = new LinkedList[gridSize][gridSize];
        initArray(g);
        this.n = new LinkedList[gridSize][gridSize];
        initArray(n);
    }

    private void initArray(List<Particle>[][] arr) {
        for (int x = 0; x < gridSize; x++)
            for (int y = 0; y < gridSize; y++) {
                arr[x][y] = new LinkedList<Particle>();
            }
    }

    public void insert(int x, int y, int direction) {
        g[x][y].add(new Particle(direction));
    }

    // TEST ONLY
    public boolean taken(int x, int y) {
        return (g[x][y].size() > 0);
    }

    private int noParticles(int x, int y) {
        return g[x][y].size();
    }

    public void nextGeneration() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                move(x, y);
            }
        }

        List<Particle>[][] t = g;
        g = n;
        n = t;

        muBbo.tick();
    }

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

    private void move(int x, int y) {
        Point current = new Point(x, y);
        boolean collision = (noParticles(x, y) >= 2);

        for (ListIterator<Particle> iterator = g[x][y].listIterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();

            if (collision) {
                particle.collide();
            }

            Point destination = particle.destination(current);

            if (!isInBoundaries(destination)) {
                particle.bounce();
                // TODO X Y SWAP
                muBbo.addNote(destination.y, destination.x);
                System.out.println(destination.y + " " + destination.x);
            }

            iterator.remove();
            n[destination.x][destination.y].add(particle);
        }
    }


    // TODO REMOVE AFTER MERGE everything below & check what above
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
        if (noParticles(x, y) == 1) {
            if (isInBoundaries(current)) {
                return g[x][y].get(0).getSymbol();
            } else {
                return glow;
            }
        } else if (noParticles(x, y) > 1) {
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
