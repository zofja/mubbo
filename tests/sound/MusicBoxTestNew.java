package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;

import java.util.Random;

class MusicBoxTestNew {

    private static final int SIZE = 15;
    private MusicBox mb;

    @BeforeEach
    void setUp() throws MidiUnavailableException {
        mb = new MusicBox(SIZE, SIZE);
    }

    @Test
    void presets() throws InterruptedException {
        for (int i = 0; i < 9; i++) {
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
    void reverb() throws InterruptedException {
        mb.setPreset("Classic");
        mb.setReverb(1000);
        for (int i = 0; i < 9; i++) {
            mb.addNote(1, 0, i);
            mb.tick();
            Thread.sleep(1000);
        }
    }
}