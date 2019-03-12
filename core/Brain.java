package core;

import java.util.Random;

public class Brain {
    private Automaton grid;
    private final int length;

    // TEST for testing purposes only
    Random rand = new Random(1234);
    private final int gridSize;
    private final int speed;

    public Brain(int gridSize, int length, int speed) {
        this.grid = new Automaton(gridSize);
        this.length = length;
        this.gridSize = gridSize;
        this.speed = 1000 * speed;
    }

    public void init(int noParticles) {
        // TEST
        int i = 0;
        while (i < noParticles) {
            int x = rand.nextInt(gridSize - 2) + 1;
            int y = rand.nextInt(gridSize - 2) + 1;
            int d = rand.nextInt(core.particle.Direction.getNoDirections());
            System.out.println(x + " " + y + " " + d);
            if (!grid.taken(x, y)) {
                grid.insert(x, y, d);
            }
            i++;
        }
        grid.printGrid();
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
