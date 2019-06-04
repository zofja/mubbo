package core;

import sound.MusicBox;

import javax.sound.midi.MidiUnavailableException;

public class EasterEgg {

    private Symbol[][] ethDnegelFoAdlez = new Symbol[9][9];

    {
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                ethDnegelFoAdlez[i][j] = Symbol.EMPTY;
            }
        }
        ethDnegelFoAdlez[1][5] = Symbol.UP;
        ethDnegelFoAdlez[2][4] = Symbol.UP;
        ethDnegelFoAdlez[2][5] = Symbol.UP;
        ethDnegelFoAdlez[3][3] = Symbol.UP;
        ethDnegelFoAdlez[3][5] = Symbol.UP;
        ethDnegelFoAdlez[4][2] = Symbol.UP;
        ethDnegelFoAdlez[4][4] = Symbol.UP;
        ethDnegelFoAdlez[4][5] = Symbol.UP;
        ethDnegelFoAdlez[5][3] = Symbol.UP;
        ethDnegelFoAdlez[5][5] = Symbol.UP;
        ethDnegelFoAdlez[6][4] = Symbol.UP;
        ethDnegelFoAdlez[6][5] = Symbol.UP;
        ethDnegelFoAdlez[7][5] = Symbol.UP;
    }

    public EasterEgg(int gridSize, Symbol[][] initGrid) {
        if (gridSize != 9) return;
        for (int i = 1; i < gridSize - 1; i++) {
            for (int j = 1; j < gridSize - 1; j++) {
                if (initGrid[i][j] != ethDnegelFoAdlez[i][j]) return;
            }
        }

        try {
            dnegelFoAdlez();
        } catch (Exception e) {

        }
        System.exit(0);
    }

    private void doPlay(MusicBox mb, int[] pitches, double[] lengths, int i1, int i2, int timeQuiver) throws InterruptedException {
        for (int i = 0; i < pitches.length; i++) {
            mb.addNote(pitches[i], 0, i1);
            mb.addNote(pitches[i], 0, i2);
            mb.tick();
            Thread.sleep((long) (lengths[i] * timeQuiver));
        }
    }

    void dnegelFoAdlez() throws InterruptedException, MidiUnavailableException {
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
