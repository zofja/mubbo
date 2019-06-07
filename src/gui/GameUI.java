package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import core.GridManager;
import core.Symbol;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import static core.Symbol.EMPTY;
import static sound.MusicBox.NUMBER_OF_INSTRUMENTS;

public class GameUI {

    private static GridManager gridManager;
    private static final int gridSize = 9;
    private boolean ifStarted = false;
    public static final int intervalDuration = 250;
    private int iterations = 3000; // time playing = iterations * intervalDuration
    private Timer timer = new Timer(intervalDuration, new UpdateGridAfterTick());

    private JPanel rootPanel;
    private JLabel description;
    private JButton PLAYButton;
    private JPanel grid;
    private JPanel options;
    private JPanel arrowMenu;
    private JPanel instrumentMenu;
    private JList instrumentList;
    private JList arrowList;

    /**
     * Random generator used to get color.
     */
    private final Random random = new Random();

    /**
     * Color of the grid.
     */
    private static final Color GRID_COLOR = new Color(0xffffff);

    /**
     * Color of the border.
     */
    private static final Color BORDER_COLOR = new Color(0xB8B8B8);


    /**
     * Number of available instruments.
     */
    private static final int instrumentsNumber = NUMBER_OF_INSTRUMENTS;

    /**
     * Currently marked as checked symbol.
     */
    private static Symbol selectedSymbol = EMPTY;


    /**
     * Currently selected icon.
     */
    private static Integer selectedInstrument = -1;

    /**
     * Enables a user to add and delete arrows from board.
     */
    private static JButton[][] buttonGrid = new JButton[gridSize][gridSize];
    ;

    /**
     * Used for visual representation.
     */
    private static Symbol[][][] symbolGrid = new Symbol[gridSize][gridSize][instrumentsNumber];

    /**
     * Used for painting instrument icons.
     */
    private static Hashtable<Integer, Symbol>[][] instruments;


    /**
     * Number of row/column in grid which will play a note if it contains {@code Particle}.
     */
    private static int wall = 8;

