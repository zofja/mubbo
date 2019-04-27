package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StartScreen {

    StartScreen(JPanel startPanel) {



        JFrame frame = new JFrame();
        frame.setTitle("MuBbo");
        frame.setSize(new Dimension(700, 700));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(startPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
