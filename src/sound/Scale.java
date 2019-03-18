package sound;

// todo add more scales

public enum Scale {

    MAJOR("major", new int[]{0, 2, 4, 5, 7, 9, 11}),
    MINOR("minor", new int[]{0, 2, 3, 5, 7, 8, 10}),
    MAJOR_PENTATONIC("major pentatonic", new int[]{0, 2, 4, 7, 9}),
    MINOR_PENTATONIC("minor pentatonic", new int[]{0, 3, 5, 7, 10});
    //    HUNGARIAN_MINOR("Hungarian minor", new int[]{0, 2, 3, 6, 7, 8, 11}),

    private static final int PITCHES_IN_OCTAVE = 12;
    public static final Scale DEFAULT_SCALE = Scale.MAJOR_PENTATONIC;

    private final String displayName;
    private final int[] pitches;

    Scale(String displayName, int[] pitches) {
        this.displayName = displayName;
        this.pitches = pitches;
    }

    public static Scale reverseLookupByString(String displayName) {
        for (Scale scale : Scale.values()) {
            if (scale.getDisplayName().equals(displayName)) {
                return scale;
            }
        }
        System.err.println("Couldn't find scale of given display name.");
        return null;
    }

    public int scaleDegreeToRelativePitch(int scaleDegree) {
        return (pitches[scaleDegree % pitches.length] + PITCHES_IN_OCTAVE * (scaleDegree / pitches.length));
    }

    public String getDisplayName() {
        return displayName;
    }
}
