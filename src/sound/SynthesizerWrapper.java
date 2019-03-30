package sound;

import java.util.Collection;

public interface SynthesizerWrapper {

    void playNote(int pitch);

    default void playNotes(Collection<Integer> currentTickNotes) {
        for (int pitch : currentTickNotes) {
            playNote(pitch);
        }
    }

}