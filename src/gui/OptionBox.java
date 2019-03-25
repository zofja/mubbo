package gui;

import javax.swing.*;

public class OptionBox extends JPanel {

    public OptionBox(Grid grid) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(700, 300);
        add(new JLabel("OPTIONS"));
        add(new JLabel("rytm"));
        add(new JSlider());
        add(new JLabel("skala"));
        add(new JSlider());
        add(new JLabel("ARROW"));

        add(new CheckBoxList(grid));
    }

}
