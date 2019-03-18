package src.core;

import src.core.particle.Direction;
import src.sound.MusicBox;

import java.util.Random;

public class Brain {
    private Grid grid;
    private final int length;

    // TEST for testing purposes only
    Random rand = new Random();
    private final int gridSize;
    private final int speed;

    public Brain(int gridSize, int length, int speed) {
        this.grid = new Grid(gridSize);
        this.length = length;
        this.gridSize = gridSize;
        this.speed = 100 * speed;
    }

    // random for test purposes
    public void init(int noParticles) {
        // TEST
        int i = 0;
        while (i < noParticles) {
            int x = rand.nextInt(gridSize - 2) + 1;
            int y = rand.nextInt(gridSize - 2) + 1;
            int d = rand.nextInt(src.core.particle.Direction.getNoDirections());
            System.out.println(x + " " + y + " " + d);
            if (!grid.taken(x, y)) {
                grid.insert(x, y, d);
                i++;
            }
        }
        grid.printGrid();
    }

    // TODO test, solve empty cell index problem
    public void init(int[][] arr) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (arr[x][y] >= 0 && arr[x][y] <= Direction.getNoDirections())
                    grid.insert(x, y, arr[x][y]);
            }
        }
    }


    public void go() {
        for (int i = 0; i < length; i++) {
            tick();
            // TEST
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        grid.nextGeneration();
        grid.printGrid();
    }
}
