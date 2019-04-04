package gui;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    public CustomLabel(String text, int fontSize) {
        super(text);
        setFont(new Font("Courier", Font.PLAIN, fontSize));
    }
}
