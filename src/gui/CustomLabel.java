package gui;

import javax.swing.*;
import java.awt.*;

/**
 * An object of class CustomLabel is a JLabel with set font.
 */
class CustomLabel extends JLabel {

    /**
     * @param text     label's text.
     * @param fontSize font size.
     */
    CustomLabel(String text, int fontSize) {
        super(text);
        setFont(new Font("Courier", Font.PLAIN, fontSize));
    }
}
