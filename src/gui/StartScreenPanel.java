package gui;

import javax.swing.*;
import java.awt.*;

class StartScreenPanel extends JPanel {

    StartScreenPanel() {
        Dimension expectedDimension = new Dimension(700, 700);
        setBackground(Color.BLACK);
        setPreferredSize(expectedDimension);
        setMaximumSize(expectedDimension);
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);
    }
}
