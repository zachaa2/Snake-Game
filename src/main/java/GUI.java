package main.java;

// View class responsible for handling game rendering
public class GUI {
    // parent Jframe
    public GameFrame mainFrame;
    // JPanel responsible for game board
    public GamePanel gamePanel;
    // JPanel responsible for title screen
    public IntroPanel titleScreen;

    GUI () {
        this.mainFrame = new GameFrame();
        this.gamePanel = new GamePanel();
        this.titleScreen = new IntroPanel();
    }

    public void renderTitleScreen(){
        this.mainFrame.setVisible(true);
        this.mainFrame.add(new IntroPanel());
    }

    public void renderGameBoard() {
        this.mainFrame.setVisible(true);
        this.mainFrame.add(this.gamePanel);  
    }

}