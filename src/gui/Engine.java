package gui;

import core.GridManager;
import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class responsible for drawing new JFrames.
 */
public class Engine {

    private final int intervalDuration = 250;
    private final int gridSize = 9;
    private int iterations = 3000; // time playing = iterations * intervalDuration
    private boolean firstTime = true;
    private boolean ifStarted = false;

    private Timer timer = new Timer(intervalDuration, new UpdateListener());
    private GridManager gridManager = new GridManager(gridSize);
    private Grid grid;

    private StartScreen screen;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Instantiates new Engine object.
     */
    public Engine() {
        screen = new StartScreen(new StartPanel());
    }

    /**
     * Instantiates new Engine object.
     *
     * @param gridPreset grid preset
     */
    public Engine(Symbol[][] gridPreset) {
        grid = new Grid(gridSize, gridPreset);
        screen = new StartScreen(new StartPanel());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      NESTED PRIVATE CLASSES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The most important Listener used to refresh grid after each tick.
     */
    private class UpdateListener implements ActionListener {

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

    /**
     * Panel with Start Screen components.
     */
    private class StartPanel extends JPanel {

        /**
         * Instantiates Start Panel.
         */
        StartPanel() {
            Logo logo = new Logo();
            StartButton start = new StartButton();
            Dimension expectedDimension = new Dimension(700, 700);
            setBackground(Color.BLACK);

            setPreferredSize(expectedDimension);
            setMaximumSize(expectedDimension);

            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);

            add(Box.createVerticalGlue());
            add(logo);
            add(Box.createVerticalGlue());
            add(start);
            add(Box.createVerticalGlue());
        }

        /**
         * Logo component for {@code StartPanel}.
         */
        private class Logo extends JPanel {

            /**
             * Instantiates Logo panel.
             */
            Logo() {
                JLabel textLabel = new JLabel();
                textLabel.setFont(new Font("Courier", Font.PLAIN, 100));
                textLabel.setForeground(Color.WHITE);
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setBackground(Color.BLACK);
                textLabel.setText("MuBbo");
                add(textLabel);
            }
        }

        /**
         * Start button component for {@code StartPanel}.
         */
        private class StartButton extends JButton {

            /**
             * Instantiates Start button.
             */
            StartButton() {
                super("START");
                setFont(new Font("Courier", Font.PLAIN, 30));
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setForeground(Color.WHITE);
                setBackground(Color.GRAY);
                addActionListener(new ButtonListener());
            }
        }

        /**
         * {@code ActionListener} derived class reponsible for showing {@code Window} upon clicking {@code StartButton}.
         */
        class ButtonListener implements ActionListener {

            /**
             * Specifies what happens after clicking {@code StartButton}.
             *
             * @param event start button click
             */
            public void actionPerformed(ActionEvent event) {
                grid = new Grid(gridSize);
                screen.dispose();
                new Window(grid, new PlayPanel());
            }
        }
    }

    /**
     * Panel with components for {@code Window}.
     */
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
                addActionListener(new ButtonListener());
            }
        }

        /**
         * {@code ActionListener} derived class reponsible for showing starting and pausing upon clicking {@code StartButton}.
         */
        class ButtonListener implements ActionListener {

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
