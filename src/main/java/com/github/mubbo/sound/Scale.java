package com.github.mubbo.sound;

/**
 * Enumeration storing different scale constants and basic operations on them. Each scale is represented by two fields:
 * name of type String, and scale steps of type int[]. The latter represents scale as number of half-tones
 * (relative pitches) from scale base to every scale step.
 */
public enum Scale {

    /**
     * One of diatonic scale. Ionian mode.
     */
    MAJOR("Major", new int[]{0, 2, 4, 5, 7, 9, 11}),
    /**
     * Diatonic scale. Aeolian mode.
     */
    MINOR("Minor", new int[]{0, 2, 3, 5, 7, 8, 10}),
    /**
     * Diatonic scale. Lydian.
     */
    LYDIAN("Lydian", new int[]{0, 2, 4, 6, 7, 9, 11}),
    /**
     * Major heptatonic<br>
     * I-II-III-V-VI<br>
     * (Omit 4 7) z
     */
    MAJOR_PENTATONIC("Major Pentatonic", new int[]{0, 2, 4, 7, 9}),
    /**
     * Natural minor<br>
     * I-III-IV-V-VII<br>
     * (Omit 2 6)
     */
    MINOR_PENTATONIC("Minor Pentatonic", new int[]{0, 3, 5, 7, 10}),
    /**
     * Fourth mode of the double harmonic scale.
     */
    HUNGARIAN_MINOR("Hungarian Minor", new int[]{0, 2, 3, 6, 7, 8, 11}),
    /**
     * Locrian mode with a major third and major seventh degree.
     */
    PERSIAN("Persian", new int[]{0, 1, 4, 5, 6, 8, 11});

    /**
     * Number of half-tones between two sounds within an octave. (A constant number within western music.) Used to
     * extend range of an scale when number of sounds is greater than scale size, as scales are octave-repeating.
     */
    private static final int PITCHES_IN_OCTAVE = 12;

    /**
     * Default scale set at the beginning of the program.
     */
    public static final Scale DEFAULT_SCALE = Scale.MAJOR_PENTATONIC;

    /**
     * Common scale name as used to display for user to choose from.
     */
    private final String displayName;

    /**
     * Representation of a scale as number of half-tones (relative pitches) from scale base to every scale step.
     * i.e. for Major scale based on C: (C, D, E, F, G, A, B) distance between notes: <br>
     * C - C (scale base and 1st scale step) 0 half-tones,<br>
     * C - D (scale base and 2nd scale step) 2 half-tones (a whole tone),<br>
     * C - E (scale base and 3rd scale step) 4 half-tones (C + whole tone = D, D + whole tone = E),<br>
     * etc.<br>
     * Keep in mind that every scale is octave-repeating (where octave consist of 12 half-tones), i.e. scale MAJOR in
     * fact consists of pitches: ..., -5, -3, -1, 0, 2, 4, 5, 7, 9, 11, 12, 14, 16, ..., while only values modulo 12 are
     * shown within a table.
     */
    private final int[] pitches;

    /**
     * Default and only constructor
     *
     * @param displayName common scale name.
     * @param pitches     representation of scale pitches as relative pitches. See: {@code Scale.pitches}.s
     */
    Scale(String displayName, int[] pitches) {
        this.displayName = displayName;
        this.pitches = pitches;
    }

    /**
     * Find a scale by reverse lookup via {@code displayName} field.
     *
     * @param displayName common name of searched scale
     * @return scale with a {@code displayName} field equal to the given - if exists <p> {@code null} - otherwise
     */
    public static Scale reverseLookupByString(String displayName) {
        for (Scale scale : Scale.values()) {
            if (scale.getDisplayName().equals(displayName)) {
                return scale;
            }
        }
        System.err.println("Couldn't find scale of given display name.");
        return null;
    }

    /**
     * Converts passed scale degree to a relative pitch (number of half-tones from scale base).
     *
     * @param degree scale degree to be converted
     * @return number of half-tones from scale base to given scale degree
     */
    public int scaleDegreeToRelativePitch(int degree) {
        return (pitches[degree % pitches.length] + PITCHES_IN_OCTAVE * (degree / pitches.length));
    }

    /**
     * Display name getter.
     *
     * @return scale common (aka display) name
     */
    public String getDisplayName() {
        return displayName;
    }
}
