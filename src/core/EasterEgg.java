package core;

import sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;

public class EasterEgg {

    private int gridSize;
    private Symbol[][][] ethDnegelFoAdlez = new Symbol[9][9][9];


    EasterEgg(int gridSize, Symbol[][][] initGrid) {
        this.gridSize = gridSize;
        initZeldaGrid();
        if (gridSize != 9) return;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (initGrid[x][y][0] != ethDnegelFoAdlez[x][y][0]) return;
            }
        }

        try {
            dnegelFoAdlez();
        } catch (Exception e) {
            System.err.println("Sound module not working");
        }
        System.exit(0);
    }

    private void initZeldaGrid() {

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                ethDnegelFoAdlez[x][y][0] = Symbol.EMPTY;
            }
        }

        ethDnegelFoAdlez[1][5][0] = Symbol.UP;
        ethDnegelFoAdlez[2][4][0] = Symbol.UP;
        ethDnegelFoAdlez[2][5][0] = Symbol.UP;
        ethDnegelFoAdlez[3][3][0] = Symbol.UP;
        ethDnegelFoAdlez[3][5][0] = Symbol.UP;
        ethDnegelFoAdlez[4][2][0] = Symbol.UP;
        ethDnegelFoAdlez[4][4][0] = Symbol.UP;
        ethDnegelFoAdlez[4][5][0] = Symbol.UP;
        ethDnegelFoAdlez[5][3][0] = Symbol.UP;
        ethDnegelFoAdlez[5][5][0] = Symbol.UP;
        ethDnegelFoAdlez[6][4][0] = Symbol.UP;
        ethDnegelFoAdlez[6][5][0] = Symbol.UP;
        ethDnegelFoAdlez[7][5][0] = Symbol.UP;
    }


    private void doPlay(MusicBox mb, int[] pitches, double[] lengths, int i1, int i2, int timeQuiver) throws InterruptedException {
        for (int i = 0; i < pitches.length; i++) {
            mb.addNote(pitches[i], 0, i1);
            mb.addNote(pitches[i], 0, i2);
            mb.tick();
            Thread.sleep((long) (lengths[i] * timeQuiver));
        }
    }

    private void dnegelFoAdlez() throws InterruptedException, MidiUnavailableException {
        int timeQuiver = 500;
        var mb = new MusicBox(25, 25);

        mb.setReverb(25);

        mb.changeScale("Major");

        doPlay(mb,
                new int[]{8, 5, 8, 8, 9, 10, 11, 12},
                new double[]{1, 1.5, 0.5, 0.25, 0.25, 0.25, 0.25, 2.5},
                4, 7, timeQuiver);

        mb.changeScale("Minor");

        doPlay(mb, new int[]{12, 12, 13, 14, 15, 15, 15, 14, 13, 14, 13, 12},
                new double[]{0.5, 0.33, 0.33, 0.33, 2.5, 0.5, 0.33, 0.33, 0.33, 0.66, 0.33, 2},
                2, 0, timeQuiver);

        mb.setReverb(200);
        doPlay(mb, new int[]{12, 11, 11, 12, 13},
                new double[]{1, 0.5, 0.25, 0.25, 2},
                3, 3, timeQuiver);

        mb.setReverb(25);
        doPlay(mb, new int[]{12, 11, 10, 10, 11, 12},
                new double[]{0.5, 0.5, 0.5, 0.25, 0.25, 2},
                6, 6, timeQuiver);

        doPlay(mb, new int[]{11, 10},
                new double[]{0.5, 0.5},
                4, 4, timeQuiver);

        mb.changeScale("Lydian");

        doPlay(mb, new int[]{9, 9, 10, 11, 13, 12},
                new double[]{0.5, 0.25, 0.25, 2, 1, 0.5},
                4, 7, timeQuiver);

        doPlay(mb, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                new double[]{0.25, 0.25, 0.5, 0.25, 0.25, 0.5, 0.25, 0.25, 0.33, 0.33, 0.33},
                9, 7, timeQuiver);
    }
}
