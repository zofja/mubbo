import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Grid extends JPanel {


    private int selected_idx = -1;
    private ImageIcon selected;
    private JButton board[][] = new JButton[9][9];
    private int tab[][] = new int[9][9];
    private ImageIcon arrow_bottom = new ImageIcon(this.getClass()
            .getResource("bottom.png"));
    private ImageIcon arrow_top = new ImageIcon(this.getClass()
            .getResource("top.png"));
    private ImageIcon arrow_left = new ImageIcon(this.getClass()
            .getResource("left.png"));
    private ImageIcon arrow_right = new ImageIcon(this.getClass()
            .getResource("right.png"));

    Grid() {
        setLayout(new GridLayout(9, 9));
        setSize(700, 700);

        class ButtonHandler implements ActionListener {

            private void processClick(int i, int j) {
                if (i != 0 && i !=8 && j != 0 && j != 8) {
                    tab[i][j] = selected_idx;
                    board[i][j].setIcon(selected);
                    if (selected != null) board[i][j].setBackground(Color.pink);
                    else board[i][j].setBackground(Color.darkGray);
                }
            }

            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        if (source == board[i][j]) {
                            processClick(i, j);
                            return;
                        }
                    }
                }
            }
        }


        ButtonHandler button_handler = new ButtonHandler();

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                board[i][j] = new JButton();
                if (i == 0 || j == 0 || i == 8 || j == 8) board[i][j].setBackground(Color.BLACK);
                else {
                    board[i][j].setBackground(Color.DARK_GRAY);
                    tab[i][j] = 0;
                }
                add(board[i][j]);
                board[i][j].addActionListener(button_handler);
            }
        }
    }


    public void display(int[][] new_board) {
        tab = new_board;
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {

                if (i == 0 || i == 8 || j == 0 || j == 8) {
                    if (new_board[i][j] > 0) {
                        board[i][j].setBackground(Color.red);
                    }
                    else board[i][j].setBackground(Color.black);
                } else {

                    switch (new_board[i][j]) {
                        case 0:
                            board[i][j].setBackground(Color.darkGray);
                            board[i][j].setIcon(null);
                            break;
                        case 1:
                            board[i][j].setBackground(Color.pink);
                            board[i][j].setIcon(arrow_bottom);
                            break;
                    }
                }
            }
        }
    }

    void setSelected(int index) {
        selected_idx = index;
        if (index == -1) selected = null;
        else if (index == 0) selected = arrow_bottom;
        else if (index == 1) selected = arrow_top;
        else if (index == 2) selected = arrow_left;
        else selected = arrow_right;
    }


}