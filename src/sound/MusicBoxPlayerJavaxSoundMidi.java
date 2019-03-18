package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MusicBoxPlayerJavaxSoundMidi implements MusicBoxPlayer {

    public static final int DEFAULT_VELOCITY = 127;
    private final MidiChannel channel;

    public MusicBoxPlayerJavaxSoundMidi() throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        this.channel = synth.getChannels()[0];
//        channel.programChange(34);            // optional **cool** guitar sound
    }

    @Override
    public void playNote(int pitch) {
        channel.noteOn(pitch, DEFAULT_VELOCITY);
    }
}
