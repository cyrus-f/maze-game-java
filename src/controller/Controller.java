package controller;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.FileLoader;
import model.GameEnv;
import model.GameState;
import model.Solver;
import view.GuiViewer;
import view.TextViewer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * A controller class which can use and manipulate data from the model to facilitate the view
 */

public class Controller {
    /**
     * an instance of the gameEnv class to use as a model to access data
     */
    private GameEnv gameEnv;
    /**
     * an instance of the textViewer class which can be used to render text to the terminal
     */
    private TextViewer textViewer;
    /**
     * an instance of the guiViewer class which can be used to render graphics
     */
    private GuiViewer guiViewer;
    /**
     * a representation of the current state of the game
     */
    private GameState currentState;

    /**
     * Creates a new instance of the Controller class
     * @param filename a String representing the filepath for the maze text file
     * @param isGui a boolean representing whether the
     *             program should be run in GUI or text-based mode
     */
    public Controller(String filename, boolean isGui) throws FileNotFoundException,
            MazeSizeMissmatchException, MazeMalformedException, IllegalArgumentException {
        FileLoader fl = new FileLoader();
        char[][] maze = new char[0][];
        maze = fl.load(filename);
        this.gameEnv = new GameEnv(maze);
        this.currentState = this.gameEnv.getInitState();
        if (isGui) {
            this.guiViewer = new GuiViewer(this.gameEnv);
            this.playGameGuiAuto();
        } else {
            this.textViewer = new TextViewer(this.gameEnv);
            this.playGameTextManual();
        }
    }

    /**
     * Renders the provided state of the game as text to the terminal
     * @param gameState the provided state to be rendered to the terminal
     */
    public void renderText(GameState gameState) {
        this.textViewer.render(gameState);
    }

    /**
     * renders the given state to the GUI display
     * @param gameState the given state to be rendered to the GUI
     */
    public void renderGui(GameState gameState) {
        this.guiViewer.render(gameState);
    }

    /**
     * Starts running the maze game in manual mode
     * as text in the terminal by displaying the initial state
     * and prompting the user for actions, then
     * reprinting the new state of the game. WASD keys are used to move up,
     * left, down, right respectively
     */
    public void playGameTextManual() {
        this.renderText(currentState);
        try (Scanner scanner = new Scanner(System.in)) {
            String actionName;
            char action;
            while (!this.gameEnv.isSolved(this.currentState)) {
                System.out.print("choose your action: ");
                actionName = scanner.nextLine();
                switch (actionName) {
                    case "w" -> action = GameEnv.UP;
                    case "a" -> action = GameEnv.LEFT;
                    case "s" -> action = GameEnv.DOWN;
                    case "d" -> action = GameEnv.RIGHT;
                    default -> {
                        System.out.println("invalid input please choose from WASD");
                        continue;
                    }
                }
                if (gameEnv.isValid(this.currentState, action)) {
                    this.currentState = gameEnv.performAction(this.currentState, action);
                    this.renderText(currentState);
                } else {
                    System.out.println("invalid action in this state");
                }

            }
            System.out.println("You won! Sir Wobbleton has escaped the maze!!");
        } catch (Exception e) {

        }
    }

    /**
     * Finds a list of actions to solve the maze from the given state.
     * If no solution is possible it will return an empty list []
     * @param initState the state to start in when finding the solution
     * @return a list of actions which if followed from the given state, will lead to the exit
     */
    public List<Character> findSolution(GameState initState) {
        Solver solver = new Solver(this.gameEnv);
        return solver.searchAStar(initState);
    }

    /**
     * Starts running the game in GUI format with an autosolver.
     * The solution will be printed to the terminal and the
     * GUI display will walk through the steps required to get from the start to exit of the maze
     */
    public void playGameGuiAuto() {
        System.out.println("finding solution...");
        int delay = 100;
        List<Character> solution = this.findSolution(this.currentState);
        boolean isSolvable = true;
        if (solution.isEmpty()) {
            System.out.println("No solutions are possible");
            isSolvable = false;
        } else {
            System.out.println("solution: " + solution);
        }
        for (char action : solution) {
            this.currentState = gameEnv.performAction(this.currentState, action);
            this.renderGui(this.currentState);
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                System.out.println("sleep failed");
            }
        }
        if (isSolvable) {
            System.out.println("You won! Sir Wobbleton has escaped the maze!!");
        }
    }

    /**
     * Starts running the maze game in the GUI display with
     * manual controls enabled. WASD keys are used to move up,
     * left, down, right respectively
     */
    public void playGameGuiManual() {
        char action = this.guiViewer.getMainPanel().getStoredAction();
        while (!this.gameEnv.isSolved(this.currentState)) {
            if (this.guiViewer.getMainPanel().getIsActionPending()) {
                if (gameEnv.isValid(this.currentState, action)) {
                    this.currentState = gameEnv.performAction(this.currentState, action);
                    this.guiViewer.getMainPanel().setActionPending(false);
                    this.renderGui(currentState);
                } else {
                    System.out.println("invalid action in this state");
                }
            }
        }

    }
}
