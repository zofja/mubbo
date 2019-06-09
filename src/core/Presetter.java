package core;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

class Presetter {

    static void exportPreset(Symbol[][][] preset, String path) {
        GsonBuilder builder;
        builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();

        try (Writer writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(preset, writer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    static Symbol[][][] importPreset(String path) {
        Symbol[][][] fromJSON = null;

        Gson gson = new Gson();
        try (Reader reader = new FileReader(path)) {
            JsonReader jreader = new JsonReader(reader);

            fromJSON = gson.fromJson(jreader, Symbol[][][].class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return fromJSON;
    }
}
