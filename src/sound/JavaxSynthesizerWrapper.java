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
     * Midi channel where all sounds are pointed to.
     */
    private List<MidiChannel> channels;

    /**
     * Number of available instruments including percussion.
     */
    public static final int NUMBER_OF_INSTRUMENTS = MusicBox.NUMBER_OF_INSTRUMENTS;

    /**
     * Channel number that MIDI operates a percussion on.
     */
    public static final int PERCUSSION_CHANNEL = 9;

    /**
     * Map of available presets: preset name -> List of instruments in MIDI general bank number.
     */
    private static final Map<String, List<Integer>> INSTRUMENTS_PRESETS = Map.of(
            "Classic", List.of(1, 48, 4, 54, 7, 56, 14, 55, 62),
            "Electronic", List.of(19, 8, 24, 29, 32, 54, 0, 7, 55)
    );

    /**
     * Map of available instruments: preset name -> List names of instruments.
     */
    public static final List<String> INSTRUMENTS_NAMES =
            List.of("Piano", "Strings", "E-piano", "Voice", "Harpsichord", "Trombone", "Vibraphone", "Trumpet");

    /**
     * Preset for percussion sounds: list of all distinct sounding MIDI pitches on 10'th channel.
     */
    private static final List<Integer> PERCUSSION_PRESET = List.of(35, 37, 38, 41, 42, 46, 49);

    /**
     * Name of a default preset.
     * INSTRUMENTS_PRESETS.get(DEFAULT_PRESET_NAME) must not be null.
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
            channels.addAll(Arrays.asList(synth.getChannels()).subList(0, NUMBER_OF_INSTRUMENTS + 1));
            setInstrumentPreset(DEFAULT_PRESET_NAME);

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

    @Override
    public void playPercussionSound(int sound) {
        int midiSound = PERCUSSION_PRESET.get(sound % PERCUSSION_PRESET.size());
        channels.get(PERCUSSION_CHANNEL).noteOn(midiSound, DEFAULT_VELOCITY);
        nd.noteOn(PERCUSSION_CHANNEL, midiSound);
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
    public void tick() {
        nd.tick();
    }

    public static Set<String> getAllInstrumentPresets() {
        return INSTRUMENTS_PRESETS.keySet();
    }

    public void setInstrumentPreset(String presetName) {
        var preset = INSTRUMENTS_PRESETS.getOrDefault(presetName, INSTRUMENTS_PRESETS.get(DEFAULT_PRESET_NAME));
        nd.setPreset(preset);
        for (int i = 0; i < preset.size(); i++) {
            channels.get(i).programChange(preset.get(i));
        }
    }

    public void setReverb(int ms) {
        nd.setReverb(ms);
    }

}
