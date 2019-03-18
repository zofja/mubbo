package sound;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class MusicBox {

    private final MusicBoxPlayer player = new MusicBoxPlayerJavaxSoundMidi();
    private final MusicBoxConverter converter;

    private List<Integer> currentTickNotes = new ArrayList<>();

    public MusicBox(int x, int y) throws MidiUnavailableException {
        converter = new MusicBoxConverter2dBorder(x, y);
    }

    public void changeScale(String scaleDisplayName) {
        converter.changeScale(scaleDisplayName);
    }

    // for debug purposes
    public void instantPlay(int degree) {
        player.playNote(converter.scaleDegreeToRelativePitch(degree));
    }

    public void addNote(int x, int y) {
        currentTickNotes.add(converter.coordinatesToScaleDegree(x, y));
    }

    public void tick() {
        player.playNotes(currentTickNotes);
        currentTickNotes.clear();
    }
}
