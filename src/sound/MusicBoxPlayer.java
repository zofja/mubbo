package src.sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Collection;

public class MusicBoxPlayer {

    public static final int DEFAULT_VELOCITY = 127;
    private final MidiChannel channel;

    public MusicBoxPlayer() throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        this.channel = synth.getChannels()[0];
        channel.programChange(34);
    }

    public void playNote(int pitch) {
        channel.noteOn(pitch, DEFAULT_VELOCITY);
    }

    public void playNotes(Collection<Integer> currentTickNotes) {
        for (int pitch : currentTickNotes) {
            playNote(pitch);
        }
    }


}
