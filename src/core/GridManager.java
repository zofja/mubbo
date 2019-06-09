package core;

import core.particle.Direction;
import core.particle.Particle;
import sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static sound.MusicBox.NUMBER_OF_INSTRUMENTS;

/**
 * Class responsible for logic of {@code Particles} moving
 * each generation throughout runtime.
 */
public class GridManager {

    /**
     * Number of cells in each row/column.
     */
    private final int gridSize;

    /**
     * Number of row/column in grid
     * which will play a note if it contains {@code Particle}.
     */
    private final int wall;

    /**
     * Number of available instruments.
     */
    private final int instrumentsNumber = NUMBER_OF_INSTRUMENTS;

    /**
     * Main music engine.
     */
    private MusicBox muBbo;

    /**
     * Current placement of particles.
     */
    private List<Particle>[][][] currentGrid;

    /**
     * Placement of particles after
     * one tick (after movement).
     */
    private List<Particle>[][][] nextGrid;

    /**
     * Used for visual representation.
     */
    private static Symbol[][][] theUltimateMusicalGrid;

    /**
     * Used for remembering initial preset.
     */
    private static Symbol[][][] preset;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Instantiates a new Grid manager.
     *
     * @param gridSize number of cells in each row/column.
     */
    public GridManager(int gridSize, String scale, int reverb) {
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        try {
            this.muBbo = new MusicBox(gridSize, gridSize);
            this.muBbo.changeScale(scale);
            this.muBbo.setReverb(reverb);
        } catch (MidiUnavailableException e) {
            System.err.println("Couldn't load sound module.");
        }

        this.currentGrid = initParticleArray();
        this.nextGrid = initParticleArray();

        theUltimateMusicalGrid = new Symbol[gridSize][gridSize][instrumentsNumber];
    }

    public void changeScaleFromManager(String scale) {
        this.muBbo.changeScale(scale);
    }

    public void changeReverbFromManager(int reverb) {
        this.muBbo.setReverb(reverb);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      PUBLIC FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Initializes {@code currentGrid} with setup from user.
     *
     * @param initGrid {@code Symbol} setup of particles on grid from GUI.
     */
    public void init(Symbol[][][] initGrid) {
        preset = initGrid;
        for (int y = 1; y < gridSize - 1; y++) {
            for (int x = 1; x < gridSize - 1; x++) {
                for (int i = 0; i < instrumentsNumber; i++) {
                    if (initGrid[x][y][i] != Symbol.COLLISION) {
                        clear(x, y, i);
                        if (initGrid[x][y][i].ordinal() >= 0 && initGrid[x][y][i].ordinal() <= Direction.getNumberOfDirections()) {
                            insert(x, y, i, initGrid[x][y][i].ordinal());
                        }
                    }
                }
            }
        }
        printGrid();
    }

    /**
     * Initializes {@code currentGrid} with setup from JSON file.
     *
     * @param path {@code Symbol} path to JSON file representing setup of particles on grid.
     */
    public void init(String path) {
        Symbol[][][] initGrid = Presetter.importPreset(path);
        init(initGrid);
    }

    /**
     * Exports {@code preset} (initial setup) to JSON file.
     *
     * @param path {@code Symbol} path to JSON file.
     */
    public void exportPreset(String path) {
        Presetter.exportPreset(preset, path);
    }

    /**
     * Responsible for progressing grid
     * to the next generation and creating output array for GUI.
     */
    public void tick() {
        nextGeneration();
        printGrid();
    }

    /**
     * Translates contents of cells to their visual
     * representations for GUI.
     *
     * @return {@code Symbol} representation of current grid.
     */
    public Symbol[][][] displayNext() {
        for (int i = 0; i < instrumentsNumber; i++) {
            for (int y = 0; y < gridSize; y++) {
                for (int x = 0; x < gridSize; x++) {
                    if (currentGrid[x][y][i].size() == 0) {
                        theUltimateMusicalGrid[x][y][i] = Symbol.EMPTY;
                    } else if (currentGrid[x][y][i].size() == 1) {
                        theUltimateMusicalGrid[x][y][i] = currentGrid[x][y][i].get(0).getSymbol();
                    } else {
                        theUltimateMusicalGrid[x][y][i] = Symbol.COLLISION;
                    }
                }
            }
        }

        return theUltimateMusicalGrid;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      PRIVATE FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Initializes empty array of {@code Particle} of size {@code gridSize}.
     *
     * @return Empty array of {@code Particle}.
     */
    private LinkedList[][][] initParticleArray() {
        LinkedList[][][] arr = new LinkedList[gridSize][gridSize][instrumentsNumber];
        for (int i = 0; i < instrumentsNumber; i++) {
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    arr[x][y][i] = new LinkedList<>();
                }
            }
        }
        return arr;
    }

