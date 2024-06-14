package view;

import model.GameEnv;
import model.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * custom class which extends the JPanel to display the maze in
 * a GUI format
 */
public class GuiPanel extends JPanel implements KeyListener {
    /**
     * the environment of the game to provide information
     */
    private GameEnv gameEnv;
    /**
     * the current state of the game
     */
    private GameState gameState;
    /**
     * an image used to display the player
     */
    private BufferedImage image;
    /**
     * a boolean representing whether there is
     * an input action waiting to be processed
     */
    private boolean isActionPending;
    /**
     * the action which has been inputted and
     * awaits processing
     */
    private char storedAction;

    /**
     * sets the status of whether an action is pending
     * @param actionPending a boolean representing
     *                      whether there is an action pending
     */
    public void setActionPending(boolean actionPending) {
        isActionPending = actionPending;
    }

    /**
     * returns whether there is a waiting action
     * @return a boolean representing whether there
     *          is a pending action
     */
    public boolean getIsActionPending() {
        return isActionPending;
    }

    /**
     * returns the stored action
     * @return the action which is stored
     */
    public char getStoredAction() {
        return storedAction;
    }

    /**
     * updates the current gamestate so it can be
     * accessed to be rendered
     * @param gameState the new state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * constructs a new instance of the GuiPanel class
     * which is used to render GUI displays of the maze game
     * @param gameEnv the environment of the game which provides
     *                important information from the model
     */
    public GuiPanel(GameEnv gameEnv) {
        this.gameEnv = gameEnv;
        this.gameState = gameEnv.getInitState();
        try {
            /*
            reference:
            https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Domestic_Goose.jpg/1024px-Domestic_Goose.jpg
             */
            image = ImageIO.read(new File("media/goose1.jpg"));
        } catch (IOException e) {
            // handle exception...
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // calculating tile size to fit to screen
        int scaler = Math.min(GuiViewer.SCREEN_HEIGHT, GuiViewer.SCREEN_WIDTH);
        /* if height is less than width then scale vertically by
         the number of rows otherwise scale horizontally to fit
        the maze on the screen
        */
        int divisor = (scaler
                == GuiViewer.SCREEN_HEIGHT) ? this.gameEnv.getNoRows() : this.gameEnv.getNoCols();
        // backup to ensure maze fits on the screen
        int thresholdCorrection = 2;
        // width and height are same as tiles are squares
        int tileDimensions = scaler / divisor / thresholdCorrection;

        int xPos = 0;
        int yPos = 0;
        for (int i = 0; i < this.gameEnv.getNoRows(); i++) {
            for (int j = 0; j < this.gameEnv.getNoCols(); j++) {
                char tile = this.gameEnv.getMazeRepr()[i][j];
                // if player at position
                if (i == gameState.getRow() && j == gameState.getCol()) {
                    // display player
                    g.drawImage(this.image, xPos, yPos, tileDimensions, tileDimensions, this);

                    char [][] currentMaze = this.gameEnv.getMazeRepr();
                    if (tile == GameEnv.PATH1 || tile == GameEnv.PATH2) {
                        // if player at empty space make it traversed
                        currentMaze[i][j] = GameEnv.TRAVERSED;
                        this.gameEnv.setMazeRepr(currentMaze);
                    } else if (tile == GameEnv.BACKTRACKED) {
                        currentMaze[i][j] = GameEnv.TRAVERSED;
                        this.gameEnv.setMazeRepr(currentMaze);
                    }

                } else {

                    // path tiles not included in below as they are white space
                    switch (tile) {
                        case GameEnv.WALL -> {
                            g.setColor(Color.BLACK);
                            g.fillRect(xPos, yPos, tileDimensions, tileDimensions);

                        }
                        case GameEnv.START_POINT -> {
                            g.setColor(Color.YELLOW);
                            g.fillRect(xPos, yPos, tileDimensions, tileDimensions);
                        }
                        case GameEnv.END_POINT -> {
                            g.setColor(Color.RED);
                            g.fillRect(xPos, yPos, tileDimensions, tileDimensions);
                        }
                        case GameEnv.TRAVERSED -> {
                            g.setColor(Color.CYAN);
                            g.fillRect(xPos, yPos, tileDimensions, tileDimensions);
                        }
                        case GameEnv.BACKTRACKED -> {
                            g.setColor(Color.BLUE);
                            g.fillRect(xPos, yPos, tileDimensions, tileDimensions);
                        }
                    }


                }
                xPos += tileDimensions;
            }
            xPos = 0;
            yPos += tileDimensions;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W -> {
                this.storedAction = GameEnv.UP;
                this.isActionPending = true;
            }
            case KeyEvent.VK_A -> {
                this.storedAction = GameEnv.LEFT;
                this.isActionPending = true;
            }
            case KeyEvent.VK_S -> {
                this.storedAction = GameEnv.DOWN;
                this.isActionPending = true;
            }
            case KeyEvent.VK_D -> {
                this.storedAction = GameEnv.RIGHT;
                this.isActionPending = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
