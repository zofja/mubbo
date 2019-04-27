package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Class opening the app's screen. An object of class Window displays:
 * - grid, where the simulation of generated melody is shown,
 * - option box, which enables a user to change settings such as new arrows direction,
 * - play/pause button.
 */
class Window extends JFrame {

    /**
     * @param grid an object of class {@code Grid} enabling to show the simulation.
     * @param play a panel containing play button.
     */
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
