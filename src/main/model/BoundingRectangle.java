package main.model;

import main.dataStructure.ArrayList;
import main.dataStructure.Point;
import main.dataStructure.Rectangle;

/**
 * This class represents a bounding rectangle that defines a region on a map.
 * It is used to filter and sort places based on their distance from a center
 * point.
 * The class implements a singleton pattern to ensure only one instance is
 * created.
 */
public class BoundingRectangle {
    // Maximum number of queries to show places
    private static final int MAX_QUERIES = 50;
    // Minimum distance for filtering places
    private static final int MIN_DISTANCE = 100;
    // Maximum distance for filtering places
    private static final int MAX_DISTANCE = 100000;

    private Rectangle boundary; // The boundary rectangle
    private Place placeCenter; // The center place
    private ArrayList<Place> places; // List of places within the boundary
    private SortOption sortOption; // The sorting option for places
    private DistanceType distanceType; // The distance type for filtering places
    private double distance; // The distance used for filtering places

    /**
     * Enumeration for sorting options.
     */
    public enum SortOption {
        DISTANCE_ASC, DISTANCE_DESC, NAME_ASC, NAME_DESC
    }

    /**
     * Enumeration for distance types.
     */
    public enum DistanceType {
        NEAR, WALKING, DRIVING, FAR, MAP
    }

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the default values for the bounding rectangle.
     */
    private BoundingRectangle() {
        this.sortOption = SortOption.DISTANCE_DESC;
        this.distanceType = DistanceType.NEAR;
        setDistance(-1);
        this.boundary = new Rectangle(0, Map2D.MAP_SIDE, (int) this.distance * 2, (int) this.distance * 2);
        this.places = new ArrayList<>(Map2D.MAX_PLACES);
        this.placeCenter = new Place();
        this.placeCenter.setLocation(boundary.getCenter());
    }

    /**
     * Helper class to implement the singleton pattern.
     */
    private static class SingletonHelper {
        private static final BoundingRectangle INSTANCE = new BoundingRectangle();
    }

    /**
     * Returns the singleton instance of the BoundingRectangle class.
     * 
     * @return The singleton instance
     */
    public static BoundingRectangle getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Compares two places for sorting based on the current sort option.
     * 
     * @param place The first place
     * @param other The second place
     * @return True if the first place should come before the second place in the
     *         sorted list, false otherwise
     */
    private boolean compareForSort(Place place, Place other) {
        return switch (sortOption) {
            case DISTANCE_ASC -> place.distance(placeCenter) < other.distance(placeCenter);
            case DISTANCE_DESC -> place.distance(placeCenter) > other.distance(placeCenter);
            case NAME_ASC -> place.getName().compareTo(other.getName()) < 0;
            case NAME_DESC -> place.getName().compareTo(other.getName()) > 0;
        };
    }

    /**
     * Sets the initial distance based on the current distance type.
     */
    public void setInitialDistance() {
        this.distance = switch (distanceType) {
            case NEAR -> MIN_DISTANCE;
            case WALKING -> MIN_DISTANCE * 10;
            case DRIVING -> MIN_DISTANCE * 100;
            case FAR -> MAX_DISTANCE;
            case MAP -> Map2D.MAP_SIDE / 2.0;
        };
    }

    /**
     * Sets the distance for filtering places.
     * If the given distance is valid, it is set as the new distance.
     * Otherwise, the initial distance is set based on the current distance type.
     * 
     * @param distance The distance to set
     */
    public void setDistance(int distance) {
        if (distance >= MIN_DISTANCE && distance <= MAX_DISTANCE) {
            this.distance = distance;
            return;
        }
        setInitialDistance();
    }

    /**
     * Adjusts the bounding rectangle based on the given center place and distance.
     * 
     * @param center   The center place
     * @param distance The distance for filtering places
     */
    public void adjust(Place center, int distance) {
        this.placeCenter = center;
        setDistance(distance);
        this.boundary = new Rectangle(center.getLocation(), (int) this.distance * 2, (int) this.distance * 2);
        places.clear();
    }

    /**
     * Clears the bounding rectangle and sets it to the default values.
     */
    public void clear() {
        int side = Map2D.MAP_SIDE;
        Rectangle boundary = new Rectangle(0, side, side, side);
        Point center = boundary.getCenter();
        Place placeCenter = new Place();
        placeCenter.setLocation(center);

        this.distance = center.distance(boundary.getLocation());
        setPlaceCenter(placeCenter);
        setBoundary(boundary);
        this.places.clear();
    }

    /**
     * Returns the boundary rectangle.
     * 
     * @return The boundary rectangle
     */
    public Rectangle getBoundary() {
        return boundary;
    }

    /**
     * Sets the boundary rectangle.
     * 
     * @param boundary The boundary rectangle to set
     */
    public void setBoundary(Rectangle boundary) {
        this.boundary = boundary;
    }

