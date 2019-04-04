/*package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class BrainTest {

    private final int gridSize = 9;
    private final int speed = 1;
    private final int length = 100;
    private final Random rand = new Random();
    private Brain brain;
    private GridManager grid;


    public void initRandomTest(int noParticles) {
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

    public void initMaxTestRandomDirection() {
        for (int y = 1; y < gridSize - 2; y++) {
            for (int x = 1; x < gridSize - 2; x++) {
                int d = rand.nextInt(core.particle.Direction.getNoDirections());
                grid.insert(x, y, d);
            }
        }
        grid.printGrid();
    }

    @BeforeEach
    void setUp() {
        brain = new Brain(gridSize, length, speed);
        grid = new GridManager(gridSize);
    }

    @Test
    void RandomTest() {
        initRandomTest(8);
        brain.go(grid);
    }
}
*/