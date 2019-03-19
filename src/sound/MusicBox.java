package sound;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class MusicBox {

    private static final int DEFAULT_BASE_PITCH = 60;

    private final SynthesizerWrapper player = new JavaxSynthesizerWrapper();
    private Scale currentScale = Scale.DEFAULT_SCALE;
    private int basePitch = DEFAULT_BASE_PITCH;
    private int sizeX;
    private int sizeY;

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

    public void changeSize(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;
    }

    public void setPitch(int newPitch) {
        this.basePitch = newPitch;
    }

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

    public void addNote(int x, int y) {
        currentTickNotes.add(currentScale.scaleDegreeToRelativePitch(soundCoordinateToScaleDegree(x, y)) + basePitch);
    }

    public void tick() {
        player.playNotes(currentTickNotes);
        currentTickNotes.clear();
    }
}
