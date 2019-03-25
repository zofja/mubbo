package core;

import core.particle.Direction;

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

    // random test
    public void initRandomTest(int noParticles) {
        // TEST
        int i = 0;
        while (i < noParticles) {
            int x = rand.nextInt(gridSize - 2) + 1;
            int y = rand.nextInt(gridSize - 2) + 1;
            int d = rand.nextInt(core.particle.Direction.getNoDirections());
            System.out.println(x + " " + y + " " + d);
            if (!grid.taken(x, y)) {
                grid.insert(x, y, d);
                i++;
            }
        }
        grid.printGrid();
    }

    // to see if particles go in accurate direction
    public void initDirectionTest() {
        grid.insert(1, 1, 2);
        grid.insert(3, 3, 1);
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
