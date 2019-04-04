package gui;

import core.Symbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static core.Symbol.*;

public class Grid extends JPanel {

    private Symbol selected_symbol = EMPTY;
    private ImageIcon selected;
    private JButton board[][] = new JButton[9][9];
    private Symbol tab[][] = new Symbol[9][9];
    private ImageIcon arrow_bottom = new ImageIcon(this.getClass()
            .getResource("bottom.png"));
    private ImageIcon arrow_top = new ImageIcon(this.getClass()
            .getResource("top.png"));
    private ImageIcon arrow_left = new ImageIcon(this.getClass()
            .getResource("left.png"));
    private ImageIcon arrow_right = new ImageIcon(this.getClass()
            .getResource("right.png"));

    public Grid() {
        setLayout(new GridLayout(9, 9));
        setSize(700, 700);

        class ButtonHandler implements ActionListener {

            private void processClick(int i, int j) {
                if (i != 0 && i !=8 && j != 0 && j != 8) {
                    tab[i][j] = selected_symbol;
                    board[i][j].setIcon(selected);
                    if (selected != null) board[i][j].setBackground(Color.pink);
                    else board[i][j].setBackground(Color.darkGray);
                }
            }

            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                for (int j = 0; j < 9; ++j) {
                    for (int i = 0; i < 9; ++i) {
                        if (source == board[i][j]) {
                            processClick(i, j);
                            return;
                        }
                    }
                }
            }
        }


        ButtonHandler button_handler = new ButtonHandler();

        for (int j = 0; j < 9; ++j) {
            for (int i = 0; i < 9; ++i) {
                board[i][j] = new JButton();
                if (i == 0 || j == 0 || i == 8 || j == 8) board[i][j].setBackground(Color.BLACK);
                else {
                    board[i][j].setBackground(Color.DARK_GRAY);
                    tab[i][j] = EMPTY;
                }
                add(board[i][j]);
                board[i][j].addActionListener(button_handler);
            }
        }
    }


    public void display(Symbol[][] new_board) {
        tab = new_board;
        for (int j = 0; j < 9; ++j) {
            for (int i = 0; i < 9; ++i) {

                if (i == 0 || i == 8 || j == 0 || j == 8) {
                    if (new_board[i][j] != EMPTY) {
                        board[i][j].setBackground(Color.red);
                    }
                    else board[i][j].setBackground(Color.black);
                } else {
                    board[i][j].setBackground(Color.pink);
                    switch (new_board[i][j]) {
                        case EMPTY:
                            board[i][j].setBackground(Color.darkGray);
                            board[i][j].setIcon(null);
                            break;
                        case DOWN:
                            board[i][j].setIcon(arrow_bottom);
                            break;
                        case UP:
                            board[i][j].setIcon(arrow_top);
                            break;
                        case LEFT:
                            board[i][j].setIcon(arrow_left);
                            break;
                        case RIGHT:
                            board[i][j].setIcon(arrow_right);
                            break;
                    }
                }
            }
        }
        this.revalidate();
        this.repaint();
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
        return tab;
    }
}