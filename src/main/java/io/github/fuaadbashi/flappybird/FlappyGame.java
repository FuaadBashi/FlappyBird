package io.github.fuaadbashi.flappybird;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Game state and physics, with no Swing dependency, so it runs (and is tested) headless. One {@link
 * #tick()} is one frame.
 */
public class FlappyGame {

    public static final int WIDTH = 360;
    public static final int HEIGHT = 640;

    static final int GRAVITY = 1;
    static final int FLAP_VELOCITY = -9;
    static final int PIPE_SPEED = -4;
    static final int PIPE_WIDTH = 64;
    static final int PIPE_HEIGHT = 512;
    static final int GAP = HEIGHT / 4;

    /** Axis-aligned rectangle with a position that changes each frame. */
    public static class Body {
        int x;
        int y;
        final int width;
        final int height;

        Body(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int width() {
            return width;
        }

        public int height() {
            return height;
        }

        boolean overlaps(Body other) {
            return x < other.x + other.width
                    && x + width > other.x
                    && y < other.y + other.height
                    && y + height > other.y;
        }
    }

    public static final class Pipe extends Body {
        private final boolean top;
        private boolean passed;

        Pipe(int x, int y, boolean top) {
            super(x, y, PIPE_WIDTH, PIPE_HEIGHT);
            this.top = top;
        }

        public boolean isTop() {
            return top;
        }
    }

    private static final int BIRD_START_X = WIDTH / 8;
    private static final int BIRD_START_Y = HEIGHT / 2;

    private final Random random;
    private final Body bird = new Body(BIRD_START_X, BIRD_START_Y, 34, 24);
    private final List<Pipe> pipes = new ArrayList<>();
    private int velocityY;
    private int score;
    private boolean gameOver;

    public FlappyGame(Random random) {
        this.random = random;
    }

    public Body bird() {
        return bird;
    }

    public List<Pipe> pipes() {
        return Collections.unmodifiableList(pipes);
    }

    public int score() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    int velocityY() {
        return velocityY;
    }

    /** Test hook: puts the bird at a height with no vertical speed. */
    void holdBirdAt(int y) {
        bird.y = y;
        velocityY = 0;
    }

    public void flap() {
        if (!gameOver) {
            velocityY = FLAP_VELOCITY;
        }
    }

    /** Adds a top and bottom pipe at the right edge, with the gap at a random height. */
    public void spawnPipes() {
        int topY = -PIPE_HEIGHT / 4 - random.nextInt(PIPE_HEIGHT / 2);
        pipes.add(new Pipe(WIDTH, topY, true));
        pipes.add(new Pipe(WIDTH, topY + PIPE_HEIGHT + GAP, false));
    }

    public void tick() {
        if (gameOver) {
            return;
        }

        velocityY += GRAVITY;
        bird.y = Math.max(bird.y + velocityY, 0);

        for (Pipe pipe : pipes) {
            pipe.x += PIPE_SPEED;
            // Count each pair once, on its top pipe; counting both and halving on display was
            // fragile.
            if (pipe.top && !pipe.passed && pipe.x + pipe.width < bird.x) {
                pipe.passed = true;
                score++;
            }
            if (bird.overlaps(pipe)) {
                gameOver = true;
            }
        }
        // Drop pipes once they leave the screen; otherwise the list grows for the whole game.
        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);

        if (bird.y > HEIGHT) {
            gameOver = true;
        }
    }

    public void restart() {
        bird.x = BIRD_START_X;
        bird.y = BIRD_START_Y;
        // Not resetting velocity made the bird restart at its crash speed and die at once.
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
    }
}
