import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    
    public static final int WIDTH  = 360;
    public static final int HEIGHT = 640;

    // Bird postions & logic
    public static int birdX               = WIDTH / 8;
    public static int birdY               = HEIGHT / 2;
    public static int birdSpeedVelocityY  = 0; // Moves bird upwards and downwards
    public static int birdWidth           = 34;
    public static int birdHeight          = 24;

    // Use of Images for bird, obstacles, and background
    public Image topPipeimg;
    public Image bottomPipeimg;
    public Image birdimg;
    public Image background;

    // Bird
    Bird bird;

    // Pipes
    int pipeX = WIDTH; 
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    int pipeSpeedVelocityX = -4;
    ArrayList<Pipe> pipes;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        boolean passed = false;
        int xVelocity = pipeSpeedVelocityX; // Moves pipes leftward at speed of 4 pixels per frame(simulates the motion of the bird)
        Image image;

        Pipe(Image img) {
            this.image = img;
        }
    }

    // Game Logic
    Timer loop;
    Timer placePipesTimer;
    int gravity = 1;
    boolean gameOver = false;
    int score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        // setBackground(Color.blue);
            
        // Constructor to load images
        background    = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        topPipeimg    = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        birdimg       = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        bottomPipeimg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdimg);

        pipes = new ArrayList<>();

        // timer for placing pipes
        placePipesTimer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        } );
        placePipesTimer.start(); 


        //game loop timer
        loop = new Timer(1000/60, this);
        loop.start();


    }   

    // Method to move the bird and pipes
    public void move() {

        bird.yVelocity += gravity;
        bird.y         += bird.yVelocity;
        bird.y          = Math.max(bird.y, 0);
        Pipe pipe;

        // Pipes
       for (int i = 0; i < pipes.size(); i++){
            pipe = pipes.get(i);
            pipe.x += pipe.xVelocity;

            if (!pipe.passed && pipe.x + pipe.width < bird.x) {
            pipe.passed = true;
            score++;
            }
        
            if (collidesWithPipe(bird, pipe)) {
                gameOver = true;
            }
       }

       
       if (bird.y> HEIGHT) {
            gameOver = true;
        }
       
    }
    // Method to place pipes
    public void placePipes() {
        int topPipeRandomY = ( (int) (pipeY - pipeHeight/4 - Math.random() * (pipeHeight/2)));

        int openningSpace = HEIGHT/4;

        Pipe topPipe    = new Pipe(topPipeimg);
        topPipe.y  = topPipeRandomY;
        pipes.add(topPipe);


        Pipe bottomPipe = new Pipe(bottomPipeimg);
        bottomPipe.y  = topPipe.y + pipeHeight + openningSpace;
        pipes.add(bottomPipe);
    }


     // Method to paint the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    } 

     // Method to draw the panel components  (bird, obstacles, background)  on the panel  when it is painted
    private void draw(Graphics g) {

        //draw background
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

        // Draw bird
        g.drawImage(bird.image, bird.x, bird.y, bird.width, bird.height, null);

        // Draw pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score/2, WIDTH - 90, 40);

        if (gameOver){
            g.drawString("Game Over!", WIDTH/2 - 50, HEIGHT/2);
        }

    }

    // Method to check for collision between bird and pipe
    public boolean collidesWithPipe(Bird b, Pipe p) {
    return (b.x + b.width >= p.x && b.x <= p.x + p.width) && (b.y <= p.y + p.height && b.y + b.height >= p.y);
    }

    // Method to handle game loop events
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        
        if (gameOver){
            loop.stop();
            placePipesTimer.stop();
        }
    }

     // Method to handle key press events
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.yVelocity = -9;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver){
                gameOver = false;
                score = 0;
                bird.y = FlappyBird.birdY;
                pipes.clear();
                loop.start();
                placePipesTimer.start();
            }
        }

    }

    // Not used methods
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
