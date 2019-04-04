package gui;

import javax.swing.*;
import java.awt.*;

public class OptionBox extends JPanel {

    public OptionBox(Grid grid) {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 700));

        add(new CustomLabel("ARROW", 30), BorderLayout.NORTH);
        add(new CheckBoxList(grid), BorderLayout.PAGE_END);
    }
}
