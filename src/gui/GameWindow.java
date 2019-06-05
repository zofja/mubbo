package gui;

import core.GridManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class opening the app's screen. An object of class GameWindow displays:
 * - grid, where the simulation of generated melody is shown,
 * - option box, which enables a user to change settings such as new arrows direction,
 * - play/pause button.
 */
class GameWindow extends JFrame {

    private Grid grid;
    private GridManager gridManager;

    private final int gridSize = 9;
    private boolean firstTime = true;
    private boolean ifStarted = false;

    public static final int intervalDuration = 250;
    private int iterations = 3000; // time playing = iterations * intervalDuration


    private Timer timer = new Timer(intervalDuration, new UpdateGridAfterTick());

    GameWindow(String scale, int reverb) {
        super("MuBbo");

        grid = new Grid(gridSize);
        gridManager = new GridManager(gridSize, scale, reverb);
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

        PlayPanel play = new PlayPanel();
        getContentPane().add(play, BorderLayout.PAGE_END);

        setVisible(true);
    }

    /**
     * The most important Listener used to refresh grid after each tick.
     */
    private class UpdateGridAfterTick implements ActionListener {

        /**
         * Specifies what happens after each tick.
         *
         * @param event change on grid
         */
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

        /**
         * Instantiates Play panel.
         */
        PlayPanel() {
            PlayButton play = new PlayButton();
            add(play);
        }

        /**
         * Play button component for {@code PlayPanel}.
         */
        private class PlayButton extends JButton {

            /**
             * Instantiates Play button.
             */
            PlayButton() {
                super("PLAY");
                setSize(new Dimension(500, 50));
                setFont(new Font("Courier", Font.PLAIN, 30));
                addActionListener(new PlayButtonListener());
            }
        }

        /**
         * {@code ActionListener} derived class reponsible for showing starting and pausing upon clicking {@code StartButton}.
         */
        class PlayButtonListener implements ActionListener {

            /**
             * Specifies what happens after clicking {@code PlayButton}.
             *
             * @param event play/pause button click
             */
            public void actionPerformed(ActionEvent event) {
                if (!ifStarted) {
                    ifStarted = true;
                    timer.start();
                    gridManager.init(grid.getSymbolGrid());
                    JButton clicked = (JButton) event.getSource();
                    clicked.setText("PAUSE");
                } else {
                    ifStarted = false;
                    timer.stop();
                    JButton clicked = (JButton) event.getSource();
                    clicked.setText("PLAY");
                }
            }
        }
    }
}
