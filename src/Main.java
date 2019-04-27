import gui.Engine;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                Engine::new
        );
    }
}
