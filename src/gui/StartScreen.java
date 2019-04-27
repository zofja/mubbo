package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class StartScreen extends JFrame {

    StartScreen(JPanel startPanel) {

        super("MuBbo");
        setSize(new Dimension(700, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(startPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
