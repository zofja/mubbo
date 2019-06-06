package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        add(new ArrowList(grid, new CheckListItem[]{
                new CheckListItem("Left"),
                new CheckListItem("Up"),
                new CheckListItem("Right"),
                new CheckListItem("Down")}), BorderLayout.EAST);

        ArrayList<CheckListItem> instruments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            instruments.add(new CheckListItem("Instrument1"));
        }
        add(new InstrumentList(grid, instruments.toArray(CheckListItem[]::new)), BorderLayout.EAST);
    }
}
