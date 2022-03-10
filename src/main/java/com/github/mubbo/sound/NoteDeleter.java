package com.github.mubbo.sound;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static com.github.mubbo.sound.JavaxSynthesizerWrapper.PERCUSSION_CHANNEL;

/**
 * Helper class used by JavaxSynth to perform a TurnOff MIDI action after desired amount of time.
 * Also handles the difference between sustain and non-sustain notes and reverb time.
 */
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
         *
         * @param instrument an instrument.
         * @param pitch      note's pitch.
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

    /**
     * Current preset.
     */
    private List<Integer> currentPreset;

    /**
     * Instruments (in MIDI general bank number) that are producing sustaining, not fading away, sound.
     */
    private static final Set<Integer> SUSTAIN_INSTRUMENTS = Set.of(15, 16, 17, 18, 19, 20, 21, 22, 23, 30, 31, 40, 41, 43, 44, 48, 49, 50, 51, 52, 53, 54, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 96, 97, 99);

    /**
     * Default time of a non-sustain instrument sound (later referred as a fade) before firing nodeOff.
     */
    public static final int DEFAULT_FADE_TIME_ON = 300;

    /**
     * Min. time of a non-sustain instrument sound (later referred as a fade) before firing nodeOff.
     */
    public static final int MIN_FADE_TIME_ON = 25;

    /**
     * Max. time of a non-sustain instrument sound (later referred as a fade) before firing nodeOff.
     */
    public static final int MAX_FADE_TIME_ON = 2000;

    /**
     * Default time of a sustain instrument sound (later referred as a sustain) before firing nodeOff.
     */
    private static final int DEFAULT_SUSTAIN_TIME_ON = 150;

    /**
     * Min. time of a sustain instrument sound (later referred as a sustain) before firing nodeOff.
     */
    private static final int MIN_SUSTAIN_TIME_ON = 50;

    /**
     * Max. time of a sustain instrument sound (later referred as a sustain) before firing nodeOff.
     */
    private static final int MAX_SUSTAIN_TIME_ON = 400;

    /**
     * Mother class synthesizer.
     */
    private final JavaxSynthesizerWrapper synth;

    /**
     * Current time before turning off the fade sound.
     */
    private int fadeNoteTimeOn = DEFAULT_FADE_TIME_ON;

    /**
     * Current time before turning off the sustain sound.
     */
    private int sustainNoteTimeOn = DEFAULT_SUSTAIN_TIME_ON;

    /**
     * List of notes turned on since last tick().
     */
    private List<Note> currTickNotesOn = new LinkedList<>();

    /**
     * For a given tick, list of all notes that has to be turned off (as passed to SchedulerExecutorService).
     */
    private ConcurrentMap<Long, Collection<Note>> notesOn;

    /**
     * FOr every note: hash of a last tick that turned it on. Used to check if note wasn't turned on twice.
     * Then we have to ignore turnOff try, as MIDI controllers doesn't support stacking turning on of the same note.
     */
    private ConcurrentMap<Note, Long> notesUpdates = new ConcurrentHashMap<>();

    /**private
     * Coordinating scheduler that turns off notes.
     */
    private final ScheduledExecutorService noteDeleteScheduler = Executors.newScheduledThreadPool(1);

    /**
     * Default constructor invoked by SynthesizerWrapper with self (this) as an argument.
     *
     * @param synth the synthesizer.
     */
    public NoteDeleter(JavaxSynthesizerWrapper synth) {
        this.synth = synth;
        notesOn = new ConcurrentHashMap<>();

    }

    /**
     * Generates a function passed to scheduler with custom hashSetKey value.
     *
     * @param hashSetKey key to a hash set for a given tick and notes length.
     * @return a function to be passed to scheduler.
     */
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

    /**
     * Receive the signal from synthesizer that it has turned on given note at this tick.
     *
     * @param instrument instrument number of a note.
     * @param pitch      pitch number of a note.
     */
    public void noteOn(int instrument, int pitch) {
        currTickNotesOn.add(new Note(instrument, pitch));
    }

    /**
     * Helper function to schedule given list of notes.
     * @param notes notes collection to be scheduled to turnOff.
     * @param key hashSetKey for notesOn insertion.
     * @param time time for notes to be scheduled to.
     */
    private void addIfNotEmpty(Collection<Note> notes, long key, int time) {
        if (!notes.isEmpty()) {
            notesOn.put(key, notes);
            for (var n : notes) {
                notesUpdates.put(n, key);
            }
            noteDeleteScheduler.schedule(deleteNotes(key), time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Schedule all notes passed since last tick() for a turnOff.
     */
    public void tick() {
        if (!currTickNotesOn.isEmpty()) {
            long keyFade = System.currentTimeMillis();
            long keySustain = -System.currentTimeMillis();

            var fadeNotes = new LinkedList<>(currTickNotesOn);
            fadeNotes.removeIf(note -> note.instrument == PERCUSSION_CHANNEL || SUSTAIN_INSTRUMENTS.contains(currentPreset.get(note.instrument)));
            var sustainNotes = new LinkedList<>(currTickNotesOn);
            sustainNotes.removeAll(fadeNotes);
            currTickNotesOn.clear();

            addIfNotEmpty(fadeNotes, keyFade, fadeNoteTimeOn);
            addIfNotEmpty(sustainNotes, keySustain, sustainNoteTimeOn);
        }
    }

    /**
     * Annotate that the preset in synthesizer has changed.
     * Important for the NoteDeleter as it holds information of all sustain instruments.
     * @param preset the set preset.
     */
    public void setPreset(List<Integer> preset) {
        this.currentPreset = preset;
    }

    /**
     * Change reverb setting.
     * @param ms new reverb time.
     */
    public void setReverb(int ms) {
        this.fadeNoteTimeOn = ms;
        this.sustainNoteTimeOn = (ms > MIN_SUSTAIN_TIME_ON) ? Integer.min(ms, MAX_SUSTAIN_TIME_ON) : MIN_SUSTAIN_TIME_ON;
    }
}
