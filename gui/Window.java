import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {

        super("MuBbo");

        setSize(1000, 700);
        setDefaultLookAndFeelDecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Grid grid = new Grid();
        getContentPane().add(grid);

        getContentPane().add(new OptionBox(grid), BorderLayout.EAST);

    }

}
