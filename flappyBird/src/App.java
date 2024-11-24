import javax.swing.*;
 
public class App {
    public static void main(String[] args) throws Exception {
        
       int boardWidth  = 360;
       int boardHeight = 640;

       JFrame frame = new JFrame("Flappy Bird");
       frame.setSize(boardWidth, boardHeight);
       frame.setLocationRelativeTo(null);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        FlappyBird fb = new FlappyBird();
        frame.add(fb);
        frame.pack();
        fb.requestFocus();
        frame.setVisible(true);
   
    }
}
