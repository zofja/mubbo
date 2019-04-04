import core.Brain;
import gui.Grid;
import gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

// TODO PROTECTED?

public class Main {

    private static boolean start = false;

    public static void main(String[] args) {

        try {
            gui.Grid grid = new Grid();
            Brain brain = new Brain(9, 60, 1, grid);
            Window window = new Window(brain, grid);
            JButton play = new JButton("PLAY");

            play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   start = true;
                }
            } );

            JPanel panel = new JPanel();
            panel.add(play);
            window.getContentPane().add(panel, BorderLayout.SOUTH);

            while(!start) {
                Thread.sleep(100);
            }

            brain.init(grid.getGrid());
            int length = 60;
            int speed = 1000;
            for (int i = 0; i < length; i++) {
                brain.tick();
//                brain.getCoreGrid().printGrid();
                grid.display(brain.getCoreGrid().displayNext());
                // TEST
                Thread.sleep(speed);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//    public static void go(Brain brain) {
//        int length = 60;
//        int speed = 1;
//        for (int i = 0; i < length; i++) {
//            brain.tick();
//            // TEST
//            try {
//                Thread.sleep(speed);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
