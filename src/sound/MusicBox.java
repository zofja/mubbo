package sound;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class MusicBox {

    private static final int DEFAULT_BASE_PITCH = 60; // todo to tutaj?

    private /*todo static?*/ final MusicBoxPlayer player = new MusicBoxPlayer();
    private final int sizeX;
    private final int sizeY;
    private Scale currentScale = Scale.DEFAULT_SCALE;
    private int basePitch = DEFAULT_BASE_PITCH; // todo to też?

    private List<Integer> currentTickNotes = new ArrayList<>();

    public MusicBox(int x, int y) throws MidiUnavailableException {
        this.sizeX = x;
        this.sizeY = y;
    }

    public void changeScale(String scaleDisplayName) {
        Scale newScale = Scale.reverseLookupByString(scaleDisplayName);
        if (newScale != null) {
            currentScale = newScale;
        }
    }

    private int soundCoordinateToScaleDegree(int x, int y) {
        if (x == 0 || x == this.sizeX + 1) {
            return sizeY - y;
        } else {
            return x - 1;
        }
    }

    public void addNote(int x, int y) {
        currentTickNotes.add(currentScale.scaleDegreeToRelativePitch(soundCoordinateToScaleDegree(x, y)) + basePitch);
    }

    public void tick() {
        player.playNotes(currentTickNotes);
        currentTickNotes.clear();
    }


}
