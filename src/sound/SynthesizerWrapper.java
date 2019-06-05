package sound;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
     * @param currentTickInstrument a table of notes to be played at once in MIDI number.
     * @param instrument number of instrument to play note with.
     */
    default void playNotes(Collection<Integer> currentTickInstrument, int instrument) {
        for (int pitch : new HashSet<>(currentTickInstrument)) {
            playNote(pitch, instrument);
        }
    }

    /**
     * Instantly plays multiple notes. Operation needs to be non-blocking.
     *
     * @param currentTickInstrument a table of notes to be played at once in MIDI number.
     */
    default void playNotes(Collection<Integer> currentTickInstrument) {
        playNotes(currentTickInstrument, 0);
    }

    /**
     * Instantly plays single percussion sound. Operation needs to be non-blocking.
     *
     * @param sound number of sound to be played.
     */
    void playPercussionSound(int sound);

    /**
     * Instantly plays multiple percussion sounds. Operation needs to be non-blocking.
     *
     * @param currentTickPercussion a table of percussion sounds to be played at once in MIDI number.
     */
    default void playPercussionSounds(Collection<Integer> currentTickPercussion) {
        for (var sound : currentTickPercussion) {
            playPercussionSound(sound);
        }
    }

    /**
     * Annotate that current tick has passed (to add turning off all current notes tasks in event scheduler.
     */
    void tick();

    /**
     * Sets playback reverb.
     *
     * @param ms reverb value in milliseconds.
     */
    void setReverb(int ms);

    /**
     * @return Set of all instrument presets.
     */
    Set<String> getAllInstrumentPresets();

    /**
     * Sets new instrument preset.
     *
     * @param presetName preset name to be set.
     */
    void setInstrumentPreset(String presetName);

}
