package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static core.Symbol.*;

public class Grid extends JPanel {

    private Symbol selected_symbol = EMPTY;
    private ImageIcon selected;
    private JButton[][] clickableGrid;
    private Symbol[][] grid;
    private int gridSize;
    private int wall;

    private ImageIcon arrow_bottom = new ImageIcon(this.getClass()
            .getResource("bottom.png"));
    private ImageIcon arrow_top = new ImageIcon(this.getClass()
            .getResource("top.png"));
    private ImageIcon arrow_left = new ImageIcon(this.getClass()
            .getResource("left.png"));
    private ImageIcon arrow_right = new ImageIcon(this.getClass()
            .getResource("right.png"));

    public Grid(int gridSize) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(700, 700);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        this.clickableGrid = new JButton[gridSize][gridSize];
        this.grid = new Symbol[gridSize][gridSize];

        initGrid();

    }

    private void initGrid() {

        class ButtonHandler implements ActionListener {

            private void processClick(int x, int y) {
                if (isInBoundaries(new Point(x, y))) {
                    grid[x][y] = selected_symbol;
                    clickableGrid[x][y].setIcon(selected);
                    if (selected != null) clickableGrid[x][y].setBackground(Color.pink);
                    else clickableGrid[x][y].setBackground(Color.darkGray);
                }
            }

            public void actionPerformed(ActionEvent e) {
                for (int y = 0; y < gridSize; ++y) {
                    for (int x = 0; x < gridSize; ++x) {
                        if (e.getSource() == clickableGrid[x][y]) {
                            processClick(x, y);
                            return;
                        }
                    }
                }
            }
        }

        ButtonHandler button_handler = new ButtonHandler();

        for (int j = 0; j < 9; ++j) {
            for (int i = 0; i < 9; ++i) {
                clickableGrid[i][j] = new JButton();
                if (i == 0 || j == 0 || i == 8 || j == 8) clickableGrid[i][j].setBackground(Color.BLACK);
                else {
                    clickableGrid[i][j].setBackground(Color.DARK_GRAY);
                    grid[i][j] = EMPTY;
                }
                add(clickableGrid[i][j]);
                clickableGrid[i][j].addActionListener(button_handler);
            }
        }

    }

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }


    public void display(Symbol[][] new_board) {
        grid = new_board;
        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {

                if (x == 0 || x == 8 || y == 0 || y == 8) {
                    if (new_board[x][y] != EMPTY) {
                        clickableGrid[x][y].setBackground(Color.red);
                    } else clickableGrid[x][y].setBackground(Color.black);
                } else {
                    clickableGrid[x][y].setBackground(Color.pink);
                    switch (new_board[x][y]) {
                        case EMPTY:
                            clickableGrid[x][y].setBackground(Color.darkGray);
                            clickableGrid[x][y].setIcon(null);
                            break;
                        case DOWN:
                            clickableGrid[x][y].setIcon(arrow_bottom);
                            break;
                        case UP:
                            clickableGrid[x][y].setIcon(arrow_top);
                            break;
                        case LEFT:
                            clickableGrid[x][y].setIcon(arrow_left);
                            break;
                        case RIGHT:
                            clickableGrid[x][y].setIcon(arrow_right);
                            break;
                    }
                }
            }
        }
    }

    void setSelected(Symbol symbol) {
        selected_symbol = symbol;
        if (symbol == RIGHT) selected = arrow_right;
        else if (symbol == DOWN) selected = arrow_bottom;
        else if (symbol == UP) selected = arrow_top;
        else if (symbol == LEFT) selected = arrow_left;
        else selected = null;
    }

    public Symbol[][] getGrid() {
        return grid;
    }
}