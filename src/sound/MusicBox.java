package sound;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Sound module main coordinating class. An object of class MusicBox processes coordinates into sound. Playing sound
 * requires two different steps:
 * - add a note (via {@code addNote} function), that is then converted into sound based on set scale
 * - play all added notes: {@code tick}
 * Sample usage:
 * <pre>
 *      {@code
 *      MusicBox mb = new MusicBox(10, 10);
 *      mb.addNote(0, 1);
 *      mb.addNote(1, 0);
 *      mb.tick();              // play two given sounds at once
 *      Thread.sleep(1000)      // wait 1 second
 *      mb.changeScale("Major");
 *      for (int i = 0; i < 8; i++) {   // play C major scale (one sound every half a second)
 *          mb.addNode(i, 0);
 *          Thread.sleep(500);
 *      }
 * }
 * </pre>
 */
public class MusicBox {

    /**
     * Default value of a base pitch in MIDI number. (60 = C4)
     */
    private static final int DEFAULT_BASE_PITCH = 55;

    /**
     * Minimal allowed base pitch value. (21 = A0, which is lowest piano note)
     */
    private static final int MIN_MIDI = 21;

    /**
     * Maximal allowed base pitch value. (84 = C6, which is two octaves below highest piano note)
     */
    private static final int MAX_MIDI = 84;

    /**
     * Number of available instruments including percussion.
     */
    public static final int NUMBER_OF_INSTRUMENTS = 10;

    /**
     * Percussion channel.
     */
    public static final int PERCUSSION_CHANNEL = 9;

    /**
     * Sound producing module.
     */
    private final SynthesizerWrapper player;

    /**
     * Current value of a scale.
     *
     * @see Scale
     */
    private Scale currentScale = Scale.DEFAULT_SCALE;

    /**
     * Current value of a base pitch in MIDI number.
     */
    private int basePitch = DEFAULT_BASE_PITCH;

    /**
     * Horizontal grid size (used in converting coordinates to pitch.
     */
    private int sizeX;

    /**
     * Vertical grid size (used in converting coordinates to pitch.
     */
    private int sizeY;

    /**
     * Notes added since last {@code tick()} call in MIDI number per instrument.
     */
    private List<List<Integer>> currentTickNotes = new ArrayList<>();

    {
        for (int i = 0; i < NUMBER_OF_INSTRUMENTS; i++) {
            currentTickNotes.add(new ArrayList<>());
        }
    }

    /**
     * @param x horizontal grid size.
     * @param y vertical grid size.
     * @throws MidiUnavailableException when it was impossible to init sound module.
     */
    public MusicBox(int x, int y) throws MidiUnavailableException {
        player = new JavaxSynthesizerWrapper();
        this.sizeX = x;
        this.sizeY = y;
    }

    /**
     * Changes current scale by a String of display name.
     *
     * @param scaleDisplayName scale display name.
     */
    public void changeScale(String scaleDisplayName) {
        Scale newScale = Scale.reverseLookupByString(scaleDisplayName);
        if (newScale == null) {
            System.err.println("Couldn't find a scale of given name: " + scaleDisplayName);
        } else {
            currentScale = newScale;
        }
    }

    /**
     * Changes current scale by pointing to enumeration value.
     *
     * @param scale scale enumeration value.
     */
    public void changeScale(Scale scale) {
        if (scale == null) {
            System.err.println("Pointed to a null value scale.");
        } else {
            currentScale = scale;
        }
    }

    /**
     * Changes grid size.
     *
     * @param x new horizontal size.
     * @param y new vertical size.
     */
    public void changeSize(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;
    }

    /**
     * Pitch setter.
     *
     * @param newPitch new pitch value in MIDI number.
     */
    public void setPitch(int newPitch) {
        if (newPitch < MIN_MIDI || newPitch > MAX_MIDI) {
            System.err.println("new pitch not within expected range of [MIN_MIDI; MAX_MIDI] = [" + MIN_MIDI + "; " + MAX_MIDI + "].");
        } else {
            this.basePitch = newPitch;
        }
    }

    /**
     * Adds node in coordinates to play.
     *
     * @param x          x coordinate.
     * @param y          y coordinate.
     * @param instrument no. of instrument
     * @see MusicBox for further explanation.
     */
    public void addNote(int x, int y, int instrument) {
        currentTickNotes.get(instrument).add(currentScale.scaleDegreeToRelativePitch(soundCoordinateToScaleDegree(x, y)) + basePitch);
    }

    /**
     * Adds node in coordinates to play.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @see MusicBox for further explanation.
     */
    public void addNote(int x, int y) {
        addNote(x, y, 0);
    }

    /**
     * Adds percussion node in coordinates to play. As opposed to addNote, percussion notes are not changed by scale.
     * Instead, they are passed to the synthesizer as in a range of [0, size - 3].
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void addPercussion(int x, int y) {
        currentTickNotes.get(PERCUSSION_CHANNEL).add(soundCoordinateToScaleDegree(x, y));
    }

    /**
     * Plays all added notes.
     *
     * @see MusicBox for further explanation.
     */
    public void tick() {
        for (int i = 0; i < NUMBER_OF_INSTRUMENTS; i++) {
            player.playNotes(currentTickNotes.get(i), i);
        }
        player.tick();
        this.clearCurrentNotes();
    }

    public void setReverb(int ms) {
        player.setReverb(ms);
    }

    public Set<String> getAllPresets() {
        return player.getAllPresets();
    }

    public void setPreset(String presetName) {
        player.setPreset(presetName);
    }


    /**
     * Clears all the currentTickNode subarrays. (Performed at each tick.)
     */
    private void clearCurrentNotes() {
        for (int i = 0; i < currentTickNotes.size(); i++) {
            currentTickNotes.get(i).clear();
        }
    }

    /**
     * Converts coordinates into a scale degree as follows:
     * <pre>
     *     {@code
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |         |   0   |   1   |   2   |   3   |  ...  | sizeX-3 |         |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * | sizeY-3 |       |       |       |       |       |         | sizeY-3 |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |    :    |       |       |       |       |       |         |    :    |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |    3    |       |       |       |       |       |         |    3    |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |    2    |       |       |       |       |       |         |    2    |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |    1    |       |       |       |       |       |         |    1    |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |    0    |       |       |       |       |       |         |    0    |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     * |         |   0   |   1   |   2   |   3   |  ...  | sizeX-3 |         |
     * +---------+-------+-------+-------+-------+-------+---------+---------+
     *     }
     * </pre>
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return scale degree as explained above
     */
    private int soundCoordinateToScaleDegree(int x, int y) {
        if (x == 0 || x == this.sizeX - 1) {
            return this.sizeY - y - 2;
        } else {
            if (y != 0 && y != this.sizeY - 1) {
                System.err.println("MusicBox.soundCoordinateToScaleDegree: received coordinates not on border: assuming y = 0.");
            }
            return x - 1;
        }
    }
}
