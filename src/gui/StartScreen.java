package gui;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    public StartScreen() {
        super("MuBbo");

        setSize(1000, 700);
//        setDefaultLookAndFeelDecorated(true);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

//        getContentPane().add(new OptionBox(grid), BorderLayout.EAST);


        ImageIcon logo_icon = new ImageIcon(this.getClass()
                .getResource("image.png"));
        JLabel logo_lable = new JLabel(logo_icon);
        JScrollPane logo = new JScrollPane(logo_lable);

        getContentPane().add(logo);

        JButton button = new JButton();
        button.setSize(new Dimension(50, 50));
        button.setLocation(500, 350);
        getContentPane().add(button);

        setVisible(true);
    }

}
