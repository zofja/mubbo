package gui;

import core.GridManager;
import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Engine {

    private final int intervalDuration = 250;
    private final int gridSize = 9;
    private int iterations = 3000; // time playing = iterations * intervalDuration
    private boolean ifStarted = false;

    private Timer timer = new Timer(intervalDuration, new UpdateListener());
    private GridManager gridManager = new GridManager(gridSize);
    private Grid grid;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public Engine() {

        new StartScreen(new StartPanel("START"));
    }

    public Engine(Symbol[][] gridPreset) { // overloaded constructor to pass Symbol[][] grid

        grid = new Grid(gridSize, gridPreset);
        new StartScreen(new StartPanel("START"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      NESTED PRIVATE CLASSES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private class UpdateListener implements ActionListener {
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

    private class StartPanel extends JPanel {

        StartPanel(String text) {
            StartButton start = new StartButton(text);
            Dimension expectedDimension = new Dimension(300, 650);
            setBackground(Color.BLACK);

            setPreferredSize(expectedDimension);
            setMaximumSize(expectedDimension);

            add(Box.createVerticalGlue());
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxlayout);
            add(start);
            add(Box.createVerticalGlue());
        }

        private class StartButton extends JButton {
            StartButton(String text) {
                super(text);

                setFont(new Font("Courier", Font.PLAIN, 30));
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setBackground(Color.RED);
                addActionListener(new ButtonListener());

            }
        }

        class ButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                new Window(new Grid(gridSize), new PlayPanel("PLAY"));
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

                setSize(new Dimension(500, 50));
                setFont(new Font("Courier", Font.PLAIN, 30));
                addActionListener(new ButtonListener());

            }
        }

        class ButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (!ifStarted) {
                    ifStarted = true;
                    timer.start();
                    gridManager.init(grid.getSymbolGrid()); // przekazuje do braina grid wyklikany przez usera

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
