package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Class which enables a user to change settings during the simulation.
 * An object of class OptionBox is a JPanel, containing an object of class {@code CheckboxList}.
 */
class OptionBox extends JPanel {

    /**
     * @param grid an object of class {@code Grid} enabling to show the simulation.
     */
    OptionBox(Grid grid) {

        setPreferredSize(new Dimension(150, 700));

        add(new CustomLabel("ARROW", 30), BorderLayout.NORTH);
        add(new CheckBoxList(grid), BorderLayout.EAST);
    }
}
