package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class JavaxSynthesizerWrapper implements SynthesizerWrapper {

    public static final int DEFAULT_VELOCITY = 127;
    private final MidiChannel channel;

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
