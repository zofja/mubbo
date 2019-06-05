package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * An object of class CustomLabel is a JLabel with set font.
 */
class CustomLabel extends JLabel {

    /**
     * @param text     label's text.
     * @param fontSize font size.
     */
    CustomLabel(String text, float fontSize) {
        super(text);
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("assets/fonts/IBM_Plex_Mono/IBMPlexMono-Regular.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(fontSize);
            setFont(font);
        } catch (FontFormatException | IOException e) {
            System.err.println("Couldn't load desired font.");
            System.exit(ERROR);
        }
    }
}
