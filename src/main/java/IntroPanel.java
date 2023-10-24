package main.java;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

class IntroPanel extends JPanel {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    JButton playButton;
    JButton customizeButton;

    private Image backgroundImage;

    IntroPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        loadBackgroundTexture("..\\..\\..\\images\\landscape.png");
        // buttons
        createButtons();
        addButtonsToPanel();

        this.setLayout(null);
    }

    public void loadBackgroundTexture(String filename) {
        try{
            this.backgroundImage = ImageIO.read(new File(filename)).getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
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

    public void createButtons() {
        this.playButton = new JButton("Play");
        this.playButton.setPreferredSize(new Dimension(80, 45));

        this.customizeButton = new JButton("Change Color");

    }

    public void addButtonsToPanel(){
        this.playButton.setBounds((int)(SCREEN_WIDTH / 2 - (0.1 * SCREEN_WIDTH) - this.playButton.getPreferredSize().width / 2), 
                                SCREEN_HEIGHT / 2, 
                                this.playButton.getPreferredSize().width, 
                                this.playButton.getPreferredSize().height);
        this.add(this.playButton);
    }

    public void renderBackground(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, this);
    }

    public void renderGameTitle(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Snake Game", (SCREEN_WIDTH - metrics.stringWidth("Snake Game")) / 2, g.getFont().getSize());

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        renderBackground(g);
        renderGameTitle(g);
    }

}