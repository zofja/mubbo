package core;

import core.particle.Particle;
import sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Grid {
    private final int gridSize;
    private final int wall;

    private /*final*/ MusicBox muBbo;

    private List<Particle>[][] currGrid;
    private List<Particle>[][] newGrid;
    private Symbol[][] next;

    public Grid(int gridSize) {
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        try {
            this.muBbo = new MusicBox(gridSize, gridSize);
        } catch (MidiUnavailableException e) {
            System.err.println("Couldn't load sound module.");
        }

        this.currGrid = new LinkedList[gridSize][gridSize];
        initArray(currGrid);
        this.newGrid = new LinkedList[gridSize][gridSize];
        initArray(newGrid);

        next = new Symbol[gridSize][gridSize];
    }

    private void initArray(List<Particle>[][] arr) {
        for (int x = 0; x < gridSize; x++)
            for (int y = 0; y < gridSize; y++) {
                arr[x][y] = new LinkedList<Particle>();
            }
    }

    public void insert(int x, int y, int direction) {
        currGrid[x][y].add(new Particle(direction));
    }

    // TEST ONLY
    public boolean taken(int x, int y) {
        return (currGrid[x][y].size() > 0);
    }

    private int noParticles(int x, int y) {
        return currGrid[x][y].size();
    }

    public void nextGeneration() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                move(x, y);
            }
        }

        List<Particle>[][] t = currGrid;
        currGrid = newGrid;
        newGrid = t;

        // TODO tu dzieś wyświetlać
        muBbo.tick();
    }

    private void displayNext() {
        next = new Symbol[gridSize][gridSize];

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (currGrid[x][y].size() == 0) {
                    next[x][y] = Symbol.EMPTY;
                } else if (currGrid[x][y].size() == 1) {
                    next[x][y] = currGrid[x][y].get(0).getSymbol();
                } else {
                    next[x][y] = Symbol.COLLISION;
                }
            }
        }
    }


    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

    private void move(int x, int y) {
        Point current = new Point(x, y);
        boolean collision = (noParticles(x, y) >= 2);

        for (ListIterator<Particle> iterator = currGrid[x][y].listIterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();

            if (collision) {
                particle.collide();
            }

            Point destination = particle.destination(current);

            if (!isInBoundaries(destination)) {
                particle.bounce();
                muBbo.addNote(destination.x, destination.y);
                System.out.println("(x: " + destination.x + ", y: " + destination.y + ") making sound");
            }

            iterator.remove();
            newGrid[destination.x][destination.y].add(particle);
        }
    }


    // TODO REMOVE AFTER GUI MERGE everything below & check what above
    private static final char border = '□';
    private static final char glow = '■';
    private static final char empty = '.';
    private static final char collision = '◯';
    private static final char error = 'X';

    public void printGrid() {
        System.out.print("  ");
        for (int x = 0; x < gridSize; x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        for (int y = 0; y < gridSize; y++) {
            System.out.print(y + " ");
            for (int x = 0; x < gridSize; x++) {
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
                return currGrid[x][y].get(0).getCharacter();
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
