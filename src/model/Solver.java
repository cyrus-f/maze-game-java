package model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * a class which is used to programmatically generate solutions to mazes
 */
public class Solver {
    /**
     * the environment of the maze to be solved which provides
     * necessary information for the algorithm
     */
    private GameEnv gameEnv;
    /**
     * a cache which maps gamestates to their associated heuristic values
     * this saves time as each value is only computed once and then stored
     * to be retrieved the next time it is needed  instead of recalculating
     */
    private Map<GameState, Double> heuristicCache = new HashMap<>();

    /**
     * constructs a new instance of the solver class which is used to
     * programmatically generate solutions for mazes
     * @param gameEnv the environment of the game to be solved
     */
    public Solver(GameEnv gameEnv) {
        this.gameEnv = gameEnv;
    }

    /**
     * determines the heuristic value of a given gamestate.
     * A heuristic is an estimate of the closeness of the
     * current node to the goal node. This method uses the
     * Euclidean distance formula, meaning the shortest
     * straight line to the exit. This will be admissible
     * meaning it will always underestimate the distance as the player
     * can only move up, down, left and right, not diagonally.
     * Note: if the heuristic has previously been calculated
     * it will simply be returned from the cache rather than
     * recalculated
     * @param state the state for which to compute a heuristic value
     * @return the heuristic value as a double
     */
    public double compute_heuristic(GameState state) {
        double value;
        // check cache first so as not to recalculate past values
        if (this.heuristicCache.get(state) != null) {
            return this.heuristicCache.get(state);
        }
        // calculate euclidean distance
        double dx = Math.abs(state.getRow() - this.gameEnv.getExitRow());
        double dy = Math.abs(state.getCol() - this.gameEnv.getExitCol());
        value = Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2));
        this.heuristicCache.put(state, value);
        return value;
    }

    /**
     * performs an A* search beginning at the given gamestate position
     * to generate a solution to the maze. An A* search is an informed
     * search algorithm which looks for the shortest path to the exit.
     * Note: if the maze is unsolvable an empty list will be returned.
     * @param initState the state from which to begin the search
     * @return a list containing action characters which
     *          when performed in the given order will reach the exit
     */
    public List<Character> searchAStar(GameState initState) {
        Node node = null;
        Map<GameState, Double> visited = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(10, new NodeComparator());
        pq.add(new Node(initState, null, ' ', this.compute_heuristic(initState)));
        while (!pq.isEmpty()) {
            node = pq.poll();
            if (this.gameEnv.isSolved(node.getState())) {
                break;
            }
            List<Node> children = this.generateChildren(node);
            for (Node succ : children) {
                if (!visited.containsKey(succ.getState())
                        || succ.getPathCost() < visited.get(succ.getState())) {
                    pq.add(succ);
                    visited.put(succ.getState(), succ.getPathCost());
                }
            }
        }
        if (pq.isEmpty()) {
            node = null;
        }
        return this.backtrackActions(node, visited);
    }

    /**
     * a helper method which takes a node and generates all possible
     * child nodes. i.e, positions which can be reached immediately by
     * taking an action in the current state.
     * @param node the node to find the children of
     * @return a list containing the children of the given node
     */
    public List<Node> generateChildren(Node node) {
        List<Node> children = new ArrayList<>();
        for (char action : GameEnv.ACTIONS) {
            // check if each action is valid
            if (this.gameEnv.isValid(node.getState(), action)) {
                GameState nextState = this.gameEnv.performAction(node.getState(), action);
                double heuristic = this.compute_heuristic(nextState);
                children.add(new Node(nextState, node,  action,
                        node.getPathCost() + GameEnv.ACTION_COST + heuristic));
            }
        }
        return children;
    }

    /**
     * a helper method which backtracks from the goalnode to the root
     * node and collects actions in order to generate a list of
     * actions which were taken to reach the exit.
     * @param goalNode the node which has been reached containing the exit
     * @param visited a map representing which nodes have been visited and
     *                the lowest path cost currently associated with them
     * @return the list of actions taken to reach the goal from the root
     */
    public List<Character> backtrackActions(Node goalNode, Map<GameState, Double> visited) {
        List<Character> characters = null;
        List<Character> seq = new ArrayList<>();
        if (goalNode != null) {
            Node node = goalNode;
            while (node.getParent() != null) {
                seq.add(node.getActionFromParent());
                node = node.getParent();
            }
            java.util.Collections.reverse(seq);
        }
        return seq;
    }
}
