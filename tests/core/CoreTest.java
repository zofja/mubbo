package core;

import gui.Engine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Random;


class GridTest {

    private final int gridSize = 9;
    private final int intervalDuration = 1;
    private int iterations = 3000;
    private Random rand;

    @BeforeEach
    void setUp() {
        rand = new Random();
    }

    private Symbol[][] initGrid() {
        var arr = new Symbol[gridSize][gridSize];
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                arr[x][y] = Symbol.EMPTY;
            }
        }
        return arr;
    }

    Symbol[][] getRandomGrid(int noParticles) {
        int i = 0;
        Symbol[][] grid = initGrid();
        while (i < noParticles) {
            int x = rand.nextInt(gridSize - 2) + 1;
            int y = rand.nextInt(gridSize - 2) + 1;
            int d = rand.nextInt(core.particle.Direction.getNoDirections());

            if (grid[x][y] == Symbol.EMPTY) {
                grid[x][y] = Symbol.values()[d];
                i++;
            }
        }
        return grid;
    }

    @Test
    void RandomTest() {
        SwingUtilities.invokeLater(() -> new Engine(getRandomGrid(8)));
    }
}