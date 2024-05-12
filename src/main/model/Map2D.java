package main.model;

import main.dataStructure.ArrayList;
import main.dataStructure.QuadTree;
import main.dataStructure.Rectangle;

/**
 * Represents a 2D map that uses a QuadTree data structure to store and retrieve
 * places.
 * The map has a fixed side length and a maximum number of places it can hold.
 * It supports operations like insertion, removal, and searching for places.
 */
public class Map2D extends QuadTree<Place> {
    public static final int MAP_SIDE = 10000000;
    public static final int MAX_PLACES = 100000000;
    public int numPlaces;

    /**
     * Constructs a new Map2D object with a root node and initializes the number of
     * places to 0.
     */
    private Map2D() {
        Rectangle boundary = new Rectangle(0, MAP_SIDE, MAP_SIDE, MAP_SIDE);
        root = new Node(boundary, null);
        numPlaces = 0;
    }

    /**
     * Helper class to implement the Singleton design pattern for Map2D.
     */
    private static class SingletonHelper {
        private static final Map2D INSTANCE = new Map2D();
    }

    /**
     * Returns the singleton instance of Map2D.
     * 
     * @return The singleton instance of Map2D.
     */
    public static Map2D getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Returns the number of places in the map.
     * 
     * @return The number of places in the map.
     */
    @Override
    public int size() {
        return numPlaces;
    }

    /**
     * Checks if the map is full and cannot hold more places.
     * 
     * @return True if the map is full, false otherwise.
     */
    @Override
    public boolean isFull() {
        return numPlaces == MAX_PLACES;
    }

    /**
     * Checks if the map is empty and does not contain any places.
     * 
     * @return True if the map is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return numPlaces == 0;
    }

    /**
     * Inserts a place into the map.
     * 
     * @param place The place to insert.
     * @return True if the place was inserted successfully, false otherwise.
     */
    @Override
    public boolean insert(Place place) {
        if (root == null || numPlaces == MAX_PLACES)
            return false;
        if (insert(root, place)) {
            numPlaces++;
            return true;
        }
        return false;
    }

