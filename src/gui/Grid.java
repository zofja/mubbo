package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static core.Symbol.*;

class Grid extends JPanel {

    /**
     * Random generator used to get color.
     */
    private final Random random = new Random();

    /**
     * Color of the grid.
     */
    private static final Color GRID_COLOR = new Color(0x323232);

    /**
     * Color of the border.
     */
    private static final Color BORDER_COLOR = new Color(0x282828);

    /**
     * Color of the particle.
     */
    private static final Color PARTICLE_COLOR = new Color(0xeeeeee);


    private final int gridDimension = 500;

    private Symbol selectedSymbol = EMPTY;
    private ImageIcon selectedIcon;

    private JButton[][] buttonGrid;
    private Symbol[][] symbolGrid;

    private int gridSize;
    private int wall;

    private ImageIcon arrowDown = new ImageIcon(this.getClass().getResource("bottom.png"));
    private ImageIcon arrowUp = new ImageIcon(this.getClass().getResource("top.png"));
    private ImageIcon arrowLeft = new ImageIcon(this.getClass().getResource("left.png"));
    private ImageIcon arrowRight = new ImageIcon(this.getClass().getResource("right.png"));

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

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

    class ButtonHandler implements ActionListener {

        private void processClick(int x, int y) {
            if (isInBoundaries(new Point(x, y))) {
                symbolGrid[x][y] = selectedSymbol;
                buttonGrid[x][y].setIcon(selectedIcon);
                if (selectedIcon != null) buttonGrid[x][y].setBackground(PARTICLE_COLOR);
                else buttonGrid[x][y].setBackground(GRID_COLOR);
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

    void display(Symbol[][] nxtBoard) {
        symbolGrid = nxtBoard;
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                if (!isInBoundaries(new Point(x, y))) {
                    if (nxtBoard[x][y] != EMPTY) {
                        buttonGrid[x][y].setBackground(randomColor().brighter());
                    } else buttonGrid[x][y].setBackground(BORDER_COLOR);
                } else {
                    buttonGrid[x][y].setBackground(PARTICLE_COLOR);
                    changeCell(nxtBoard[x][y], x, y);
                }
            }
        }
    }

    /**
     * Generates random, bright color.
     *
     * @return a Color.
     */
    private Color randomColor() {
        int c = random.nextInt(3 * 0xff);
        if (c < 0xff) {
            c %= 0xff;
            return new Color(0xff - c, c, 0xff);
        } else if (c < 2 * 0xff) {
            c %= 0xff;
            return new Color(0xff, 0xff - c, c);
        } else {
            c %= 0xff;
            return new Color(c, 0xff, 0xff - c);
        }
    }

    void setSelectedIcon(Symbol symbol) {
        selectedSymbol = symbol;
        if (symbol == RIGHT) selectedIcon = arrowRight;
        else if (symbol == DOWN) selectedIcon = arrowDown;
        else if (symbol == UP) selectedIcon = arrowUp;
        else if (symbol == LEFT) selectedIcon = arrowLeft;
        else selectedIcon = null;
    }

    Symbol[][] getSymbolGrid() {
        return symbolGrid;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private void initGrid() {

        ButtonHandler button_handler = new ButtonHandler();

        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                buttonGrid[x][y] = new JButton();
                if (!isInBoundaries(new Point(x, y))) {
                    buttonGrid[x][y].setBackground(BORDER_COLOR);
                } else {
                    buttonGrid[x][y].setBackground(GRID_COLOR);
                    symbolGrid[x][y] = EMPTY;
                }
                add(buttonGrid[x][y]);
                buttonGrid[x][y].addActionListener(button_handler);
            }
        }
    }

    private void changeCell(Symbol symbol, int x, int y) {
        switch (symbol) {
            case EMPTY:
                buttonGrid[x][y].setBackground(GRID_COLOR);
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

    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

}