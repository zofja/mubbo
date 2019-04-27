package sound;

public enum Scale {

    MAJOR("Major", new int[]{0, 2, 4, 5, 7, 9, 11}),
    MINOR("Minor", new int[]{0, 2, 3, 5, 7, 8, 10}),
    MAJOR_PENTATONIC("Major Pentatonic", new int[]{0, 2, 4, 7, 9}),
    MINOR_PENTATONIC("Minor Pentatonic", new int[]{0, 3, 5, 7, 10}),
    HUNGARIAN_MINOR("Hungarian Minor", new int[]{0, 2, 3, 6, 7, 8, 11}),
    PERSIAN("Persian", new int[]{0, 1, 4, 5, 6, 8, 11});

    private static final int PITCHES_IN_OCTAVE = 12;
    public static final Scale DEFAULT_SCALE = Scale.MAJOR_PENTATONIC; // change scale here

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

    public int scaleDegreeToRelativePitch(int degree) {
        return (pitches[degree % pitches.length] + PITCHES_IN_OCTAVE * (degree / pitches.length));
    }

    public String getDisplayName() {
        return displayName;
    }
}
