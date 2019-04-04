package gui;

import core.GridManager;
import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Engine {

    private final int intervalDuration = 1;
    private final int gridSize = 9;
    private int iterations = 3000; // time playing = iterations * intervalDuration

    private Timer timer = new Timer(secondsToMillis(intervalDuration), new UpdateListener());
    private GridManager gridManager = new GridManager(gridSize);
    private Grid grid;

    public Engine() {

        grid = new Grid(gridSize);
        new Window(grid, new PlayPanel("PLAY"));
    }

    public Engine(Symbol[][] gridPreset) { // overloaded constructor to pass Symbol[][] grid

        grid = new Grid(gridSize, gridPreset);
        new Window(grid, new PlayPanel("PLAY"));

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

    private class PlayPanel extends JPanel {

        PlayPanel(String text) {
            PlayButton play = new PlayButton(text);
            add(play);
        }

        private class PlayButton extends JButton {
            PlayButton(String text) {
                super(text);

                setFont(new Font("Courier", Font.PLAIN, 50));
                addActionListener(new ButtonListener());

            }
        }

        class ButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                timer.start();
                gridManager.init(grid.getGrid()); // przekazuje do braina grid wyklikany przez usera
            }
        }
    }

    private int secondsToMillis(int seconds) {
        return 1000 * seconds;
    }
}
