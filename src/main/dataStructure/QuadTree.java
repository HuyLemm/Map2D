package main.dataStructure;

import main.model.BoundingRectangle;
import main.model.Place;
import main.model.Node;

/**
 * The QuadTree class represents a generic quadtree data structure.
 * It provides methods for inserting, removing, searching, and displaying data
 * points in the quadtree.
 *
 * @param <T> the type of data stored in the quadtree
 */
public abstract class QuadTree<T> {

    protected Node root;

    /**
     * Returns the number of data points stored in the quadtree.
     *
     * @return the size of the quadtree
     */
    public abstract int size();

    /**
     * Checks if the quadtree is full (reached its maximum capacity).
     *
     * @return true if the quadtree is full, false otherwise
     */
    public abstract boolean isFull();

    /**
     * Checks if the quadtree is empty (contains no data points).
     *
     * @return true if the quadtree is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Inserts a data point into the quadtree.
     *
     * @param dataToInsert the data point to insert
     * @return true if the insertion is successful, false otherwise
     */
    public abstract boolean insert(T dataToInsert);

    /**
     * Removes a data point from the quadtree.
     *
     * @param dataToRemove the data point to remove
     * @return true if the removal is successful, false otherwise
     */
    public abstract boolean remove(T dataToRemove);

    /**
     * Searches for data points within a specified bounding rectangle and matching a
     * partial data.
     *
     * @param box         the bounding rectangle to search within
     * @param partialData the partial data to match
     * @return an ArrayList of Place objects that match the search criteria
     */
    public abstract ArrayList<Place> search(BoundingRectangle box, T partialData);

    /**
     * Displays the structure of the quadtree.
     * Prints the nodes and their boundaries in a hierarchical manner.
     */
    public void display() {
        if (root == null) {
            System.out.println("Tree is empty");
        } else {
            display(root, 0);
        }
    }

    private void display(Node node, int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "  ";
        }

        if (node.isLeaf()) {
            System.out.println(indent + "Node " + level + ": Leaf - " + node.getBoundary());
            for (int i = 0; i < node.getData().size(); i++) {
                Place dataPoint = node.getData().get(i);
                System.out.println(indent + "  Data point = " + dataPoint);
            }

        } else {
            System.out.println(indent + "Node " + level + ": " + node.getBoundary());
            for (int i = 0; i < node.getChildren().size(); i++) {
                Node child = node.getChildren().get(i);
                display(child, level + 1);
            }
        }
    }

    /**
     * Displays the data points stored in the quadtree.
     * Prints the non-null data points in a depth-first manner.
     */
    public void displayData() {
        if (root == null) {
            System.out.println("Root is not set.");
        } else {
            displayData(root);
        }
    }

    private void displayData(Node node) {
        if (node == null)
            return;

        // Check each child node for data if the current node is not a leaf
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                Node child = node.getChildren().get(i);
                displayData(child);
            }
        } else {
            // This is a leaf node, display all non-null data points
            for (int i = 0; i < node.getData().size(); i++) {
                Place dataPoint = node.getData().get(i);
                if (dataPoint != null) {
                    System.out.println(dataPoint);
                }
            }
        }
    }
}
