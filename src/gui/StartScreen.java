package gui;

import javax.swing.*;
import java.awt.*;


/*
TODO
1. wybór skali (może być w trakcie grania)
rozwijana lista
    Scale.values[]                         zwraca wszystkie wartości skali (jako klasa)
    skala.getDisplayName()      zwraca Stringa, który możesz wypisać
    musicBox.changeScale(String nazwaSkali)       zmienia skalę na wybraną przez użytkownika
2. wybór presetu (zestawu instrumentu, również może być w trakcie grania)
rozwijana lista
    musicBox.getAllPresets()      zwraca Set<String> listę nazw presetów do wybrania
    musicBox.setPreset(String nazwaPresetu)          zmienia preset na wybrany przez użytkownika
3. pogłos (może być w trakcie grania)
suwak wartości od 25 do 2000
    musicBox.setReverb(int ms)                                    ustawia pogłos
 */

/**
 * Class opening the start screen. An object of class StartScreen displays:
 * - app logo,
 * - start button.
 */
class StartScreen extends JFrame {

    /**
     * Instantiates Start screen.
     *
     * @param startPanel {@code JPanel} object with {@code Logo} and {@code StartPanel} components.
     */
    StartScreen(JPanel startPanel) {
        super("MuBbo");
        setSize(1000, 900);
        setMinimumSize(new Dimension(700, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(startPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
