package model;

/**
 * a node class used to store information about a gamestate and how
 * it was reached. This is for use in building trees
 * in search algorithms.
 */
public class Node {
    /**
     * the gamestate which is being represented
     */
    private GameState state;
    /**
     * a back reference to the node which was passed through to get to this node
     */
    private Node parent;
    /**
     * the action taken in the previous state to transition to this node
     */
    private char actionFromParent;
    /**
     * the cost taken to get to this node
     */
    private double pathCost;

    /**
     * returns the cost to get to this position node in the maze from the starting position
     * @return the path cost
     */
    public double getPathCost() {
        return pathCost;
    }

    /**
     * returns the gamestate of the node representing its position in the maze
     * @return the state of the node
     */
    public GameState getState() {
        return state;
    }

    /**
     * returns the parent node which was passed through to get to this node
     * @return the parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * returns the action which was taken in the previous state to get to this node
     * @return the action from the parent node
     */
    public char getActionFromParent() {
        return actionFromParent;
    }

    /**
     * constructs a new instance of the node class to store
     * and organise information about states in the maze and the
     * cost to reach those positions
     * @param state the state to be described
     * @param parent the node which was passed through to
     *              get to this node. If the root node is being constructed then
     *               this is null
     * @param actionFromParent the action taken in the previous
     *                        state to reach this node. If the root node is being
     *                         constructed then this is ' '.
     * @param pathCost the cost to reach this position in the maze from the start
     */
    public Node(GameState state, Node parent, char actionFromParent, double pathCost) {
        this.state = state;
        this.parent = parent;
        this.actionFromParent = actionFromParent;
        this.pathCost = pathCost;
    }
}
