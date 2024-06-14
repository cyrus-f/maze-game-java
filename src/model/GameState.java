package model;

/**
 * a class representing a state of the maze game.
 * It contains information about the location of the player.
 */
public class GameState {
    /**
     * the row position of the player
     */
    private int row;
    /**
     * the column position of the player
     */
    private int col;

    /**
     * returns the row position of the player
     * @return row position
     */
    public int getRow() {
        return row;
    }

    /**
     * returns the column position of the player
     * @return column position
     */
    public int getCol() {
        return col;
    }

    /**
     * constructs a new instance of gamestate.
     * It contains the row and column coordinates of the player.
     * @param row the row position of the player
     * @param col the column position of the player
     */
    public GameState(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        // if compared with itself return true
        if (o == this) {
            return true;
        }
        // Check if o is an instance of GameState
        if (!(o instanceof GameState)) {
            return false;
        }
        GameState g = (GameState) o;
        return Integer.compare(this.row, g.row) == 0
                && Integer.compare(this.col, g.col) == 0;
    }

    @Override
    public int hashCode() {
        return this.row + this.col;

    }

    @Override
    public String toString() {
        return "row: " + this.row + " col: " + this.col;
    }

    /**
     * creates a deepcopy of the gamestate.
     * i.e. creates a new object which is equal to the current state but resides in
     * a different memory address.
     * @return a deepcopy of the given state
     */
    public GameState deepCopy() {
        return new GameState(this.row, this.col);
    }
}
