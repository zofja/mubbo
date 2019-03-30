package gui;

import core.Brain;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    public static Brain brain;
    private Grid grid;

    public Window() throws InterruptedException {

        super("MuBbo");

        grid = new Grid();
        brain = new Brain(9, 60, 1, grid);


        setSize(1000, 700);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(grid);
        getContentPane().add(new OptionBox(grid, brain), BorderLayout.EAST);

        setVisible(true);


    }

}
