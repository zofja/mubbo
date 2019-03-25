package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrainTest {

    private final int gridSize = 9;
    private final int speed = 10000;
    private final int length = 100;
    private Brain brain = new Brain(gridSize, speed, length);
    private Grid grid = new Grid(gridSize);


    @Test
    void DirectionTest() {
        grid.insert(1, 1, 2);
        grid.insert(3, 3, 1);
        grid.printGrid();
        brain.go();
    }

    @Test
    void RandomTest() {
        brain.initRandomTest(8);
        brain.go();
    }

    @Test
    void init1() {
    }
}