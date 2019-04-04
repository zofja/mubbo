package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static core.Symbol.*;

class Grid extends JPanel {

    private Symbol selected_symbol = EMPTY;
    private ImageIcon selected;

    private JButton[][] gridGUI;
    private Symbol[][] grid;

    private int gridSize;
    private int wall;

    private ImageIcon arrowDown = new ImageIcon(this.getClass().getResource("bottom.png"));
    private ImageIcon arrowUp = new ImageIcon(this.getClass().getResource("top.png"));
    private ImageIcon arrowLeft = new ImageIcon(this.getClass().getResource("left.png"));
    private ImageIcon arrowRight = new ImageIcon(this.getClass().getResource("right.png"));

    Grid(int gridSize) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(700, 700);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;
        this.gridGUI = new JButton[gridSize][gridSize];
        this.grid = new Symbol[gridSize][gridSize];

        initGrid();
    }

    Grid(int gridSize, Symbol[][] gridPreset) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(700, 700);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        this.gridGUI = new JButton[gridSize][gridSize];
        this.grid = new Symbol[gridSize][gridSize];

        initGrid();
        display(gridPreset);
    }

    class ButtonHandler implements ActionListener {

        private void processClick(int x, int y) {
            if (isInBoundaries(new Point(x, y))) {
                grid[x][y] = selected_symbol;
                gridGUI[x][y].setIcon(selected);
                if (selected != null) gridGUI[x][y].setBackground(Color.pink);
                else gridGUI[x][y].setBackground(Color.darkGray);
            }
        }

        public void actionPerformed(ActionEvent e) {
            for (int y = 0; y < gridSize; ++y) {
                for (int x = 0; x < gridSize; ++x) {
                    if (e.getSource() == gridGUI[x][y]) {
                        processClick(x, y);
                        return;
                    }
                }
            }
        }
    }

    private void initGrid() {

        ButtonHandler button_handler = new ButtonHandler();

        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                gridGUI[x][y] = new JButton();
                if (!isInBoundaries(new Point(x, y))) {
                    gridGUI[x][y].setBackground(Color.BLACK);
                } else {
                    gridGUI[x][y].setBackground(Color.DARK_GRAY);
                    grid[x][y] = EMPTY;
                }
                add(gridGUI[x][y]);
                gridGUI[x][y].addActionListener(button_handler);
            }
        }
    }

    void display(Symbol[][] nxtBoard) {
        grid = nxtBoard;
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {

                if (!isInBoundaries(new Point(x, y))) {
                    if (nxtBoard[x][y] != EMPTY) {
                        gridGUI[x][y].setBackground(Color.red);
                    } else gridGUI[x][y].setBackground(Color.black);
                } else {
                    gridGUI[x][y].setBackground(Color.pink);
                    switch (nxtBoard[x][y]) {
                        case EMPTY:
                            gridGUI[x][y].setBackground(Color.darkGray);
                            gridGUI[x][y].setIcon(null);
                            break;
                        case DOWN:
                            gridGUI[x][y].setIcon(arrowDown);
                            break;
                        case UP:
                            gridGUI[x][y].setIcon(arrowUp);
                            break;
                        case LEFT:
                            gridGUI[x][y].setIcon(arrowLeft);
                            break;
                        case RIGHT:
                            gridGUI[x][y].setIcon(arrowRight);
                            break;
                    }
                }
            }
        }
    }

    void setSelected(Symbol symbol) {
        selected_symbol = symbol;
        if (symbol == RIGHT) selected = arrowRight;
        else if (symbol == DOWN) selected = arrowDown;
        else if (symbol == UP) selected = arrowUp;
        else if (symbol == LEFT) selected = arrowLeft;
        else selected = null;
    }

    Symbol[][] getGrid() {
        return grid;
    }

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }
}