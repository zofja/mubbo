package sound;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class NoteDeleter {

    /**
     * Helper class for holding values of a note: the instrument and the pitch.
     */
    private class Note {
        /**
         * Note's instrument (in current preset).
         */
        public final int instrument;
        /**
         * Note's pitch.
         */
        public final int pitch;

        /**
         * Default constructor.
         * @param instrument an instrument.
         * @param pitch note's pitch.
         */
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

    private List<Integer> currentPreset;

    /**
     * Default value of a sound velocity.
     */
    public static final int DEFAULT_SHORT_TIME_ON = 300;

    public static final int MIN_SHORT_TIME_ON = 25;

    public static final int MAX_SHORT_TIME_ON = 2000;

    private static final Set<Integer> LONG_INSTRUMENTS = Set.of(15, 16, 17, 18, 19, 20, 21, 22, 23, 30, 31, 40, 41, 43, 44, 48, 49, 50, 51, 52, 53, 54, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 96, 97, 99);

    private static final int DEFAULT_SUSTAIN_TIME_ON = 150;

    private static final int MIN_SUSTAIN_TIME_ON = 50;

    private static final int MAX_SUSTAIN_TIME_ON = 400;

    private final JavaxSynthesizerWrapper synth;

    /**
     * Time before turning off the note.
     * Warning: in current implementation must be lower than Engine.intervalDuration.
     */
    private int shortNoteTimeOn = DEFAULT_SHORT_TIME_ON;

    private int sustainNoteTimeOn = DEFAULT_SUSTAIN_TIME_ON;

    private List<Note> currTickNotesOn = new LinkedList<>();

    private ConcurrentMap<Long, Collection<Note>> notesOn;

    private ConcurrentMap<Note, Long> notesUpdates = new ConcurrentHashMap<>();

    private final ScheduledExecutorService noteDeleteScheduler = Executors.newScheduledThreadPool(1);

    public NoteDeleter(JavaxSynthesizerWrapper synth) {
        this.synth = synth;
        notesOn = new ConcurrentHashMap<>();

    }

    private Runnable deleteNotes(long hashSetKey) {
        return () -> {
            var notes = notesOn.remove(hashSetKey);
            for (var n : notes) {
                if (notesUpdates.get(n) == hashSetKey) {
                    notesUpdates.remove(n);
                    synth.noteOff(n.instrument, n.pitch);
                }
            }
        };
    }

    public void noteOn(int instrument, int pitch) {
        currTickNotesOn.add(new Note(instrument, pitch));
    }

    public void tick() {
        if (!currTickNotesOn.isEmpty()) {
            long key = System.currentTimeMillis();
            long keyLong = -System.currentTimeMillis();

            var shortNotes = new LinkedList<>(currTickNotesOn);
            shortNotes.removeIf(note -> LONG_INSTRUMENTS.contains(currentPreset.get(note.instrument)));
            var sustainNotes = new LinkedList<>(currTickNotesOn);
            sustainNotes.removeAll(shortNotes);
            currTickNotesOn.clear();

            notesOn.put(key, shortNotes);
            notesOn.put(keyLong, sustainNotes);
            for (var n : shortNotes) {
                notesUpdates.put(n, key);
            }
            for (var n : sustainNotes) {
                notesUpdates.put(n, keyLong);
            }
            noteDeleteScheduler.schedule(deleteNotes(key), shortNoteTimeOn, TimeUnit.MILLISECONDS);
            noteDeleteScheduler.schedule(deleteNotes(keyLong), sustainNoteTimeOn, TimeUnit.MILLISECONDS);
        }
    }

    public void setPreset(List<Integer> preset) {
        this.currentPreset = preset;
    }

    public void setReverb(int ms) {
        this.shortNoteTimeOn = ms;
        this.sustainNoteTimeOn = (ms > MIN_SUSTAIN_TIME_ON) ? Integer.min(ms, MAX_SUSTAIN_TIME_ON) : MIN_SUSTAIN_TIME_ON;
    }
}
