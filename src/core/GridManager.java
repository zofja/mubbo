package core;

import core.particle.Direction;
import core.particle.Particle;
import sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GridManager {

    private final int gridSize;
    private final int wall;

    private MusicBox muBbo;

    private List<Particle>[][] prvGrid;
    private List<Particle>[][] nxtGrid;

    /////////////////////////////////////////////////////////
    private static Symbol[][] theUltimateMusicalGrid;
    /////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public GridManager(int gridSize) {
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        try {
            this.muBbo = new MusicBox(gridSize, gridSize);
        } catch (MidiUnavailableException e) {
            System.err.println("Couldn't load sound module.");
        }

        this.prvGrid = initParticleArray();
        this.nxtGrid = initParticleArray();

        theUltimateMusicalGrid = new Symbol[gridSize][gridSize];
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      PUBLIC FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void init(Symbol[][] initGrid) {
        for (int y = 1; y < gridSize - 1; y++) {
            for (int x = 1; x < gridSize - 1; x++) {
                if (initGrid[x][y].ordinal() >= 0 && initGrid[x][y].ordinal() <= Direction.getNoDirections())
                    insert(x, y, initGrid[x][y].ordinal());
            }
        }
        printGrid();
    }

    public void tick() {
        nextGeneration();
        printGrid();
    }

    public Symbol[][] displayNext() {

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (prvGrid[x][y].size() == 0) {
                    theUltimateMusicalGrid[x][y] = Symbol.EMPTY;
                } else if (prvGrid[x][y].size() == 1) {
                    theUltimateMusicalGrid[x][y] = prvGrid[x][y].get(0).getSymbol();
                } else {
                    theUltimateMusicalGrid[x][y] = Symbol.COLLISION;
                }
            }
        }

        return theUltimateMusicalGrid;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      PRIVATE FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private LinkedList[][] initParticleArray() {
        LinkedList[][] arr = new LinkedList[gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                arr[x][y] = new LinkedList<>();
            }
        }
        return arr;
    }

    // the most important function in cell automaton
    private void move(int x, int y) {
        Point current = new Point(x, y);
        boolean collision = (noParticles(x, y) >= 2);

        for (ListIterator<Particle> iterator = prvGrid[x][y].listIterator(); iterator.hasNext(); ) {
            Particle particle = iterator.next();

            if (collision) {
                particle.collide();
            }

            Point destination = particle.destination(current);

            if (!isInBoundaries(destination)) {
                particle.bounce();
                muBbo.addNote(destination.x, destination.y);
                printCurrentSound(destination);
            }

            iterator.remove(); // remove particle from the list
            nxtGrid[destination.x][destination.y].add(particle); // add new destination to nxt grid
        }
    }

    private void insert(int x, int y, int direction) {
        prvGrid[x][y].add(new Particle(direction));
    }

    private int noParticles(int x, int y) {
        return prvGrid[x][y].size();
    }

    private void nextGeneration() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                move(x, y);
            }
        }

        List<Particle>[][] t = prvGrid;
        prvGrid = nxtGrid;
        nxtGrid = t;

        muBbo.tick();
    }

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Pretty terminal output :)
    private static final char border = '□';
    private static final char glow = '■';
    private static final char empty = '.';
    private static final char collision = '◯';
    private static final char error = 'X';

    private void printCurrentSound(Point destination) {
        System.out.println("(x: " + destination.x + ", y: " + destination.y + ") making sound");
    }

    private void printGrid() {
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
                return prvGrid[x][y].get(0).getCharacter();
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
