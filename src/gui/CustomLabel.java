package gui;

import javax.swing.*;
import java.awt.*;

class CustomLabel extends JLabel {

    CustomLabel(String text, int fontSize) {
        super(text);
        setFont(new Font("Courier", Font.PLAIN, fontSize));
    }
}
