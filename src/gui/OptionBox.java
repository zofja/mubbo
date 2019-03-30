package gui;

import core.Brain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionBox extends JPanel {

    public OptionBox(Grid grid, Brain brain) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(700, 300);
        add(new JLabel("OPTIONS"));
//        add(new JLabel("rytm"));
//        add(new JSlider());
//        add(new JLabel("skala"));
//        add(new JSlider());
        add(new JLabel("ARROW"));

        add(new CheckBoxList(grid));

        JButton play = new JButton("PLAY");

        play.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                brain.init(grid.getGrid());
            }
        });

        add(play);
    }

}