    /**
     * Responsible for determining next state of cell in the grid
     * based on the current state - moves {@code Particle}
     * between current generation ({@code currentGrid}) and
     * new generation ({@code nextGrid}).
     * <p>
     * Collision happens, when there are 2 or more {@code Particle} in the same cell.
     * It results in 90 degrees clockwise change of {@code Direction} (turn) of each colliding
     * {@code Particle}.
     * <p>
     * If {@code Particle} reaches any of the walls (any coordinate equals {@code 0} or {@code wall}),
     * then {@code Particle} has to make a 180 degrees change of direction (bounce)
     * and play a note.
     *
     * @param x index of column in grid.
     * @param y index of row in grid.
     */
    private void move(int x, int y) {

        for (int i = 0; i < instrumentsNumber; i++) {
            Point current = new Point(x, y);
            boolean collision = (numberOfParticles(x, y, i) >= 2);

            for (ListIterator<Particle> iterator = currentGrid[x][y][i].listIterator(); iterator.hasNext(); ) {
                Particle particle = iterator.next();

                if (collision) {
                    particle.collide();
                }

                Point destination = particle.destination(current);

                if (!isInBoundaries(destination)) {
                    particle.bounce();
                    if (i == 9) {
                        muBbo.addPercussion(destination.x, destination.y);
                    } else {
                        muBbo.addNote(destination.x, destination.y, i);
                    }
                    printCurrentSound(destination);
                }

                iterator.remove();
                nextGrid[destination.x][destination.y][i].add(particle);
            }
        }
    }

    public void clear() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int i = 0; i < instrumentsNumber; i++) {
                    currentGrid[x][y][i].clear();
                }
            }
        }
    }

    private void clear(int x, int y, int i) {
        currentGrid[x][y][i].clear();
    }

    /**
     * Creates new {@code Particle} in grid cell.
     *
     * @param x         index of column in grid.
     * @param y         index of row in grid.
     * @param direction index of direction in {@code Direction.values()} array.
     */
    private void insert(int x, int y, int i, int direction) {
        currentGrid[x][y][i].add(new Particle(direction));
    }

    /**
     * Number of particles currently in cell.
     *
     * @param x index of column in grid.
     * @param y index of row in grid.
     * @return Number of particles currently in cell.
     */
    private int numberOfParticles(int x, int y, int i) {
        return currentGrid[x][y][i].size();
    }

    /**
     * Progresses current grid to the next generation.
     */
    private void nextGeneration() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                move(x, y);
            }
        }

        List<Particle>[][][] t = currentGrid;
        currentGrid = nextGrid;
        nextGrid = t;

        muBbo.tick();
    }

    /**
     * Checks if {@code Particle} should make a sound
     * based on its coordinates.
     * {@code Particle} is out of boudaries if one of it's coordinates
     * is equal to {@code 0} or {@code wall}.
     *
     * @param p coordinates
     * @return true {@code Particle} is in boundaries, it shouldn't make a sound
     * false {@code Particle} is in not boundaries, it should make a sound
     */
    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      TERMINAL OUTPUT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Represents border.
     */
    private static final char border = '□';

    /**
     * Represents making a sound.
     */
    private static final char glow = '■';

    /**
     * Represents empty cell.
     */
    private static final char empty = '.';

    /**
     * Represents cell containg more than one {@code Particle}..
     */
    private static final char collision = '◯';

    /**
     * Represents illegal behaviour.
     */
    private static final char error = 'X';

    /**
     * Helper function printing which cell had just made sound.
     *
     * @param destination - coordinates
     */
    private void printCurrentSound(Point destination) {
        System.out.println("(x: " + destination.x + ", y: " + destination.y + ") making sound");
    }

    /**
     * Prints current grid setting in terminal.
     */
    private void printGrid() {
        System.out.print("  ");
        for (int x = 0; x < gridSize; x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        for (int i = 0; i < instrumentsNumber; i++) {
            for (int y = 0; y < gridSize; y++) {
                System.out.print(y + " ");
                for (int x = 0; x < gridSize; x++) {
                    System.out.print(getSymbol(x, y, i) + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Translates state of cell to character representation.
     *
     * @param x index of column in grid.
     * @param y index of row in grid.
     * @return Character representing state of cell.
     */
    private char getSymbol(int x, int y, int i) {
        Point current = new Point(x, y);
        if (numberOfParticles(x, y, i) == 1) {
            if (isInBoundaries(current)) {
                return currentGrid[x][y][i].get(0).getCharacter();
            } else {
                return glow;
            }
        } else if (numberOfParticles(x, y, i) > 1) {
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
