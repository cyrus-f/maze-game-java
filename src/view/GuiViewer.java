package view;

import model.GameEnv;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

/**
 * a viewer class which is used to display the maze onto a window
 */
public class GuiViewer extends  JFrame implements ActionListener {
    /**
     * the environment of the game which provides important info
     * about the model
     */
    private GameEnv gameEnv;
    /**
     * the panel which contains the maze
     */
    private GuiPanel mainPanel;
    /**
     * stores the width of the computer screen for use in
     * scaling the display
     */
    public static final int SCREEN_WIDTH = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getScreenDevices()[0]
            .getDisplayMode()
            .getWidth();
    /**
     * stores the height of the screen for use in scaling the display
     */
    public static final int SCREEN_HEIGHT = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getScreenDevices()[0]
            .getDisplayMode()
            .getHeight();

    /**
     * returns the main panel which contains the maze
     * @return the panel which contains the maze
     */
    public GuiPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * constructs a new instance of GuiViewer which can be used
     * to render graphical maze representations
     * @param gameEnv the environment of the game which contains
     *                important info about the model
     */
    public GuiViewer(GameEnv gameEnv) {
        this.gameEnv = gameEnv;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Maze Game");
        this.mainPanel = new GuiPanel(this.gameEnv);
        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * renders the given state into the display by loading
     * it into the panel and repainting it.
     * @param gameState the state to be rendered to the display
     */
    public void render(GameState gameState) {
        this.mainPanel.setGameState(gameState);
        this.mainPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
