package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

public class Presetter {

    private static final String presetDirectory = "presets";

    static void exportPreset(Symbol[][][] preset, String path) {
        GsonBuilder builder;
        builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();

        File dir = new File(presetDirectory);
        File presetPath = new File(dir, path);

        try (Writer writer = new FileWriter(presetPath)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(preset, writer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Symbol[][][] importPreset(String path) {
        Symbol[][][] fromJSON = null;

        Gson gson = new Gson();
        File dir = new File(presetDirectory);
        File presetPath = new File(dir, path);

        try (Reader reader = new FileReader(presetPath)) {
            JsonReader jreader = new JsonReader(reader);

            fromJSON = gson.fromJson(jreader, Symbol[][][].class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return fromJSON;
    }
}
