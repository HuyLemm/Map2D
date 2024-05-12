package main.model;

import main.dataStructure.ArrayList;
import main.dataStructure.Rectangle;

/**
 * Represents a node in a spatial index tree.
 * Each node has a boundary, children nodes, and data points.
 * The maximum capacity of a node is defined by MAX_CAPACITY.
 * Nodes can be either leaf nodes or internal nodes.
 * Leaf nodes contain data points, while internal nodes contain children nodes.
 * 
 * This class provides methods to manipulate the node's properties and perform
 * operations on the data.
 */
public class Node {
    public static final int MAX_CAPACITY = 4;
    private Node parent;
    private Rectangle boundary;
    private ArrayList<Node> children;
    private ArrayList<Place> data;

    /**
     * Constructs a new Node object with default values.
     * The boundary is initialized as an empty rectangle.
     * The children and data lists are initialized with the maximum capacity.
     */
    public Node() {
        this.boundary = new Rectangle();
        this.children = new ArrayList<>(MAX_CAPACITY);
        this.data = new ArrayList<>(MAX_CAPACITY);
    }

    /**
     * Constructs a new Node object with the specified properties.
     * 
     * @param boundary the boundary of the node
     * @param children the children nodes of the node
     * @param data     the data points of the node
     * @param parent   the parent node of the node
     */
    public Node(Rectangle boundary, ArrayList<Node> children, ArrayList<Place> data, Node parent) {
        this.boundary = boundary;
        this.children = children;
        this.data = data;
        this.parent = parent;
    }

    /**
     * Constructs a new Node object with the specified boundary and parent node.
     * The children and data lists are initialized with the maximum capacity.
     * 
     * @param boundary the boundary of the node
     * @param parent   the parent node of the node
     */
    public Node(Rectangle boundary, Node parent) {
        this(boundary, new ArrayList<>(MAX_CAPACITY), new ArrayList<>(MAX_CAPACITY), parent);
    }

    /**
     * Returns the parent node of this node.
     * 
     * @return the parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node of this node.
     * 
     * @param parent the parent node to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Returns the boundary of this node.
     * 
     * @return the boundary
     */
    public Rectangle getBoundary() {
        return boundary;
    }

    /**
     * Sets the boundary of this node.
     * 
     * @param boundary the boundary to set
     */
    public void setBoundary(Rectangle boundary) {
        this.boundary = boundary;
    }

    /**
     * Returns the children nodes of this node.
     * 
     * @return the children nodes
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * Sets the children nodes of this node.
     * 
     * @param children the children nodes to set
     */
    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    /**
     * Returns the data points of this node.
     * 
     * @return the data points
     */
    public ArrayList<Place> getData() {
        return data;
    }

    /**
     * Sets the data points of this node.
     * 
     * @param data the data points to set
     */
    public void setData(ArrayList<Place> data) {
        this.data = data;
    }

    /**
     * Clears the data points of this node.
     */
    public void clearData() {
        data.clear();
    }

    /**
     * Checks if this node is a leaf node.
     * A leaf node has no children.
     * 
     * @return true if this node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Adds a data point to this node.
     * If the data point is null or the node is already at maximum capacity, the
     * addition fails.
     * 
     * @param place the data point to add
     * @return true if the addition is successful, false otherwise
     */
    public boolean addData(Place place) {
        if (place == null && data.size() >= MAX_CAPACITY)
            return false;
        return data.add(place);
    }

    /**
     * Removes a data point from this node.
     * If the data point is null, the removal fails.
     * 
     * @param place the data point to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeData(Place place) {
        if (place == null)
            return false;
        return data.remove(place);
    }

    /**
     * Returns a string representation of this node.
     * The string includes the parent node, boundary, children nodes, and data
     * points.
     * 
     * @return a string representation of this node
     */
    @Override
    public String toString() {
        StringBuilder childrenStr = new StringBuilder();
        if (!children.isEmpty()) {
            String[] childrenStrArr = new String[children.size()];
            for (int i = 0; i < children.size(); i++) {
                childrenStrArr[i] = children.get(i).isLeaf() ? "Leaf" : "Node";
            }
            childrenStr.append(String.join(", ", childrenStrArr));
        }

        StringBuilder dataStr = new StringBuilder();
        if (!data.isEmpty()) {
            String[] dataStrArr = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                dataStrArr[i] = data.get(i).getLocation().toString();
            }
            dataStr.append(String.join(", ", dataStrArr));
        }

        return "Node{" +
                "parent=" + parent +
                ", boundary=" + boundary +
                ", children=[" + childrenStr +
                "], data=[" + dataStr +
                "]}";
    }
}
