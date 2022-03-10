package com.github.mubbo.sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;
import java.util.Random;

import static com.github.mubbo.sound.MusicBox.NUMBER_OF_INSTRUMENTS;

class MusicBoxTest {

    private static final int SIZE = 15;
    private MusicBox mb;

    @BeforeEach
    void setUp() throws MidiUnavailableException {
        mb = new MusicBox(SIZE, SIZE);
    }

    @Test
    void singleNote() throws InterruptedException {
        mb.addNote(1, 0, new Random().nextInt(NUMBER_OF_INSTRUMENTS));
        mb.tick();
        Thread.sleep(1000);
    }

    private void play(MusicBox mb, int[] pitches, double[] lengths, int i1, int i2, int timeQuiver) throws InterruptedException {
        for (int i = 0; i < pitches.length; i++) {
            mb.addNote(pitches[i], 0, i1);
            mb.addNote(pitches[i], 0, i2);
            mb.tick();
            Thread.sleep((long) (lengths[i] * timeQuiver));
        }
    }

    @Test
    void legendOfZelda() throws InterruptedException {
        int timeQuiver = 500;

        mb.setReverb(25);

        mb.changeScale("Major");

        play(mb,
                new int[]{8, 5, 8, 8, 9, 10, 11, 12},
                new double[]{1, 1.5, 0.5, 0.25, 0.25, 0.25, 0.25, 2.5},
                4, 7, timeQuiver);

        mb.changeScale("Minor");

        play(mb, new int[]{12, 12, 13, 14, 15, 15, 15, 14, 13, 14, 13, 12},
                new double[]{0.5, 0.33, 0.33, 0.33, 2.5, 0.5, 0.33, 0.33, 0.33, 0.66, 0.33, 2},
                2, 0, timeQuiver);

        mb.setReverb(200);
        play(mb, new int[]{12, 11, 11, 12, 13},
                new double[]{1, 0.5, 0.25, 0.25, 2},
                3, 3, timeQuiver);

        mb.setReverb(25);
        play(mb, new int[]{12, 11, 10, 10, 11, 12},
                new double[]{0.5, 0.5, 0.5, 0.25, 0.25, 2},
                6, 6, timeQuiver);

        play(mb, new int[]{11, 10},
                new double[]{0.5, 0.5},
                4, 4, timeQuiver);

        mb.changeScale("Lydian");

        play(mb, new int[]{9, 9, 10, 11, 13, 12},
                new double[]{0.5, 0.25, 0.25, 2, 1, 0.5},
                4, 7, timeQuiver);

//        play(mb, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
//                new double[]{0.25, 0.25, 0.5, 0.25, 0.25, 0.5, 0.25, 0.25, 0.33, 0.33, 0.33},
//                9, 7, timeQuiver);
    }


    @Test
    void aLotOfNotes() {
        for (int i = 0; i < 1000000; i++) {
            mb.addNote(i % 10, 0, 0);
        }
        mb.tick();
    }

    @Test
    void randomMelody() throws InterruptedException {
        mb.changeScale("minor pentatonic");
        Random r = new Random();
        int[] delay = {200, 200, 400};
        int randomInstrument = r.nextInt(NUMBER_OF_INSTRUMENTS);
        for (int i = 0; i < 30; i++) {
            mb.addNote(r.nextInt(10), 0, randomInstrument);
            mb.tick();
            Thread.sleep(delay[r.nextInt(3)]);
            mb.addNote(r.nextInt(5) + 2, 0, randomInstrument);
            mb.tick();
            Thread.sleep(delay[r.nextInt(3)]);
        }
        Thread.sleep(2000);
    }

    @Test
    void limits() throws InterruptedException {
        final int SLEEP_TIME = 500;
        Random r = new Random();
        int randomInstrument = r.nextInt(NUMBER_OF_INSTRUMENTS);

        mb.addNote(1, 0, randomInstrument);
        mb.addNote(1, SIZE - 1, randomInstrument);
        mb.addNote(0, SIZE - 2, randomInstrument);
        mb.addNote(SIZE - 1, SIZE - 2, randomInstrument);
        mb.tick();
        Thread.sleep(SLEEP_TIME);

        mb.addNote(SIZE - 2, 0, randomInstrument);
        mb.addNote(SIZE - 2, SIZE - 1, randomInstrument);
        mb.addNote(0, 1, randomInstrument);
        mb.addNote(SIZE - 1, 1, randomInstrument);
        mb.tick();
        Thread.sleep(SLEEP_TIME);

        Thread.sleep(1000);
    }

    @Test
    void presets() throws InterruptedException {
        for (int i = 0; i < NUMBER_OF_INSTRUMENTS; i++) {
            mb.setPreset("Classic");
            mb.addNote(1, 0, i);
            mb.tick();
            Thread.sleep(1000);
            mb.setPreset("Electronic");
            mb.addNote(1, 0, i);
            mb.tick();
            Thread.sleep(1000);
        }
    }


    @Test
    void shortNodeLongReverb() throws InterruptedException {
        mb.addNote(1, 0, 0);
        mb.tick();
        Thread.sleep(1000);

        mb.setReverb(1000);
        mb.addNote(1, 0, 0);
        mb.tick();
        Thread.sleep(1000);

        Thread.sleep(1000);

        mb.setReverb(2000);
        mb.addNote(1, 0, 0);
        mb.tick();
        Thread.sleep(1000);
        mb.addNote(1, 0, 0);
        mb.tick();
        Thread.sleep(3000);
    }

    @Test
    void sustainNoteLongReverb() throws InterruptedException {
        mb.addNote(1, 0, 3);
        mb.tick();
        Thread.sleep(1000);

        mb.setReverb(1000);
        mb.addNote(1, 0, 3);
        mb.tick();
        Thread.sleep(1000);

        Thread.sleep(1000);

        mb.setReverb(2000);
        mb.addNote(1, 0, 3);
        mb.tick();
        Thread.sleep(1000);
        mb.addNote(1, 0, 3);
        mb.tick();
        Thread.sleep(3000);
    }

    @Test
    void reverb() throws InterruptedException {
        mb.setPreset("Classic");
        mb.setReverb(1000);
        for (int i = 0; i < NUMBER_OF_INSTRUMENTS; i++) {
            mb.addNote(1, 0, i);
            mb.tick();
            Thread.sleep(1000);
        }
    }

    @Test
    void percussion() throws InterruptedException {
        for (int j = 0; j < 3; j++) {
            for (int i = 1; i < 10; i++) {
                mb.addPercussion(i, 0);
                mb.tick();
                Thread.sleep(500);
            }
            Thread.sleep(500);
        }
    }

}