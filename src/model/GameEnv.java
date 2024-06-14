package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * a class used to store data about the game described in a maze file and handle changes of state
 */

public class GameEnv {
    // input file symbols
    /**
     * a character representing a wall in the maze
     */
    public static final char WALL = '#';
    /**
     * a character representing a path in the maze
     */
    public static final char PATH1 = ' ';
    /**
     * a character to use as an alternate representation for a path in the maze
     */
    public static final char PATH2 = '.';
    /**
     * a character representing a path which has been traversed in the maze
     */
    public static final char TRAVERSED = 'T';
    /**
     * a character representing a path which has been backtracked in the maze
     */
    public static final char BACKTRACKED = 'B';
    /**
     * a list containing the tiles which are traversable in the maze
     */
    public static final List<Character> PATHS = new ArrayList<>(
            Arrays.asList(PATH1, PATH2, TRAVERSED, BACKTRACKED));
    /**
     * a character representing the start point in the maze
     */
    public static final char START_POINT = 'S';
    /**
     * a character representing the end point in the maze
     */
    public static final char END_POINT = 'E';
    /**
     * a character representing the player goose named Sir Wobbleton
     */
    public static final char SIR_WOBBLETON = 'P';

    // action symbols
    /**
     * a character representing the action of moving upwards
     */
    public static final char UP = 'u';
    /**
     * a character representing the action of moving downwards
     */
    public static final char DOWN = 'd';
    /**
     * a character representing the action of moving left
     */
    public static final char LEFT = 'l';
    /**
     * a character representing the action of moving r
     */
    public static final char RIGHT = 'r';
    /**
     * a list containing all possible actions
     */
    public static final List<Character> ACTIONS = new ArrayList<>(
            Arrays.asList(UP, DOWN, LEFT, RIGHT));
    /**
     * the cost of taking an action. This is for use in search algorithms.
     */
    public static final int ACTION_COST = 1;
    /**
     * a 2d array of characters representing the maze
     */
    private char[][] mazeRepr;
    /**
     * the initial row position of the goose when the game starts
     */
    private int initRow;
    /**
     * the initial column position of the goose when the game starts
     */
    private int initCol;
    /**
     * the row position of the exit
     */
    private int exitRow;
    /**
     * the column position of the exit
     */
    private int exitCol;
    /**
     * the number of rows in the maze
     */
    private int noRows;
    /**
     * the number of columns in the maze
     */
    private int noCols;

    /**
     * returns a 2d char array representation of the maze
     * @return a representation of the maze
     */
    public char[][] getMazeRepr() {
        return mazeRepr;
    }

    /**
     * sets the representation of the maze
     * @param mazeRepr a new maze 2d char array to replace the old
     */
    public void setMazeRepr(char[][] mazeRepr) {
        this.mazeRepr = mazeRepr;
    }

    /**
     * returns the number of rows in the maze
     * @return the number of rows in the maze
     */
    public int getNoRows() {
        return noRows;
    }

    /**
     * returns the number of columns in the maze
     * @return the number of columns in the maze
     */
    public int getNoCols() {
        return noCols;
    }

    /**
     * returns the exit row of the maze
     * @return the exit row
     */
    public int getExitRow() {
        return exitRow;
    }

    /**
     * returns the exit column of the maze
     * @return the exit column
     */
    public int getExitCol() {
        return exitCol;
    }

    /**
     * constructs a new instance of GameEnv which takes in the
     * data from a maze representation array and parses it into
     * a useful form
     * @param mazeRepr a 2d char array representing a maze
     */
    public GameEnv(char[][] mazeRepr) {
        this.mazeRepr = mazeRepr;
        this.noRows = mazeRepr.length;
        this.noCols = this.mazeRepr[0].length;
        for (int i = 0; i < this.mazeRepr.length; i++) {
            for (int j = 0; j < this.mazeRepr[i].length; j++) {
                if (this.mazeRepr[i][j] == START_POINT) {
                    this.initRow = i;
                    this.initCol = j;
                } else if (this.mazeRepr[i][j] == END_POINT) {
                    this.exitRow = i;
                    this.exitCol = j;
                }


            }

        }
    }

    /**
     * returns a GameState object representing the initial state of the maze in the environment
     * @return the initial state
     */
    public GameState getInitState() {
        return new GameState(this.initRow, this.initCol);
    }

    /**
     * determines whether an action is valid from a given state
     * and if so returns the next state. If it is invalid
     * returns a deepcopy of the current gamestate.
     * @param state the state to transition from
     * @param action the action being taken in the state
     * @return a gamestate object representing the next state
     */
    public GameState performAction(GameState state, char action) {
        // check for invalid action
        if (!ACTIONS.contains(action)) {
            return state.deepCopy();
        }
        // find next coordinate based on action
        int nextRow;
        int nextCol;
        switch (action) {
            case LEFT -> {
                nextRow = state.getRow();
                nextCol = state.getCol() - 1;
            }
            case RIGHT -> {
                nextRow = state.getRow();
                nextCol = state.getCol() + 1;
            }
            case UP -> {
                nextRow = state.getRow() - 1;
                nextCol = state.getCol();
            }
            case DOWN -> {
                nextRow = state.getRow() + 1;
                nextCol = state.getCol();
            }
            default -> {
                return state.deepCopy();
            }
        }

        GameState nextState = new GameState(nextRow, nextCol);
        if (isValid(nextState)) {
            return nextState;
        } else {
            return state.deepCopy();
        }
    }

    /**
     * checks whether a state is valid. i.e., if it is possible to be in this state.
     * @param state a gamestate object to be checked for validity
     * @return a boolean representing the validity of the state
     */
    public boolean isValid(GameState state) {
        // check state is within bounds
        if (!(0 <= state.getRow() && state.getRow() < this.noRows
                && 0 <= state.getCol() && state.getCol() < this.noCols)) {
            return false;

        }
        //check for collision
        if (this.mazeRepr[state.getRow()][state.getCol()] == WALL) {
            // state results in collision
            return false;
        }
        return true;
    }
    /**
     * checks whether a state action pair is valid. i.e., if it is
     * possible to take this action in this state.
     * @param state a gamestate object to be checked for validity
     * @param action a character representing the action to be taken in the state
     * @return a boolean representing the validity of the state action pair
     */

    public boolean isValid(GameState state, char action) {
        return !performAction(state, action).equals(state);
    }

    /**
     * determines whether the given GameState is solved or not.
     * i.e, if the player is in the exit position.
     * @param state a state to be checked for solution or not
     * @return a boolean representing whether the maze has been solved
     */
    public boolean isSolved(GameState state) {
        return state.getRow() == this.exitRow && state.getCol() == this.exitCol;
    }
}
