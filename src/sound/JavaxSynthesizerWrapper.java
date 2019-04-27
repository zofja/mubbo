package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Default implementation of Synthesizer Interface. Simple and not requiring external libraries, but strongly
 * limited in usability.<br>
 * May need to be replaced in further iterations to allow for multiple instruments of fading sounds, etc.
 */
public class JavaxSynthesizerWrapper implements SynthesizerWrapper {

    /**
     * Default value of a sound velocity.
     */
    private static final int DEFAULT_VELOCITY = 100;

    /**
     * Midi channel where all sound are pointed to.
     */
    private final MidiChannel channel;

    /**
     * Default constructor.
     *
     * @throws RuntimeException when it was unable to initialize sound module.
     */
    public JavaxSynthesizerWrapper() throws RuntimeException {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            this.channel = synth.getChannels()[0];
            Thread.sleep(100);                  // Without a short sleep, first notes sound out of tempo.
        } catch (InterruptedException | MidiUnavailableException e) {
            throw new RuntimeException("Unable to initialize sound module.");
        }
    }

    @Override
    public void playNote(int pitch) {
        channel.noteOn(pitch, DEFAULT_VELOCITY);
    }
}
