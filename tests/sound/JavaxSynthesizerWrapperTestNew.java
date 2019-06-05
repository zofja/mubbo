package sound;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaxSynthesizerWrapperTestNew {

    private JavaxSynthesizerWrapper player;

    @BeforeEach
    void setUp() {
        try {
            this.player = new JavaxSynthesizerWrapper();
        } catch (RuntimeException e) {
            fail();
        }
    }

    @AfterEach
    void setDown() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    void MultipleInstrumentsTest() throws InterruptedException {
        for (int i = 0; i < 16; i++) {
            player.tick();
            player.playNote(60, i);
            Thread.sleep(500);
        }
    }

    @Test
    void PercussionTest() throws InterruptedException {
        for (int i = 0; i < 25; i++) {
            System.out.println(i);
            player.playPercussionSound(i);
            Thread.sleep(500);
        }
    }
}