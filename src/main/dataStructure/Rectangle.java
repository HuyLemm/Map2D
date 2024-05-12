package main.dataStructure;

import java.util.Objects;

/**
 * Represents a rectangle in a two-dimensional space.
 * A rectangle is defined by its top left corner, width, and height.
 * It provides methods for manipulating and querying the rectangle.
 */
public class Rectangle {
    private final int height;
    private final int width;
    private Point topLeft;
    private Point center;

    /**
     * Constructs a new Rectangle object with default values.
     * The top left corner is set to (0, 0), and the width and height are set to 0.
     */
    public Rectangle() {
        this.topLeft = new Point();
        this.width = 0;
        this.height = 0;
    }

    /**
     * Constructs a new Rectangle object with the specified top left corner
     * coordinates, width, and height.
     * 
     * @param topLeftX the x-coordinate of the top left corner
     * @param topLeftY the y-coordinate of the top left corner
     * @param width    the width of the rectangle
     * @param height   the height of the rectangle
     */
    public Rectangle(int topLeftX, int topLeftY, int width, int height) {
        this.topLeft = new Point(topLeftX, topLeftY);
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new Rectangle object with the specified center point, width, and
     * height.
     * The top left corner is calculated based on the center point.
     * 
     * @param center the center point of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point center, int width, int height) {
        this.width = width;
        this.height = height;
        this.topLeft = new Point((int) (center.getX() - width / 2.0), (int) (center.getY() + height / 2.0));
    }

    /**
     * Sets the location of the top left corner of the rectangle to the specified
     * coordinates.
     * 
     * @param x the x-coordinate of the new location
     * @param y the y-coordinate of the new location
     */
    public void setLocation(int x, int y) {
        this.topLeft.setLocation(x, y);
    }

    /**
     * Sets the location of the top left corner of the rectangle to the specified
     * point.
     * 
     * @param p the new location point
     */
    public void setLocation(Point p) {
        this.topLeft = p;
    }

    /**
     * Returns the center point of the rectangle.
     * 
     * @return the center point
     */
    public Point getCenter() {
        return new Point((int) (topLeft.getX() + width / 2.0), (int) (topLeft.getY() - height / 2.0));
    }

    /**
     * Checks if this rectangle intersects with the specified rectangle.
     * 
     * @param r the rectangle to check intersection with
     * @return true if the rectangles intersect, false otherwise
     */
    public boolean intersects(Rectangle r) {
        return this.topLeft.getX() < r.topLeft.getX() + r.width &&
                this.topLeft.getX() + this.width > r.topLeft.getX() &&
                this.topLeft.getY() > r.topLeft.getY() - r.height &&
                this.topLeft.getY() - this.height < r.topLeft.getY();
    }

    /**
     * Checks if this rectangle contains the specified coordinates.
     * 
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the rectangle contains the coordinates, false otherwise
     */
    public boolean contains(int x, int y) {
        return x >= this.topLeft.getX() && x <= this.topLeft.getX() + this.width &&
                y <= this.topLeft.getY() && y >= this.topLeft.getY() - this.height;
    }

    /**
     * Checks if this rectangle contains the specified point.
     * 
     * @param point the point to check
     * @return true if the rectangle contains the point, false otherwise
     */
    public boolean contains(Point point) {
        int x = point.getX();
        int y = point.getY();
        return contains(x, y);
    }

    /**
     * Returns the location of the top left corner of the rectangle.
     * 
     * @return the location point
     */
    public Point getLocation() {
        return this.topLeft;
    }

    /**
     * Returns the height of the rectangle.
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the rectangle.
     * 
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rectangle rectangle = (Rectangle) o;
        return height == rectangle.height && width == rectangle.width && topLeft.equals(rectangle.topLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, topLeft);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "height=" + height +
                ", width=" + width +
                ", topLeft=" + topLeft +
                '}';
    }
}
