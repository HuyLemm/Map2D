package main.dataStructure;

import java.util.Objects;

/**
 * The Point class represents a point in a two-dimensional space.
 * It has properties for x and y coordinates, and provides methods for
 * manipulating and comparing points.
 */
public class Point {
    private int x;
    private int y;

    public Point() {
        this.x = this.y = -1;
    }

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Point object that is a copy of the given Point object.
     *
     * @param p The Point object to be copied.
     */
    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Calculates the distance between this point and the given point.
     *
     * @param p The point to calculate the distance to.
     * @return The distance between this point and the given point.
     */
    public int distance(Point p) {
        double dx = x - p.getX();
        double dy = y - p.getY();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Sets the coordinates of the point to the specified values.
     *
     * @param x The new x-coordinate of the point.
     * @param y The new y-coordinate of the point.
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return A string representation of the point.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Checks if this point is equal to the given object.
     *
     * @param o The object to compare with.
     * @return true if the object is a Point and has the same coordinates as this
     *         point, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;
        return getX() == point.getX() && getY() == point.getY();
    }

    /**
     * Returns the hash code value for this point.
     *
     * @return The hash code value for this point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
