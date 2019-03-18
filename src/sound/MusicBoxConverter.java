package sound;

public interface MusicBoxConverter {

    int basePitch = 60; // todo abstract it out

    int scaleDegreeToRelativePitch(int degree);

    default int coordinatesToRelativePitch(int x) {
        return this.scaleDegreeToRelativePitch(basePitch + coordinatesToScaleDegree(x));
    }

    default int coordinatesToRelativePitch(int x, int y) {
        return this.scaleDegreeToRelativePitch(basePitch + coordinatesToScaleDegree(x, y));
    }

    int coordinatesToScaleDegree(int x);

    int coordinatesToScaleDegree(int x, int y);

    void changeScale(String scaleDisplayName);
}
