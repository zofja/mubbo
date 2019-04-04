package gui;

import javax.swing.*;
import java.awt.*;

public class OptionBox extends JPanel {

    public OptionBox(Grid grid) {

        setPreferredSize(new Dimension(150, 700));

        add(new CustomLabel("ARROW", 30), BorderLayout.NORTH);
        add(new CheckBoxList(grid), BorderLayout.EAST);
    }
}
