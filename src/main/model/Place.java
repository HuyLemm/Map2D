package main.model;

import main.dataStructure.ArrayList;
import main.dataStructure.Point;

import java.util.Objects;

/**
 * Represents a place with a name, services, and location.
 * A place can have multiple services and is identified by its name and
 * location.
 * Provides methods to add and remove services, check if it is full of services,
 * calculate the distance to another place, and compare with another place.
 */
public class Place {
        public static final int MAX_SERVICES = 10;
        private String name;
        private ArrayList<ServiceType> services;
        private Point location;

        /**
         * Constructs a new Place object with default values.
         * The location is set to (0, 0), services list is empty, and name is null.
         */
        public Place() {
                this.location = new Point();
                this.services = new ArrayList<>(MAX_SERVICES);
                this.name = null;
        }

        /**
         * Constructs a new Place object with the given location, name, and services.
         *
         * @param location The location of the place.
         * @param name     The name of the place.
         * @param services The array of services provided by the place.
         */
        public Place(Point location, String name, ServiceType[] services) {
                this.location = location;
                this.services = new ArrayList<>(services);
                this.name = name;
        }

        /**
         * Returns the name of the place.
         *
         * @return The name of the place.
         */
        public String getName() {
                return name;
        }

        /**
         * Sets the name of the place.
         *
         * @param name The name of the place.
         */
        public void setName(String name) {
                this.name = name;
        }

        /**
         * Returns the list of services provided by the place.
         *
         * @return The list of services provided by the place.
         */
        public ArrayList<ServiceType> getServices() {
                return services;
        }

        /**
         * Sets the list of services provided by the place.
         *
         * @param services The list of services provided by the place.
         */
        public void setServices(ArrayList<ServiceType> services) {
                this.services = services;
        }

        /**
         * Returns the location of the place.
         *
         * @return The location of the place.
         */
        public Point getLocation() {
                return location;
        }

        /**
         * Sets the location of the place.
         *
         * @param location The location of the place.
         */
        public void setLocation(Point location) {
                this.location = location;
        }

        /**
         * Calculates the distance between this place and another place.
         *
         * @param place The other place to calculate the distance to.
         * @return The distance between this place and the other place.
         */
        public int distance(Place place) {
                Point p1 = getLocation();
                Point p2 = place.getLocation();

                return p1.distance(p2);
        }

        /**
         * Adds a new service to the place.
         *
         * @param newService The new service to add.
         * @return true if the service was added successfully, false otherwise.
         */
        public boolean addService(ServiceType newService) {
                if (services.contains(newService))
                        return false;
                return services.add(newService);
        }

        /**
         * Removes a service from the place.
         *
         * @param serviceToRemove The service to remove.
         * @return true if the service was removed successfully, false otherwise.
         */
        public boolean removeService(ServiceType serviceToRemove) {
                return services.remove(serviceToRemove);
        }

        /**
         * Checks if the place is full of services.
         *
         * @return true if the place is full of services, false otherwise.
         */
        public boolean isFullService() {
                return services.size() == MAX_SERVICES;
        }

        /**
         * Compares this place with another place for partial equality.
         * Two places are considered partially equal if they have the same name,
         * location, or at least one common service.
         *
         * @param p The other place to compare with.
         * @return true if the places are partially equal, false otherwise.
         */
        public boolean partialEquals(Place p) {
                if (p == null)
                        return false;

                boolean nameMatch = (name != null && name.equals(p.getName()));
                boolean locationMatch = (location != null && location.equals(p.getLocation()));

                // Checks if there's any common service between this Place and Place p
                boolean serviceMatch = false;
                if (services != null && p.getServices() != null) {
                        serviceMatch = services.contains(p.getServices());
                }

                if (nameMatch) {
                        System.out.println("name matched: " + name);
                }
                if (locationMatch) {
                        System.out.println("location matched: " + location);
                }
                if (serviceMatch) {
                        System.out.println("service matched");
                }

                return nameMatch || locationMatch || serviceMatch;
        }

        /**
         * Returns a string representation of the place.
         *
         * @return A string representation of the place.
         */
        @Override
        public String toString() {
                // Convert each ServiceType object to a string
                String[] serviceStrings = new String[services.size()];
                for (int i = 0; i < services.size(); i++) {
                        serviceStrings[i] = services.get(i).toString();
                }
                // Join the service strings with commas
                String serviceStr = String.join(", ", serviceStrings);
                return "Place{" +
                                "name='" + name + '\'' +
                                ", services=[" + serviceStr + "]" + '\'' +
                                ", location=" + location +
                                '}';
        }

        /**
         * Checks if this place is equal to another object.
         * Two places are considered equal if they have the same name, services, and
         * location.
         *
         * @param o The object to compare with.
         * @return true if the places are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;
                Place place = (Place) o;
                return Objects.equals(getName(), place.getName()) && Objects.equals(getServices(), place.getServices())
                                && Objects.equals(getLocation(), place.getLocation());
        }

        /**
         * Returns the hash code value for the place.
         *
         * @return The hash code value for the place.
         */
        @Override
        public int hashCode() {
                return Objects.hash(getName(), getServices(), getLocation());
        }
}
