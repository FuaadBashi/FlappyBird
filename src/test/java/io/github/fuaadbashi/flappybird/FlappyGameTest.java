package io.github.fuaadbashi.flappybird;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class FlappyGameTest {

    private final FlappyGame game = new FlappyGame(new Random(1));

    private void ticks(int n) {
        for (int i = 0; i < n; i++) {
            game.tick();
        }
    }

    @Test
    void gravityAcceleratesTheBirdDownward() {
        int start = game.bird().y();

        ticks(3);

        assertEquals(start + 1 + 2 + 3, game.bird().y());
    }

    @Test
    void flappingSendsTheBirdUpward() {
        int start = game.bird().y();

        game.flap();
        game.tick();

        assertTrue(game.bird().y() < start);
    }

    @Test
    void theBirdCannotFlyAboveTheScreen() {
        for (int i = 0; i < 100; i++) {
            game.flap();
            game.tick();
        }

        assertEquals(0, game.bird().y());
    }

    @Test
    void fallingOffTheBottomEndsTheGame() {
        ticks(40);

        assertTrue(game.isGameOver());
    }

    @Test
    void flyingThroughAGapScoresOnePointPerPairOfPipes() {
        game.spawnPipes();
        FlappyGame.Pipe top = game.pipes().get(0);
        int gapCentre = top.y() + top.height() + FlappyGame.GAP / 2;

        // Hold the bird in the middle of the gap until the pipes are behind it.
        while (game.pipes().get(0).x() + FlappyGame.PIPE_WIDTH >= game.bird().x()) {
            game.holdBirdAt(gapCentre - game.bird().height() / 2);
            game.tick();
            assertFalse(game.isGameOver(), "the bird fits through the gap");
        }

        assertEquals(1, game.score());
    }

    @Test
    void hittingAPipeEndsTheGame() {
        game.spawnPipes();
        FlappyGame.Pipe top = game.pipes().get(0);

        while (!game.isGameOver() && top.x() > game.bird().x()) {
            game.holdBirdAt(0);
            game.tick();
        }

        assertTrue(game.isGameOver());
    }

    @Test
    void pipesThatLeaveTheScreenAreDiscarded() {
        game.spawnPipes();
        for (int i = 0; i < 200; i++) {
            game.holdBirdAt(FlappyGame.HEIGHT / 2);
            game.tick();
        }

        assertTrue(game.pipes().isEmpty());
    }

    @Test
    void restartingResetsTheBirdsVelocity() {
        ticks(40);
        assertTrue(game.isGameOver());

        game.restart();

        assertFalse(game.isGameOver());
        assertEquals(0, game.velocityY());
        assertEquals(0, game.score());
        game.tick();
        assertFalse(game.isGameOver(), "the bird no longer dies on the first frame");
    }
}
