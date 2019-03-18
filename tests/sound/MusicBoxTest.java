package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(true);
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
}