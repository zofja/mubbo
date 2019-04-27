package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.MidiUnavailableException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JavaxSynthesizerWrapperTest {

    private JavaxSynthesizerWrapper player;

    @BeforeEach
    void setUp() {
        try {
            this.player = new JavaxSynthesizerWrapper();
        } catch (RuntimeException e) {
            fail();
        }
    }

    @Test
    void parallelSetUp() throws MidiUnavailableException {
        JavaxSynthesizerWrapper player2 = new JavaxSynthesizerWrapper();
        assertTrue(true);
    }

    @Test
    void singleNote() throws InterruptedException {
        player.playNote(60);
        Thread.sleep(1000);
        assertTrue(true);
    }

    @Test
    void miltipleNotes() throws InterruptedException {
        player.playNote(60);
        player.playNote(61);
        Thread.sleep(1000);
        assertTrue(true);
    }

    @Test
    void miltipleNotesAsynchronous() throws InterruptedException {
        player.playNote(60);
        Thread.sleep(500);
        player.playNote(61);
        Thread.sleep(1000);
        assertTrue(true);
    }

    @Test
    void miltipleNotes2() throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(60);
        list.add(61);
        player.playNotes(list);
        Thread.sleep(1000);
        assertTrue(true);
    }

}