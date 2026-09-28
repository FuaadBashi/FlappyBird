package io.github.fuaadbashi.flappybird;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        // Swing components must be created on the event dispatch thread.
        SwingUtilities.invokeLater(
                () -> {
                    JFrame frame = new JFrame("Flappy Bird");
                    GamePanel panel = new GamePanel();
                    frame.add(panel);
                    frame.pack();
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    panel.requestFocusInWindow();
                    panel.start();
                });
    }
}
