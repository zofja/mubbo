package core;

import core.particle.Direction;

import java.util.concurrent.TimeUnit;

public class Brain {

    private Grid grid;
    private final long length;

    // TEST for testing purposes only
    private final int gridSize;
    private final long speed;

    public Brain(int gridSize, int length, int speed) {
        this.grid = new Grid(gridSize);
        this.gridSize = gridSize;
        this.length = TimeUnit.SECONDS.toMillis(length);
        this.speed = TimeUnit.SECONDS.toMillis(speed);
    }

    // TODO test, solve empty cell index problem
    public void init(Symbol[][] arr) {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (arr[x][y].ordinal() >= 0 && arr[x][y].ordinal() <= Direction.getNoDirections())
                    grid.insert(x, y, arr[x][y].ordinal());
            }
        }
        grid.printGrid();
        go(this.grid);
    }

    public void go(Grid grid) {
        for (int i = 0; i < length; i++) {
            tick(grid);
            // TEST
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick(Grid grid) {
        grid.nextGeneration();
        grid.printGrid();
    }
}
