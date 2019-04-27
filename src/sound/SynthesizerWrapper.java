package sound;

import java.util.Collection;

/**
 * Interface of a functions required by MusicBox from Synthesizer.
 */
public interface SynthesizerWrapper {

    /**
     * Instantly plays single note. Operation needs to be non-blocking.
     *
     * @param pitch note pitch in MIDI number.
     */
    void playNote(int pitch);

    /**
     * Instantly plays multiple notes. Operation needs to be non-blocking.
     *
     * @param currentTickNotes a table of notes to be played at once in MIDI number.
     */
    default void playNotes(Collection<Integer> currentTickNotes) {
        for (int pitch : currentTickNotes) {
            playNote(pitch);
        }
    }

}
