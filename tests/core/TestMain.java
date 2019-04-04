package core;

import gui.Engine;

import javax.swing.*;

public class TestMain {

    private static Symbol[][] randomGrid = Presets.genRandomGrid(8);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Engine(randomGrid));
    }
}
