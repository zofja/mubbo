package gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static Grid grid;

    public Window() {

        super("MuBbo");

        setSize(1000, 700);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new Grid();
        getContentPane().add(grid);
        getContentPane().add(new OptionBox(grid), BorderLayout.EAST);

        setVisible(true);

    }

    public Grid getGrid() {
        return this.grid;
    }

}
