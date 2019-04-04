import core.GridManager;
import gui.Grid;
import gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Main {

    private final int speed = 1;
    private final int gridSize = 9;

    private int iterations = 60;

    private Timer timer;
    private Grid grid;
    private GridManager gridManager;

    public Main() {

        grid = new Grid(gridSize);
        gridManager = new GridManager(gridSize);

        Window window = new Window(grid); // grid, bo trzeba zmieniać guziki jak się wybierze
        JButton play = new JButton("PLAY");
        play.setFont(new Font("Courier", Font.PLAIN, 50));
        play.addActionListener(new ButtonListener());
        ActionListener updateListener = new UpdateListener();
        JPanel panel = new JPanel();
        panel.add(play);
        window.getContentPane().add(panel, BorderLayout.SOUTH);


        // speed – interval
        timer = new Timer((int) TimeUnit.SECONDS.toMillis(speed), updateListener);
    }

    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            timer.start();
            gridManager.init(grid.getGrid()); // przekazuje do braina grid wyklikany przez usera
        }
    }

    class UpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (iterations == 0) {
                timer.stop();
            } else {
                gridManager.tick();
                grid.display(gridManager.displayNext());
                iterations--;
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new Main()
        );
    }
}
