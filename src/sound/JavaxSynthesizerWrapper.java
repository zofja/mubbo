package sound;

import javax.sound.midi.*;
import java.util.*;

/**
 * Default implementation of Synthesizer Interface. Simple and not requiring external libraries, but strongly
 * limited in usability.<br>
 * May need to be replaced in further iterations to allow for multiple instruments of fading sounds, etc.
 */
public class JavaxSynthesizerWrapper implements SynthesizerWrapper {

    /**
     * External helper class to call for note turning off after a delay time has passed.v
     */
    private NoteDeleter nd = new NoteDeleter(this);

    /**
     * Default value of a sound velocity.
     */
    public static final int DEFAULT_VELOCITY = 80;

    /**
     * Midi channel where all sound are pointed to.
     */
    private List<MidiChannel> channels;

    /**
     * Number of available instruments including percussion.
     */
    public static final int NUMBER_OF_INSTRUMENTS = MusicBox.NUMBER_OF_INSTRUMENTS;

    /**
     * Map of available presets: preset name -> List of instruments in MIDI general bank number.
     */
    private static final Map<String, List<Integer>> PRESETS = Map.of(
            "Classic", List.of(1, 48, 4, 54, 7, 56, 14, 55, 62),
            "Electronic", List.of(19, 8, 24, 29, 32, 54, 0, 7, 55)
    );

    /**
     * Name of a default preset.
     * PRESETS.get(DEFAULT_PRESET_NAME) must not be null.
     */
    private static final String DEFAULT_PRESET_NAME = "Classic";

    /**
     * Default constructor.
     *
     * @throws RuntimeException when it was unable to initialize sound module.
     */
    public JavaxSynthesizerWrapper() throws RuntimeException {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            this.channels = new ArrayList<>();
            channels.addAll(Arrays.asList(synth.getChannels()).subList(0, NUMBER_OF_INSTRUMENTS));
            setPreset(DEFAULT_PRESET_NAME);

            Thread.sleep(100);                  // Without a short sleep, first notes sound out of tempo.
            (new Thread()).run();
        } catch (InterruptedException | MidiUnavailableException e) {
            throw new RuntimeException("Unable to initialize sound module.");
        }
    }

    @Override
    public void playNote(int pitch, int instrument) {
        channels.get(instrument).noteOn(pitch, DEFAULT_VELOCITY);
        nd.noteOn(instrument, pitch);
    }

    /**
     * Turn off note as used by external note deleter.
     * @param instrument instrument of a note to turn off.
     * @param pitch note's pitch.
     */
    public void noteOff(int instrument, int pitch) {
        channels.get(instrument).noteOff(pitch, DEFAULT_VELOCITY);
    }

    @Override
    public void playPercussionSound(int sound) {

    }

    @Override
    public void tick() {
        nd.tick();
    }

    public Set<String> getAllPresets() {
        return PRESETS.keySet();
    }

    public void setPreset(String presetName) {
        var preset = PRESETS.getOrDefault(presetName, PRESETS.get(DEFAULT_PRESET_NAME));
        nd.setPreset(preset);
        for (int i = 0; i < preset.size(); i++) {
            channels.get(i).programChange(preset.get(i));
        }
    }

    public void setReverb(int ms) {
        nd.setReverb(ms);
    }

}
