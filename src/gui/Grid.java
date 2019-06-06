package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

import static core.Symbol.COLLISION;
import static core.Symbol.EMPTY;
import static sound.MusicBox.NUMBER_OF_INSTRUMENTS;

/**
 * Class responsible for displaying a simulation of generated music - a board with moving {@code Particles}.
 */
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

    /**
     * Number of available instruments.
     */
    private final int instrumentsNumber = NUMBER_OF_INSTRUMENTS + 1;

    /**
     * Length and width of grid.
     */
    private final int gridDimension = 500; // pixels

    /**
     * Currently marked as checked symbol.
     */
    private Symbol selectedSymbol = EMPTY;

    /**
     * Currently selected icon.
     */
    private ImageIcon selectedIcon;

    /**
     * Currently selected icon.
     */
    private Integer selectedInstrument;

    /**
     * Enables a user to add and delete arrows from board.
     */
    private JButton[][] buttonGrid;

    /**
     * Used for visual representation.
     */
    private Symbol[][][] symbolGrid;

    /**
     * Used for painting instrument icons.
     */
    private HashSet<Integer>[][] instruments;

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
    private ImageIcon arrowDown = new ImageIcon(this.getClass().getResource("assets/down.png"));
    private ImageIcon arrowUp = new ImageIcon(this.getClass().getResource("assets/up.png"));
    private ImageIcon arrowLeft = new ImageIcon(this.getClass().getResource("assets/left.png"));
    private ImageIcon arrowRight = new ImageIcon(this.getClass().getResource("assets/right.png"));
    private ImageIcon collision = new ImageIcon(this.getClass().getResource("assets/collision.png"));

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
        InstrumentIcon.generateImages();
        this.instruments = new HashSet[gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                instruments[x][y] = new HashSet<>();
            }
        }
        this.buttonGrid = new JButton[gridSize][gridSize];
        this.symbolGrid = new Symbol[gridSize][gridSize][instrumentsNumber];
        initGrid();
    }

    // TODO eliminate copypaste

    /**
     * @param gridSize   grid size.
     * @param gridPreset current visual grid representation.
     */
    Grid(int gridSize, Symbol[][][] gridPreset) {
        setLayout(new GridLayout(gridSize, gridSize));
        setSize(gridDimension, gridDimension);
        this.gridSize = gridSize;
        this.wall = gridSize - 1;

        this.buttonGrid = new JButton[gridSize][gridSize];
        this.symbolGrid = new Symbol[gridSize][gridSize][instrumentsNumber];

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
            if (!isInBoundaries(new Point(x, y)) || selectedInstrument == -1) {
                return;
            }
            symbolGrid[x][y][selectedInstrument] = selectedSymbol;
            if (selectedSymbol == EMPTY || selectedSymbol == COLLISION) {
                buttonGrid[x][y].setBackground(GRID_COLOR);
                buttonGrid[x][y].setIcon(null);
            } else {
                assert (instruments[x][y] != null);
                instruments[x][y].add(selectedInstrument);
                buttonGrid[x][y].setIcon(new InstrumentIcon(instruments[x][y], symbolGrid[x][y][selectedInstrument])); // ustaw customową ikonę
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
    void display(Symbol[][][] nxtBoard) {
        symbolGrid = nxtBoard;
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                for (int i = 0; i < instrumentsNumber; i++) {
                    if (!isInBoundaries(new Point(x, y))) {
                        if (nxtBoard[x][y][i] != EMPTY) {
                            buttonGrid[x][y].setBackground(randomColor().brighter());
                        } else buttonGrid[x][y].setBackground(BORDER_COLOR);
                    } else {
                        buttonGrid[x][y].setBackground(PARTICLE_COLOR); // TODO custom instrument kolor
                        changeCell(nxtBoard[x][y][i], x, y);
                    }
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

    /**
     * Sets currently marked as checked icon.
     *
     * @param symbol currently selected symbol.
     */
    void setSelectedSymbol(Symbol symbol) {
        selectedSymbol = symbol;
        System.out.println(selectedSymbol);
    }

    void setSelectedInstrument(Integer instrument) {
        selectedInstrument = instrument;
        System.out.println(selectedInstrument);
    }

    Integer getSelectedInstrument() {
        return this.selectedInstrument;
    }


    /**
     * Getter.
     *
     * @return current grid.
     */
    Symbol[][][] getSymbolGrid() {
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
                for (int i = 0; i < instrumentsNumber; i++) {
                    if (!isInBoundaries(new Point(x, y))) {
                        buttonGrid[x][y].setBackground(BORDER_COLOR);
                    } else {
                        buttonGrid[x][y].setBackground(GRID_COLOR);
                        symbolGrid[x][y][i] = EMPTY;
                    }
                    add(buttonGrid[x][y]);
                    buttonGrid[x][y].addActionListener(button_handler);
                }
            }
        }
    }

    /**
     * Changes given cell's symbol and colour.
     *
     * @param symbol new symbol.
     * @param x      x coordinate of cell to change.
     * @param y      y coordinate of cell to change.
     */
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
            case COLLISION:
                buttonGrid[x][y].setIcon(collision);
                break;
        }
    }

    /**
     * Checks if given point is in boundaries.
     *
     * @param p coordinates
     * @return true if point in in boundaries, otherwise false.
     */
    private boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }

}