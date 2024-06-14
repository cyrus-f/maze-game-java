package view;

import model.GameEnv;
import model.GameState;

import java.util.Map;

/**
 * a viewer class which can render gamestates in text form to the terminal
 */
public class TextViewer {
    /**
     * the environment of the game containing important info about the model
     */
    private GameEnv gameEnv;
    // defining colours
    /**
     * constant to reset the colour
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * the colour black
     */
    public static final String ANSI_BLACK = "\u001B[30m";
    /**
     * the colour red
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * the colour green
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * the colour yellow
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * the colour gray
     */
    public static final String ANSI_GRAY = "\u001B[90m";

    /**
     * the unicode block shape. 2 are used to create a
     * more square shape
     */
    public static final String BLOCK = "\u2588" + "\u2588";
    /**
     * a block which is red
     */
    public static final String RED_BLOCK = ANSI_RED + BLOCK + ANSI_RESET;
    /**
     * a block which is green
     */
    public static final String GREEN_BLOCK = ANSI_GREEN + BLOCK + ANSI_RESET;
    /**
     * a block which is black
     */
    public static final String BLACK_BLOCK = ANSI_BLACK + BLOCK + ANSI_RESET;
    /**
     * a block which is gray
     */
    public static final String GRAY_BLOCK = ANSI_GRAY + BLOCK + ANSI_RESET;
    /**
     * a block which is yellow
     */
    public static final String YELLOW_BLOCK = ANSI_YELLOW + BLOCK + ANSI_RESET;

    /**
     * constructs a new instance of the TextViewer which can render states
     * as text to the terminal
     * @param gameEnv the environment of the game containing important info about the model
     */
    public TextViewer(GameEnv gameEnv) {
        this.gameEnv = gameEnv;
    }

    /**
     * renders a state to the terminal as text
     * @param gameState the state to be rendered
     */
    public void render(GameState gameState) {
        for (int i = 0; i < this.gameEnv.getNoRows(); i++) {
            for (int j = 0; j < this.gameEnv.getNoCols(); j++) {
                char tile = this.gameEnv.getMazeRepr()[i][j];
                // if player is at the position
                if (i == gameState.getRow() && j == gameState.getCol()) {
                    // player representation takes priority over STARTPOINT and ENDPOINT
                    System.out.print(GREEN_BLOCK);
                } else if (tile == GameEnv.END_POINT) {
                    System.out.print(RED_BLOCK);
                } else if (tile == GameEnv.START_POINT) {
                    System.out.print(YELLOW_BLOCK);
                } else if (tile == GameEnv.WALL) {
                    System.out.print(GRAY_BLOCK);
                } else if (GameEnv.PATHS.contains(tile)) {
                    System.out.print(BLACK_BLOCK);

                }
            }
            System.out.println();
        }
    }

    /**
     * renders the state to the terminal using the original input
     * symbols from the maze file. used for debugging
     * @param gameState the gamestate to be rendered
     */
    public void printCharFormat(GameState gameState) {
        // \/\/  check maze by outputting (delete later)
        for (int i = 0; i < this.gameEnv.getMazeRepr().length; i++) {
            for (int j = 0; j < this.gameEnv.getMazeRepr()[i].length; j++) {
                // if player at position
                if (i == gameState.getRow() && j == gameState.getCol()) {
                    System.out.print(GameEnv.SIR_WOBBLETON);
                } else {
                    System.out.print(this.gameEnv.getMazeRepr()[i][j]);
                }
            }
            System.out.println();
        }
    }
}
