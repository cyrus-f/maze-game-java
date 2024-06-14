package model;

import java.util.Comparator;

/**
 * a class to compare the usefulness of being at a given
 * position in the maze as opposed to another. This is determined
 * through comparison of path costs of nodes. This needed to create
 * an ordering in a PriorityQueue for use in
 * algorithmic solving.
 */
public class NodeComparator implements Comparator<Node> {
    // Overriding compare()method of Comparator to order nodes for use in the PriorityQueue
    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getPathCost() < n2.getPathCost()) {
            return -1;
        } else if (n1.getPathCost() > n2.getPathCost()) {
            return 1;
        }
        return 0;
    }
}

