package io.github.fuaadbashi.flappybird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/** Draws a {@link FlappyGame} and drives it with Swing timers and keyboard input. */
public class GamePanel extends JPanel {

    private static final int FRAME_MS = 1000 / 60;
    private static final int PIPE_INTERVAL_MS = 1200;

    private final FlappyGame game = new FlappyGame(new Random());
    private final Image background = load("flappybirdbg.png");
    private final Image birdImage = load("flappybird.png");
    private final Image topPipeImage = load("toppipe.png");
    private final Image bottomPipeImage = load("bottompipe.png");
    private final Timer frameTimer;
    private final Timer pipeTimer;

    public GamePanel() {
        setPreferredSize(new Dimension(FlappyGame.WIDTH, FlappyGame.HEIGHT));
        setFocusable(true);

        frameTimer = new Timer(FRAME_MS, e -> onFrame());
        pipeTimer = new Timer(PIPE_INTERVAL_MS, e -> game.spawnPipes());

        addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                            game.flap();
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && game.isGameOver()) {
                            game.restart();
                            start();
                        }
                    }
                });
    }

    public void start() {
        frameTimer.start();
        pipeTimer.start();
    }

    private void onFrame() {
        game.tick();
        repaint();
        if (game.isGameOver()) {
            frameTimer.stop();
            pipeTimer.stop();
        }
    }

    /** Loads an image next to this class on the classpath, so it works from a jar too. */
    private static Image load(String name) {
        return new ImageIcon(Objects.requireNonNull(GamePanel.class.getResource(name), name))
                .getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, FlappyGame.WIDTH, FlappyGame.HEIGHT, null);

        FlappyGame.Body bird = game.bird();
        g.drawImage(birdImage, bird.x(), bird.y(), bird.width(), bird.height(), null);

        for (FlappyGame.Pipe pipe : game.pipes()) {
            Image image = pipe.isTop() ? topPipeImage : bottomPipeImage;
            g.drawImage(image, pipe.x(), pipe.y(), pipe.width(), pipe.height(), null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + game.score(), FlappyGame.WIDTH - 90, 40);

        if (game.isGameOver()) {
            g.drawString("Game Over!", FlappyGame.WIDTH / 2 - 50, FlappyGame.HEIGHT / 2);
            g.drawString("Press Enter", FlappyGame.WIDTH / 2 - 50, FlappyGame.HEIGHT / 2 + 30);
        }
    }
}