    /**
     * Returns the list of places within the boundary.
     * 
     * @return The list of places
     */
    public ArrayList<Place> getPlaces() {
        return places;
    }

    /**
     * Sets the list of places within the boundary.
     * 
     * @param places The list of places to set
     */
    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    /**
     * Returns the center place.
     * 
     * @return The center place
     */
    public Place getPlaceCenter() {
        return placeCenter;
    }

    /**
     * Sets the center place.
     * 
     * @param center The center place to set
     */
    public void setPlaceCenter(Place center) {
        this.placeCenter = center;
    }

    /**
     * Returns the current sort option.
     * 
     * @return The sort option
     */
    public SortOption getSortOption() {
        return sortOption;
    }

    /**
     * Sets the sort option for sorting places.
     * 
     * @param sortOption The sort option to set
     */
    public void setSortOption(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    /**
     * Returns the current distance type.
     * 
     * @return The distance type
     */
    public DistanceType getDistanceType() {
        return distanceType;
    }

    /**
     * Sets the distance type for filtering places.
     * 
     * @param distanceType The distance type to set
     */
    public void setDistanceType(DistanceType distanceType) {
        this.distanceType = distanceType;
        setInitialDistance();
    }

    /**
     * Returns the current distance for filtering places.
     * 
     * @return The distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Adds a place to the list of places if it is within the distance range.
     * 
     * @param place The place to add
     * @return True if the place is added successfully, false otherwise
     */
    public boolean addPlace(Place place) {
        if (!places.contains(place) && placeCenter.distance(place) <= distance) {
            return places.add(place);
        }
        return false;
    }

    /**
     * Adds a list of places to the list of places within the boundary.
     * 
     * @param places The list of places to add
     */
    public void addPlaces(ArrayList<Place> places) {
        for (int i = 0; i < places.size(); i++) {
            this.addPlace(places.get(i));
        }
    }

    /**
     * Removes a place from the list of places within the boundary.
     * 
     * @param place The place to remove
     * @return True if the place is removed successfully, false otherwise
     */
    public boolean removePlace(Place place) {
        return places.remove(place);
    }

    /**
     * Displays the list of places within the boundary.
     * If there are no places, it prints a message indicating that there are no
     * places to show.
     */
    public void showPlaces() {
        if (places.isEmpty()) {
            System.out.println("No places to show.");
            return;
        }

        for (int i = 0; i < places.size(); i++) {
            if (i >= MAX_QUERIES)
                break;
            System.out.println(i + 1 + ". Distance: " + places.get(i).distance(placeCenter) + ", " + places.get(i));
        }
    }

    /**
     * Sorts the list of places within the boundary based on the current sort
     * option.
     */
    public void sortPlaces() {
        ArrayList<Place> sortedPlaces = mergeSort(places);
        setPlaces(sortedPlaces);
    }

    /**
     * Performs merge sort on the given list of places.
     * 
     * @param places The list of places to sort
     * @return The sorted list of places
     */
    public ArrayList<Place> mergeSort(ArrayList<Place> places) {
        if (places.size() <= 1)
            return places;

        int mid = places.size() / 2;
        ArrayList<Place> leftHalf = new ArrayList<>(mid);
        ArrayList<Place> rightHalf = new ArrayList<>(mid);

        for (int i = 0; i < places.size(); i++) {
            if (i >= mid) {
                rightHalf.add(places.get(i));
            } else {
                leftHalf.add(places.get(i));
            }
        }

        ArrayList<Place> sortedLeft = mergeSort(leftHalf);
        ArrayList<Place> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
    }

    /**
     * Merges two sorted lists of places into a single sorted list.
     * 
     * @param leftHalf  The left half of the list
     * @param rightHalf The right half of the list
     * @return The merged and sorted list of places
     */
    private ArrayList<Place> merge(ArrayList<Place> leftHalf, ArrayList<Place> rightHalf) {
        ArrayList<Place> merged = new ArrayList<>(leftHalf.size() + rightHalf.size());
        int i = 0;
        int j = 0;

        while (i < leftHalf.size() && j < rightHalf.size()) {
            if (compareForSort(leftHalf.get(i), rightHalf.get(j))) {
                merged.add(leftHalf.get(i));
                i++;
            } else {
                merged.add(rightHalf.get(j));
                j++;
            }
        }

        // Add remaining elements from leftHalf, if any
        while (i < leftHalf.size()) {
            merged.add(leftHalf.get(i));
            i++;
        }

        // Add remaining elements from rightHalf, if any
        while (j < rightHalf.size()) {
            merged.add(rightHalf.get(j));
            j++;
        }

        return merged;
    }

    /**
     * Returns a string representation of the bounding rectangle.
     * 
     * @return The string representation
     */
    @Override
    public String toString() {
        return "BoundingRectangle{" + "\n" +
                "boundary=" + boundary + "\n" +
                ", placeCenter=" + placeCenter + "\n" +
                ", total_places=" + places.size() +
                ", distance=" + distance + "\n" +
                '}';
    }
}