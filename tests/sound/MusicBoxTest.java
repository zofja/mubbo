package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;

import java.util.Random;

class MusicBoxTest {

    private MusicBox mb;

    @BeforeEach
    void setUp() throws MidiUnavailableException {
        mb = new MusicBox(15, 15);
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
            mb.instantPlay(r.nextInt(10));
            Thread.sleep(delay[r.nextInt(3)]);
            mb.instantPlay(r.nextInt(5) + 2);
            Thread.sleep(delay[r.nextInt(3)]);
        }
        Thread.sleep(2000);
    }

}