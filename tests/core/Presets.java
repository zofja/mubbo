package core;

import java.util.Random;

public class Presets {

    private static int gridSize = 9;
    private static Random rand = new Random();

    static Symbol[][] initGrid() {
        var arr = new Symbol[gridSize][gridSize];
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                arr[x][y] = Symbol.EMPTY;
            }
        }
        return arr;
    }

    static Symbol[][] genRandomGrid(int noParticles) {
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
}