    public GameUI() {
        $$$setupUI$$$();
        PLAYButton.addActionListener(actionEvent -> {
            if (!ifStarted) {
                ifStarted = true;
                for (var row : buttonGrid) {
                    for (var button : row) {
                        button.setBorderPainted(false);
                    }
                }
                timer.start();
                gridManager.init(symbolGrid);
                JButton clicked = (JButton) actionEvent.getSource();
                clicked.setText("PAUSE");
            } else {
                ifStarted = false;
                timer.stop();
                for (var row : buttonGrid) {
                    for (var button : row) {
                        button.setBorderPainted(true);
                    }
                }
                JButton clicked = (JButton) actionEvent.getSource();
                clicked.setText("PLAY");
            }
        });
        arrowList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                ListItem item = (ListItem) list.getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                list.repaint(list.getCellBounds(index, index));
                if (selectedSymbol != Symbol.EMPTY) {
                    ((ListItem) list.getModel().getElementAt(selectedSymbol.ordinal())).setSelected(false);
                }
                if (index == selectedSymbol.ordinal()) {
                    selectedSymbol = Symbol.EMPTY;
                } else if (index == 4) { // because COLLISION is originally 4th in Symbol enum
                    selectedSymbol = Symbol.EMPTY;
                } else {
                    selectedSymbol = Symbol.values()[index];
                }
            }
        });
        instrumentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                ListItem item = (ListItem) list.getModel().getElementAt(index);
                System.out.println(item);
                item.setSelected(!item.isSelected());
                list.repaint(list.getCellBounds(index, index));
                if (selectedInstrument != -1) {
                    ((ListItem) list.getModel().getElementAt(selectedInstrument)).setSelected(false);
                }
                if (selectedInstrument == index) {
                    selectedInstrument = -1;
                } else {
                    selectedInstrument = index;
                }
            }
        });
    }

    public void main2(String scale, int reverb) {
        JFrame frame = new JFrame("GameUI");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // initialize attributes
        gridManager = new GridManager(gridSize, scale, reverb);
        InstrumentIcon.generateImages();
        instruments = new Hashtable[gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                instruments[x][y] = new Hashtable<>();
            }
        }
    }


    private void createUIComponents() {
        // custom grid
        grid = new JPanel();
        grid.setLayout(new GridLayout(gridSize, gridSize));
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(70, 70));
                Border border = new LineBorder(Color.LIGHT_GRAY, 1);
                button.setBorder(border);
                buttonGrid[x][y] = button;
                for (int i = 0; i < instrumentsNumber; i++) {
                    if (!isInBoundaries(new Point(x, y))) {
                        buttonGrid[x][y].setBackground(BORDER_COLOR);
                    } else {
                        buttonGrid[x][y].setBackground(GRID_COLOR);
                        symbolGrid[x][y][i] = EMPTY;
                    }
                    grid.add(buttonGrid[x][y]);
                    buttonGrid[x][y].addActionListener(new ButtonHandler());
                }
            }
        }

        // custom arrowList
        arrowList = new JList<ListItem>();
        ListItem[] list = new ListItem[]{
                new ListItem("LEFT"),
                new ListItem("UP"),
                new ListItem("RIGHT"),
                new ListItem("DOWN"),
                new ListItem("EMPTY")};
        arrowList.setListData(list);
        arrowList.setCellRenderer(new ListRenderer());

        // custom instrumentList
        instrumentList = new JList<ListItem>();
        ArrayList<ListItem> instruments = new ArrayList<>();
        for (int i = 0; i < instrumentsNumber; i++) {
            instruments.add(new ListItem("Instrument " + i));
        }
        instrumentList.setListData(instruments.toArray());
        instrumentList.setCellRenderer(new ListRenderer());
    }

    /**
     * Class used to react to grid's button click.
     */
    static class ButtonHandler implements ActionListener {

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
            if (selectedSymbol == EMPTY) {
                instruments[x][y].remove(selectedInstrument);
                buttonGrid[x][y].setIcon(new InstrumentIcon(instruments[x][y]));
            } else {
                instruments[x][y].put(selectedInstrument, selectedSymbol);
                buttonGrid[x][y].setIcon(new InstrumentIcon(instruments[x][y])); // ustaw customową ikonę
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
                display(gridManager.displayNext());
                iterations--;
            }
        }
    }


    /**
     * Displays grid.
     *
     * @param nxtBoard grid to display.
     */
    void display(Symbol[][][] nxtBoard) {
        symbolGrid = nxtBoard;
        for (int y = 0; y < gridSize; ++y) {
            for (int x = 0; x < gridSize; ++x) {
                instruments[x][y].clear();
                boolean lightWall = false;
                for (int i = 0; i < instrumentsNumber; i++) {
                    if (!isInBoundaries(new Point(x, y))) {
                        if (nxtBoard[x][y][i] != EMPTY) {
                            lightWall = true;
                        } else {
                            buttonGrid[x][y].setBackground(BORDER_COLOR);
                        }
                    } else {
                        if (nxtBoard[x][y][i] != EMPTY) {
                            instruments[x][y].put(i, nxtBoard[x][y][i]);
                        } else {
                            buttonGrid[x][y].setIcon(null);
                        }
                    }
                }
                if (lightWall) {
                    buttonGrid[x][y].setBackground(randomColor().brighter());
                }
                buttonGrid[x][y].setIcon(new InstrumentIcon(instruments[x][y]));
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
     * Checks if given point is in boundaries.
     *
     * @param p coordinates
     * @return true if point in in boundaries, otherwise false.
     */
    private static boolean isInBoundaries(Point p) {
        return !(p.x == 0 || p.x == wall || p.y == 0 || p.y == wall);
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 2, new Insets(20, 20, 20, 20), -1, -1));
        rootPanel.setBackground(new Color(-2105377));
        rootPanel.setMaximumSize(new Dimension(900, 900));
        rootPanel.setMinimumSize(new Dimension(900, 900));
        rootPanel.setPreferredSize(new Dimension(900, 900));
        description = new JLabel();
        Font descriptionFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 20, description.getFont());
        if (descriptionFont != null) description.setFont(descriptionFont);
        description.setText("Position arrows on the grid and click PLAY.");
        rootPanel.add(description, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PLAYButton = new JButton();
        PLAYButton.setBackground(new Color(-14869219));
        PLAYButton.setFocusPainted(false);
        PLAYButton.setFocusable(false);
        Font PLAYButtonFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 28, PLAYButton.getFont());
        if (PLAYButtonFont != null) PLAYButton.setFont(PLAYButtonFont);
        PLAYButton.setForeground(new Color(-2105377));
        PLAYButton.setText("PLAY");
        rootPanel.add(PLAYButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        grid.setBackground(new Color(-1907998));
        rootPanel.add(grid, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(630, 630), new Dimension(630, 630), new Dimension(630, 630), 0, true));
        options = new JPanel();
        options.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        options.setOpaque(false);
        rootPanel.add(options, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        arrowMenu = new JPanel();
        arrowMenu.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        arrowMenu.setOpaque(false);
        options.add(arrowMenu, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Font arrowListFont = this.$$$getFont$$$("Noto Sans Mono CJK JP Regular", -1, 20, arrowList.getFont());
        if (arrowListFont != null) arrowList.setFont(arrowListFont);
        arrowList.setOpaque(false);
        arrowMenu.add(arrowList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        instrumentMenu = new JPanel();
        instrumentMenu.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        instrumentMenu.setOpaque(false);
        options.add(instrumentMenu, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Font instrumentListFont = this.$$$getFont$$$("Noto Sans Mono CJK KR Regular", -1, 18, instrumentList.getFont());
        if (instrumentListFont != null) instrumentList.setFont(instrumentListFont);
        instrumentList.setOpaque(false);
        instrumentMenu.add(instrumentList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
