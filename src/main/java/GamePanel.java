package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;

    int applesEaten = 0;

    int appleX;
    int appleY;

    Direction direction = Direction.RIGHT;

    boolean running = false;
    boolean isSnakeDead = false;

    Timer timer;
    Random random;

    private Image backgroundImage;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        loadBackgroundTexture("..\\..\\..\\images\\grass.png");
        this.setBackground(Color.black);
    }
    
    public void loadBackgroundTexture(String filename) {
        try{
            this.backgroundImage = ImageIO.read(new File(filename)).getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_REPLICATE);
        }  catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load background texture!",
               "Graphics Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Could not load background texture");
            // close app
            Container parent = this.getParent();
            Window win = SwingUtilities.getWindowAncestor(parent);
            win.dispose();
        }
    }

    public void startGame(){
        running = true;
        isSnakeDead = false;
        newApple();
        timer = new Timer(DELAY, this);
        timer.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running){
            // draw background 
            g.drawImage(backgroundImage, 0, 0, this);
            // draw grid lines
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            // draw apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // draw snake
            for (int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // draw score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        }
        else if (isSnakeDead) {
            // draw game over screen
            gameOver(g);
        } else {
            this.setBackground(Color.green);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move() {
        for (int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case UP:
                y[0] = y[0] - UNIT_SIZE;
                break;
            case DOWN:
                y[0] = y[0] + UNIT_SIZE;
                break;
            case LEFT:
                x[0] = x[0] - UNIT_SIZE;
                break;
            case RIGHT:
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        // check if head of snake hit apple
        if ((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        // check head collision with body
        for (int i = bodyParts; i > 0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                isSnakeDead = true;
            }
        }
        // check head collision with game border
        if ((x[0] < 0) || (x[0] > SCREEN_WIDTH)){
            running = false;
            isSnakeDead = true;
        }
        if ((y[0] < 0) || (y[0] > SCREEN_HEIGHT)){
            running = false;
            isSnakeDead = true;
        }
        if (!running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g){
        // game over screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        // draw score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics Scoremetrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - Scoremetrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != Direction.RIGHT){
                        direction = Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        direction = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN){
                        direction = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP){
                        direction = Direction.DOWN;
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }


    
    

}
