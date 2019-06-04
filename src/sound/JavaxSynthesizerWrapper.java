package sound;

import gui.Engine;

import javax.sound.midi.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Default implementation of Synthesizer Interface. Simple and not requiring external libraries, but strongly
 * limited in usability.<br>
 * May need to be replaced in further iterations to allow for multiple instruments of fading sounds, etc.
 */
public class JavaxSynthesizerWrapper implements SynthesizerWrapper {

    private class Note {
        public final int instrument;
        public final int pitch;
        private final long onTime;

        public Note(int instrument, int pitch) {
            this.instrument = instrument;
            this.pitch = pitch;
            this.onTime = System.currentTimeMillis();
        }

        public boolean isDue() {
            return System.currentTimeMillis() < this.onTime + TIME_ON;
        }
    }

    /**
     * Number of available instruments including percussion.
     */
    public static final int NUMBER_OF_INSTRUMENTS = MusicBox.NUMBER_OF_INSTRUMENTS;

    private static final int[] PRESET1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Default value of a sound velocity.
     */
    public static final int DEFAULT_VELOCITY = 80;

    /**
     * Time before turning off the note.
     * Warning: in current implementation must be lower than Engine.intervalDuration.
     */
    private static final int TIME_ON = 100;

    /**
     * Midi channel where all sound are pointed to.
     */
    private List<MidiChannel> channels;

    private Queue<Note> notesOn;

    /**
     * Default constructor.
     *
     * @throws RuntimeException when it was unable to initialize sound module.
     */
    public JavaxSynthesizerWrapper() throws RuntimeException {
        assert TIME_ON < Engine.intervalDuration;
        notesOn = new LinkedList<>();

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            this.channels = new ArrayList<>();
            channels.addAll(Arrays.asList(synth.getChannels()).subList(0, NUMBER_OF_INSTRUMENTS));
            for (int i = 0; i < NUMBER_OF_INSTRUMENTS - 1; i++) {
                channels.get(i).programChange(PRESET1[i]);
            }

            Thread.sleep(100);                  // Without a short sleep, first notes sound out of tempo.
            (new Thread()).run();
        } catch (InterruptedException | MidiUnavailableException e) {
            throw new RuntimeException("Unable to initialize sound module.");
//        } catch (InvalidMidiDataException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void playNote(int pitch, int instrument) {
        channels.get(instrument).noteOn(pitch, DEFAULT_VELOCITY);
        notesOn.add(new Note(instrument, pitch));
    }

    @Override
    public void tick() {
        new Thread(() -> {
            try {
                Thread.sleep(TIME_ON);
                while (!notesOn.isEmpty()) {
                    Note n = notesOn.poll();
                    channels.get(n.instrument).noteOff(n.pitch, DEFAULT_VELOCITY);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
