package gui;

import javax.swing.*;
import java.awt.*;

class Window extends JFrame {

    Window(Grid grid, JPanel play) {

        super("MuBbo");

        setSize(1000, 900);
        setMinimumSize(new Dimension(700, 700));
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titlePane = new JPanel();
        titlePane.setSize(700, 50);
        CustomLabel title = new CustomLabel("Position arrows on the grid and click play", 25);
        titlePane.add(title);
        OptionBox box = new OptionBox(grid);

        getContentPane().add(titlePane, BorderLayout.NORTH);
        getContentPane().add(box, BorderLayout.EAST);
        getContentPane().add(grid, BorderLayout.CENTER); // TODO grid size
        getContentPane().add(play, BorderLayout.PAGE_END);

        setVisible(true);
    }
}
