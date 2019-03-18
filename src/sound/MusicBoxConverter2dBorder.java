package sound;

public class MusicBoxConverter2dBorder implements MusicBoxConverter {

    private Scale currentScale = Scale.DEFAULT_SCALE;
    private final int sizeX;
    private final int sizeY;

    public MusicBoxConverter2dBorder(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public int scaleDegreeToRelativePitch(int degree) {
        return currentScale.scaleDegreeToRelativePitch(degree);
    }

    @Override
    public int coordinatesToScaleDegree(int x) {
        System.err.println("MusicBoxConverter2dBorder.coordinatesToScaleDegree: expected 2 arguments, got 1. " +
                "Auto-converted to (x, 0).");
        return coordinatesToRelativePitch(x, 0);
    }

    @Override
    public int coordinatesToScaleDegree(int x, int y) {
        if (x == 0 || x == this.sizeX + 1) {
            return sizeY - y;
        } else {
            return x - 1;
        }
    }

    @Override
    public void changeScale(String newScale) {
        currentScale = Scale.reverseLookupByString(newScale);
    }
}
