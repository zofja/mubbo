package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;

import java.util.Random;

class MusicBoxTest {

    private static final int SIZE = 15;
    private MusicBox mb;

    @BeforeEach
    void setUp() throws MidiUnavailableException {
        mb = new MusicBox(SIZE, SIZE);
    }

    @Test
    void singleNote() throws InterruptedException {
        mb.addNote(1, 0);
        mb.tick();
        Thread.sleep(1000);
    }

    @Test
    void legendOfZelda() throws InterruptedException {
        int timeQuiver = 500;

        int[] pitches = {8, 5, 8, 8, 9, 10, 11, 12};
        double[] lengths = {1, 1.5, 0.5, 0.25, 0.25, 0.25, 0.25, 2.5};

        for (int i = 0; i < pitches.length; i++) {
            mb.addNote(pitches[i], 0);
            mb.tick();
            Thread.sleep((long) (lengths[i] * timeQuiver));
        }

        mb.changeScale("minor");

        pitches = new int[]{12, 12, 13, 14, 15, 15, 15, 14, 13, 14, 13, 12};
        lengths = new double[]{0.5, 0.33, 0.33, 0.33, 2.5, 0.5, 0.33, 0.33, 0.33, 0.66, 0.33, 2};

        for (int i = 0; i < pitches.length; i++) {
            mb.addNote(pitches[i], 0);
            mb.tick();
            Thread.sleep((long) (lengths[i] * timeQuiver));
        }

        Thread.sleep(3000);
    }

    @Test
    void aLotOfNotes() throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            mb.addNote(i % 10, 0);
        }
        mb.tick();
    }

    @Test
    void randomMelody() throws InterruptedException {
        mb.changeScale("minor pentatonic");
        Random r = new Random();
        int[] delay = {200, 200, 400};
        for (int i = 0; i < 30; i++) {
            mb.addNote(r.nextInt(10), 0);
            mb.tick();
            Thread.sleep(delay[r.nextInt(3)]);
            mb.addNote(r.nextInt(5) + 2, 0);
            mb.tick();
            Thread.sleep(delay[r.nextInt(3)]);
        }
        Thread.sleep(2000);
    }

    @Test
    void limits() throws InterruptedException {
        final int SLEEP_TIME = 500;

        mb.addNote(1, 0);
        mb.addNote(1, SIZE - 1);
        mb.addNote(0, SIZE - 2);
        mb.addNote(SIZE - 1, SIZE - 2);
        mb.tick();
        Thread.sleep(SLEEP_TIME);

        mb.addNote(SIZE - 2, 0);
        mb.addNote(SIZE - 2, SIZE - 1);
        mb.addNote(0, 1);
        mb.addNote(SIZE - 1, 1);
        mb.tick();
        Thread.sleep(SLEEP_TIME);

        Thread.sleep(1000);
    }

}