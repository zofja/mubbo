package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Class opening the start screen. An object of class StartScreen displays:
 * - app logo,
 * - start button.
 */
class StartScreen extends JFrame {

    /**
     * Instantiates Start screen.
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
