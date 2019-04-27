package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static core.Symbol.*;

/**
 * Class responsible for displaying a simulation of generated music - a board with moving {@code Particles}.
 */
class Grid extends JPanel {

    /**
     *  Length and width of grid.
     */
    private final int gridDimension = 500;

    /**
     * Currently marked as checked symbol.
     */
    private Symbol selectedSymbol = EMPTY;

    /**
     * Currently selected icon.
     */
    private ImageIcon selectedIcon;

    /**
     * Enables a user to add and delete arrows from board.
     */
    private JButton[][] buttonGrid;

    /**
     * Used for visual representation.
     */
    private Symbol[][] symbolGrid;

    /**
     * Number of cells in each row/column.
     */
    private int gridSize;

    /**
     * Number of row/column in grid which will play a note if it contains {@code Particle}.
     */
    private int wall;

    /**
     * Icons shown on the board.
     */
    private ImageIcon arrowDown = new ImageIcon(this.getClass().getResource("bottom.png"));
    private ImageIcon arrowUp = new ImageIcon(this.getClass().getResource("top.png"));
    private ImageIcon arrowLeft = new ImageIcon(this.getClass().getResource("left.png"));
    private ImageIcon arrowRight = new ImageIcon(this.getClass().getResource("right.png"));

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param gridSize grid size.
     */
    Grid(int gridSize) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(gridDimension, gridDimension);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;
        this.buttonGrid = new JButton[gridSize][gridSize];
        this.symbolGrid = new Symbol[gridSize][gridSize];

        initGrid();
    }

    // TODO eliminate copypaste
    /**
     * @param gridSize   grid size.
     * @param gridPreset current visual grid representation.
     */
    Grid(int gridSize, Symbol[][] gridPreset) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(gridDimension, gridDimension);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        this.buttonGrid = new JButton[gridSize][gridSize];
        this.symbolGrid = new Symbol[gridSize][gridSize];

        initGrid();
        display(gridPreset);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  PRIVATE NESTED CLASSES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Class used to react to grid's button click.
     */
    class ButtonHandler implements ActionListener {

        /**
         * Changes {@code symbolGrid} displays {@code selectedIcon} on clicked grid's field.
         *
         * @param x x coordinate of clicked button.
         * @param y y coordinate of clicked button.
         */
        private void processClick(int x, int y) {
            if (isInBoundaries(new Point(x, y))) {
                symbolGrid[x][y] = selectedSymbol;
                buttonGrid[x][y].setIcon(selectedIcon);
                if (selectedIcon != null) buttonGrid[x][y].setBackground(Color.pink);
                else buttonGrid[x][y].setBackground(Color.darkGray);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int y = 0; y < gridSize; ++y) {
                for (int x = 0; x < gridSize; ++x) {
                    if (e.getSource() == buttonGrid[x][y]) {
                        processClick(x, y);
                        return;
                    }
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                              PACKAGE PRIVATE FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays grid.
     *
     * @param nxtBoard grid to display.
     */
    void display(Symbol[][] nxtBoard) {
        symbolGrid = nxtBoard;
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                if (!isInBoundaries(new Point(x, y))) {
                    if (nxtBoard[x][y] != EMPTY) {
                        buttonGrid[x][y].setBackground(Color.red);
                    } else buttonGrid[x][y].setBackground(Color.black);
                } else {
                    buttonGrid[x][y].setBackground(Color.pink);
                    changeCell(nxtBoard[x][y], x, y);
                }
            }
        }
    }

    /**
     * Sets currently marked as checked icon.
     *
     * @param symbol currently selected symbol.
     */
    void setSelectedIcon(Symbol symbol) {
        selectedSymbol = symbol;
        if (symbol == RIGHT) selectedIcon = arrowRight;
        else if (symbol == DOWN) selectedIcon = arrowDown;
        else if (symbol == UP) selectedIcon = arrowUp;
        else if (symbol == LEFT) selectedIcon = arrowLeft;
        else selectedIcon = null;
    }

    /**
     * Getter.
     *
     * @return current grid.
     */
    Symbol[][] getSymbolGrid() {
        return symbolGrid;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Inits an empty grid.
     */
    private void initGrid() {

        ButtonHandler button_handler = new ButtonHandler();

        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                buttonGrid[x][y] = new JButton();
                if (!isInBoundaries(new Point(x, y))) {
                    buttonGrid[x][y].setBackground(Color.BLACK);
                } else {
                    buttonGrid[x][y].setBackground(Color.DARK_GRAY);
                    symbolGrid[x][y] = EMPTY;
                }
                add(buttonGrid[x][y]);
                buttonGrid[x][y].addActionListener(button_handler);
            }
        }
    }

    /**
     * Changes given cell's symbol and colour.
     *
     * @param symbol new symbol.
     * @param x x coordinate of cell to change.
     * @param y y coordinate of cell to change.
     */
    private void changeCell(Symbol symbol, int x, int y) {
        switch (symbol) {
            case EMPTY:
                buttonGrid[x][y].setBackground(Color.darkGray);
                buttonGrid[x][y].setIcon(null);
                break;
            case DOWN:
                buttonGrid[x][y].setIcon(arrowDown);
                break;
            case UP:
                buttonGrid[x][y].setIcon(arrowUp);
                break;
            case LEFT:
                buttonGrid[x][y].setIcon(arrowLeft);
                break;
            case RIGHT:
                buttonGrid[x][y].setIcon(arrowRight);
                break;
        }
    }

    /**
     * Checks if given point is in boundaries.
     * @param p coordinates
     * @return true if point in in boundaries, otherwise false.
     */
    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

}