package main.java;

import javax.swing.JFrame;
import java.awt.*;

public class GameFrame extends JFrame {
    
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    GameFrame() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
