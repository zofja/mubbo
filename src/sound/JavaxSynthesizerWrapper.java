package sound;

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

    private static final Map<String, List<Integer>> PRESETS = Map.of(
            "Classic", List.of(1, 48, 4, 54, 7, 56, 14, 55, 62),
            "Electronic", List.of(19, 8, 24, 29, 32, 54, 0, 7, 55)
    );

    private static final String DEFAULT_PRESET = "Classic";

    private List<Integer> currentPreset;

    /**
     * Default value of a sound velocity.
     */
    public static final int DEFAULT_TIME_ON = 300;

    private static final Set<Integer> LONG_INSTRUMENTS = Set.of(15, 16, 17, 18, 19, 20, 21, 22, 23, 30, 31, 40, 41, 43, 44, 48, 49, 50, 51, 52, 53, 54, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 96, 97, 99);

    private static final int DEFAULT_LONG_TIME_ON = 100;

    /**
     * Time before turning off the note.
     * Warning: in current implementation must be lower than Engine.intervalDuration.
     */
    private int time_on = DEFAULT_TIME_ON;

    private int long_time_on = DEFAULT_LONG_TIME_ON;

    private ConcurrentMap<Long, Collection<Note>> notesOn;

    private ConcurrentMap<Note, Long> notesUpdates = new ConcurrentHashMap<>();

    private List<Note> currTickNotesOn = new LinkedList<>();

    private final ScheduledExecutorService noteDeleteScheduler = Executors.newScheduledThreadPool(1);

    private Runnable deleteNotes(long hashSetKey) {
        return () -> {
            var notes = notesOn.remove(hashSetKey);
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
            setPreset(DEFAULT_PRESET);

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
            long keyLong = -System.currentTimeMillis();

            var sh = new LinkedList<>(currTickNotesOn);
            sh.removeIf(note -> LONG_INSTRUMENTS.contains(note.instrument));
            var l = new LinkedList<>(currTickNotesOn);
            l.removeAll(sh);

            notesOn.put(key, sh);
            notesOn.put(keyLong, l);
            for (var n : sh) {
                notesUpdates.put(n, key);
            }
            for (var n : l) {
                notesUpdates.put(n, keyLong);
            }
            currTickNotesOn.clear();
            noteDeleteScheduler.schedule(deleteNotes(key), time_on, TimeUnit.MILLISECONDS);
            noteDeleteScheduler.schedule(deleteNotes(keyLong), long_time_on, TimeUnit.MILLISECONDS);
        }
    }

    public Set<String> getAllPresets() {
        return PRESETS.keySet();
    }

    public void setPreset(String presetName) {
        var preset = PRESETS.getOrDefault(presetName, PRESETS.get(DEFAULT_PRESET));
        this.currentPreset = preset;
        for (int i = 0; i < preset.size(); i++) {
            channels.get(i).programChange(preset.get(i));
        }
    }

    public void setReverb(int ms) {
        this.time_on = ms;
        this.long_time_on = Integer.min(ms, DEFAULT_LONG_TIME_ON);

    }

}