    /**
     * Recursive helper method to insert a place into the map.
     * 
     * @param node  The current node being processed.
     * @param place The place to insert.
     * @return True if the place was inserted successfully, false otherwise.
     */
    private boolean insert(Node node, Place place) {
        if (!node.getBoundary().contains(place.getLocation())) {
            // Place does not fit in the current node's boundary
            return false;
        }

        if (node.isLeaf()) {
            // Check if the place already exists in the node's data
            for (int i = 0; i < node.getData().size(); i++) {
                Place dataPoint = node.getData().get(i);
                if (dataPoint.equals(place) || dataPoint.getLocation().equals(place.getLocation())) {
                    return false; // Place already exists
                }
            }
            if (node.getData().size() < Node.MAX_CAPACITY) {
                node.addData(place);
                return true; // Place added successfully
            } else {
                // If node is full of data, split and distribute into 4 child nodes
                split(node);
                return insert(node, place);
            }
        }

        // Attempt to insert into child nodes
        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            if (insert(child, place)) {
                return true;
            }
        }
        return false; // Unable to insert into any child nodes
    }

    /**
     * Splits a node into 4 child nodes and redistributes the data.
     * 
     * @param node The node to split.
     * @return True if the split was successful, false otherwise.
     */
    private boolean split(Node node) {
        if (!node.isLeaf() || node.getData().size() < Node.MAX_CAPACITY)
            return false;

        Rectangle boundary = node.getBoundary();
        ArrayList<Node> children = node.getChildren();

        int subWidth = boundary.getWidth() / 2;
        int subHeight = boundary.getHeight() / 2;
        int x = boundary.getLocation().getX();
        int y = boundary.getLocation().getY();

        Rectangle northWest = new Rectangle(x, y, subWidth, subHeight);
        Rectangle northEast = new Rectangle(x + subWidth, y, subWidth, subHeight);
        Rectangle southWest = new Rectangle(x, y - subHeight, subWidth, subHeight);
        Rectangle southEast = new Rectangle(x + subWidth, y - subHeight, subWidth, subHeight);

        children.add(new Node(northWest, node));
        children.add(new Node(northEast, node));
        children.add(new Node(southWest, node));
        children.add(new Node(southEast, node));
        node.setChildren(children);

        distributeData(node);
        return true;
    }

    /**
     * Distributes the data in a node among its child nodes after a split.
     * 
     * @param node The node to distribute the data from.
     * @return True if the distribution was successful, false otherwise.
     */
    private boolean distributeData(Node node) {
        ArrayList<Place> data = node.getData();
        ArrayList<Node> children = node.getChildren();

        for (int i = 0; i < data.size(); i++) {
            Place dataPoint = data.get(i);
            insert(node, dataPoint);
        }

        node.clearData();

        return true;
    }

    /**
     * Removes a place from the map.
     * 
     * @param place The place to remove.
     * @return True if the place was removed successfully, false otherwise.
     */
    @Override
    public boolean remove(Place place) {
        if (root == null || numPlaces == 0)
            return false;
        boolean success = remove(null, root, place);
        if (success) {
            numPlaces--;
        }
        return success;
    }

    /**
     * Recursive helper method to remove a place from the map.
     * 
     * @param parent The parent node of the current node being processed.
     * @param node   The current node being processed.
     * @param place  The place to remove.
     * @return True if the place was removed successfully, false otherwise.
     */
    private boolean remove(Node parent, Node node, Place place) {
        if (node == null || place == null)
            return false; // Node does not exist
        if (!node.getBoundary().contains(place.getLocation()))
            return false; // Not found

        if (node.isLeaf()) {
            boolean removed = node.removeData(place);
            if (removed && parent != null) {
                recursiveMerge(parent);
            }
            return removed;
        }

        // Attempt to find and remove in child nodes
        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            if (remove(node, child, place)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Recursively merges child nodes of a node if they have a total of less than 4
     * data points.
     * 
     * @param node The node to perform the merge on.
     */
    private void recursiveMerge(Node node) {
        Node current = node;
        while (current != null && !current.isLeaf() && canMerge(current)) {
            merge(current);
            current = current.getParent();
        }
    }

    /**
     * Checks if the child nodes of a node have a total of less than 4 data points.
     * 
     * @param node The node to check.
     * @return True if the child nodes can be merged, false otherwise.
     */
    public boolean canMerge(Node node) {
        ArrayList<Node> children = node.getChildren();
        int totalSize = 0;
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            if (!child.isLeaf()) {
                return false;
            }
            totalSize += child.getData().size();
        }
        return totalSize <= Node.MAX_CAPACITY;
    }

    /**
     * Merges the child nodes of a node into a single node.
     * 
     * @param node The node to perform the merge on.
     */
    private void merge(Node node) {
        ArrayList<Node> children = node.getChildren();
        ArrayList<Place> mergedData = new ArrayList<>(Node.MAX_CAPACITY);
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            for (int j = 0; j < child.getData().size(); j++) {
                mergedData.add(child.getData().get(j));
            }
        }
        node.setData(mergedData);
        node.setChildren(new ArrayList<>(Node.MAX_CAPACITY)); // Clear children to make this node a leaf
    }

    /**
     * Searches for places within a bounding rectangle that match a partial data.
     * 
     * @param box         The bounding rectangle to search within.
     * @param partialData The partial data to match.
     * @return An ArrayList of places that match the search criteria.
     */
    @Override
    public ArrayList<Place> search(BoundingRectangle box, Place partialData) {
        search(root, box, partialData);
        box.sortPlaces();
        return box.getPlaces();
    }

    /**
     * Recursive helper method to search for places within a bounding rectangle that
     * match a partial data.
     * 
     * @param node        The current node being processed.
     * @param box         The bounding rectangle to search within.
     * @param partialData The partial data to match.
     */
    private void search(Node node, BoundingRectangle box, Place partialData) {
        if (node == null)
            return;

        // Skip the branch if the bounding boxes do not intersect
        if (!node.getBoundary().intersects(box.getBoundary()))
            return;

        if (node.isLeaf()) {
            ArrayList<Place> dataPoints = node.getData();
            if (partialData == null) {
                box.addPlaces(dataPoints);
            } else {
                for (int i = 0; i < dataPoints.size(); i++) {
                    Place dataPoint = dataPoints.get(i);
                    if (dataPoint.partialEquals(partialData)) {
                        box.addPlace(dataPoint);
                    }
                }
            }
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            search(child, box, partialData);
        }
    }

    /**
     * Searches for a specific place within a bounding rectangle.
     * 
     * @param box   The bounding rectangle to search within.
     * @param place The place to search for.
     * @return The place if found, null otherwise.
     */
    public Place searchPlace(BoundingRectangle box, Place place) {
        return searchPlace(root, box, place);
    }

    /**
     * Recursive helper method to search for a specific place within a bounding
     * rectangle.
     * 
     * @param node         The current node being processed.
     * @param box          The bounding rectangle to search within.
     * @param placeInQuery The place to search for.
     * @return The place if found, null otherwise.
     */
    private Place searchPlace(Node node, BoundingRectangle box, Place placeInQuery) {
        if (node == null)
            return null;

        if (!node.getBoundary().intersects(box.getBoundary()))
            return null;

        if (node.isLeaf()) {
            if (!node.getData().isEmpty()) {
                for (int i = 0; i < node.getData().size(); i++) {
                    Place dataPoint = node.getData().get(i);
                    if (dataPoint.equals(placeInQuery)) {
                        return dataPoint;
                    }
                }
            }
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
            Node child = node.getChildren().get(i);
            Place result = searchPlace(child, box, placeInQuery);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
