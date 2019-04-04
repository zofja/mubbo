import core.Brain;
import gui.Grid;
import gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

// TODO PROTECTED?

public class Test {

    private static boolean start = false;


    private final int length = 60;
    private final int speed = 1;
    private final int gridSize = 9;

    private int iterations = 60;

    Timer timer;
    Brain brain;
    Grid grid;

    public Test() {

        grid = new Grid();

        brain = new Brain(gridSize, length, speed);

        Window window = new Window(brain, grid);
        JButton play = new JButton("PLAY");
        play.addActionListener(new MyButtonListener());

        ActionListener updateListener = new MyUpdateListener();

        JPanel panel = new JPanel();
        panel.add(play);
        window.getContentPane().add(panel, BorderLayout.SOUTH);

        timer = new Timer((int) TimeUnit.SECONDS.toMillis(speed), updateListener);


    }

    class MyButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            System.out.println("Entered start button event handler");

            timer.start();
            brain.init(grid.getGrid());

        }
    }

    class MyUpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (iterations == 0) {
                timer.stop();
            } else {
                brain.tick();
                grid.display(brain.getCoreGrid().displayNext());
                iterations--;
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new Test()
        );
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
