import java.awt.Image;

public class Bird {
    int x = FlappyBird.birdX;
    int y = FlappyBird.birdY;
    int yVelocity = FlappyBird.birdSpeedVelocityY;
    int width = FlappyBird.birdWidth;
    int height = FlappyBird.birdHeight;
    Image image;

    public Bird(Image img) {
        this.image = img;
    }

}
