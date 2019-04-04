package gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(Grid grid, JPanel play) {

        super("MuBbo");

        setSize(1000, 700);
        getContentPane().setFont(new Font("Verdana", Font.PLAIN, 50));
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(grid);
        getContentPane().add(new OptionBox(grid), BorderLayout.EAST);
        getContentPane().add(play, BorderLayout.SOUTH);

        setVisible(true);
    }
}
