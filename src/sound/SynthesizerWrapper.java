package sound;

import java.util.Collection;
import java.util.HashSet;

/**
 * Interface of a functions required by MusicBox from Synthesizer.
 */
public interface SynthesizerWrapper {

    /**
     * Instantly plays single note. Operation needs to be non-blocking.
     *
     * @param pitch note pitch in MIDI number.
     * @param instrument number of instrument to play note with.
     */
    void playNote(int pitch, int instrument);

    /**
     * Instantly plays single note. Operation needs to be non-blocking.
     *
     * @param pitch note pitch in MIDI number.
     */
    default void playNote(int pitch) {
        playNote(pitch, 0);
    }

    /**
     * Instantly plays multiple notes. Operation needs to be non-blocking.
     *
     * @param currentTickNotes a table of notes to be played at once in MIDI number.
     * @param instrument number of instrument to play note with.
     */
    default void playNotes(Collection<Integer> currentTickNotes, int instrument) {
        for (int pitch : new HashSet<>(currentTickNotes)) {
            playNote(pitch, instrument);
        }
    }

    /**
     * Instantly plays multiple notes. Operation needs to be non-blocking.
     *
     * @param currentTickNotes a table of notes to be played at once in MIDI number.
     */
    default void playNotes(Collection<Integer> currentTickNotes) {
        playNotes(currentTickNotes, 0);
    }

    void tick();
}
