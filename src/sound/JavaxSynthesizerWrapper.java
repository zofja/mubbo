package sound;

import gui.Engine;

import javax.sound.midi.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Default implementation of Synthesizer Interface. Simple and not requiring external libraries, but strongly
 * limited in usability.<br>
 * May need to be replaced in further iterations to allow for multiple instruments of fading sounds, etc.
 */
public class JavaxSynthesizerWrapper implements SynthesizerWrapper {

    private class Note {
        public final int instrument;
        public final int pitch;

        public Note(int instrument, int pitch) {
            this.instrument = instrument;
            this.pitch = pitch;
        }

        // will be unique, as pitch in (0, 200) and instrument in [0, 9]
        @Override
        public int hashCode() {
            return instrument + pitch * 31;
        }

        @Override
        public String toString() {
            return "Note: [instrument : " + instrument + ", pitch : " + pitch + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass())
                return false;
            Note other = (Note) obj;
            return this.pitch == other.pitch && this.instrument == other.instrument;
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
     * Midi channel where all sound are pointed to.
     */
    private List<MidiChannel> channels;

    /**
     * Default value of a sound velocity.
     */
    public static final int DEFAULT_TIME_ON = 30;

    /**
     * Time before turning off the note.
     * Warning: in current implementation must be lower than Engine.intervalDuration.
     */
    private int time_on = DEFAULT_TIME_ON;

    private ConcurrentMap<Long, Collection<Note>> notesOn;

    private ConcurrentMap<Note, Long> notesUpdates = new ConcurrentHashMap<>();

    private List<Note> currTickNotesOn = new LinkedList<>();

    private final ScheduledExecutorService noteDeleteScheduler = Executors.newScheduledThreadPool(1);

    private Runnable deleteNotes(long hashSetKey) {
        return () -> {
            var notes = notesOn.remove(hashSetKey);
            System.out.println("Delete " + notes.size() + " notes: " + notes);
            System.out.println();
            for (var n : notes) {
                if (notesUpdates.get(n) == hashSetKey) {
                    notesUpdates.remove(n);
                    channels.get(n.instrument).noteOff(n.pitch, DEFAULT_VELOCITY);
                }
            }
        };
    }


    /**
     * Default constructor.
     *
     * @throws RuntimeException when it was unable to initialize sound module.
     */
    public JavaxSynthesizerWrapper() throws RuntimeException {
        notesOn = new ConcurrentHashMap<>();

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
        }
    }

    @Override
    public void playNote(int pitch, int instrument) {
        channels.get(instrument).noteOn(pitch, DEFAULT_VELOCITY);
        currTickNotesOn.add(new Note(instrument, pitch));
    }

    @Override
    public void tick() {
        if (!currTickNotesOn.isEmpty()) {
            long key = System.currentTimeMillis();

            notesOn.put(key, new LinkedList<>(currTickNotesOn));
            for (var n : currTickNotesOn) {
                notesUpdates.put(n, key);
            }
            currTickNotesOn.clear();
            noteDeleteScheduler.schedule(deleteNotes(key), time_on, TimeUnit.MILLISECONDS);
        }
    }
}
