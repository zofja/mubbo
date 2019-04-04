package gui;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    private Grid grid;

    public Window(Grid grid) {

        super("MuBbo");

//        grid = new GridManager();
//        brain = new Brain(9, 60, 1, grid);
        this.grid = grid;


        setSize(1000, 700);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(grid);
        getContentPane().add(new OptionBox(grid), BorderLayout.EAST);

        setVisible(true);


    }

}
