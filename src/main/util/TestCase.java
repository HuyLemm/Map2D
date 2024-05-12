package main.util;

import main.dataStructure.Point;
import main.model.BoundingRectangle;
import main.model.Map2D;
import main.model.Place;
import main.model.ServiceType;

/**
 * This class contains test cases for the Map2D class and its functionalities.
 * It includes methods to test various scenarios such as inserting and removing
 * places from the QuadTree,
 * testing boundary conditions, adding and removing services from a place, and
 * performing search operations.
 * Each test case is implemented as a separate method.
 */
public class TestCase {
    public static void main(String[] args) {
        // Initialize the QuadTree
        Map2D map = Map2D.getInstance();
        // Populate the QuadTree with sample data
        populateQuadTree(map);
        map.display();

        // testInsertToEmptyTree(); //Test case 1
        // testRemoveFromEmptyTree(); //Test case 2
        // testFillTreeToMaxCapacity(); //Test case 3
        // testInsertToFullTree(); //Test case 4
        // testRemoveFromFullTree();//Test case 5
        // testInsertToFullNode(); //Test case 6
        // testRemoveFromFullNode(); //Test case 7
        // testInsertToNonFullNode();//Test case 8
        // testAddServiceToFullList(); //Test case 9
        // testRemoveServiceFromFullList(); //Test case 10
        // testAddNonExistentService(); //Test case 11
        // testRemoveNonExistentService(); //Test case 12
        // testAddService(); //Test case 13
        // testRemoveService(); //Test case 14
        // testRemoveFromNonFullNode();// Test case 15
        // testInsertOutsideBoundary(); //Test case 16
        // testAddServiceToEmptyList(); //Test case 17
        // testRemoveServiceFromEmptyList(); //Test case 18
        // testFillServiceListToMaxCapacity(); //Test case 19
        // testSearchAllWithinBox(map); //Test case 20
        // testSearchWithinBoxWithParams(map); //Test case 21
        // testSearchWithParamsNoBox(map); //Test case 22
        // testSearchNoIntersect(map); //Test case 23
        // testSearchIntersectsMultipleNodes(map);//Test case 24
    }

    private static void populateQuadTree(Map2D map) {
        // Populate the QuadTree with Place objects in specific locations
        map.insert(new Place(new Point(150, 250), "Place 2", new ServiceType[] { ServiceType.HOSPITAL }));
        map.insert(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        map.insert(new Place(new Point(500, 600), "Place 4", new ServiceType[] { ServiceType.HOTEL }));
        map.insert(new Place(new Point(700, 800), "Place 5", new ServiceType[] { ServiceType.SCHOOL }));
        map.insert(new Place(new Point(900, 1000), "Place 5", new ServiceType[] { ServiceType.GYM }));
    }

    private static void testInsertToEmptyTree() {
        Map2D map = Map2D.getInstance();
        boolean result = map
                .insert(new Place(new Point(500, 500), "Cafe Central", new ServiceType[] { ServiceType.CAFE }));
        System.out.println("Insert to empty tree: " + result);
        map.displayData();
    }

    private static void testRemoveFromEmptyTree() {
        Map2D map = Map2D.getInstance();
        boolean result = map
                .remove(new Place(new Point(1000, 1000), "Nonexistent Place", new ServiceType[] { ServiceType.HOTEL }));
        System.out.println("Remove from empty tree: " + result);
    }

    private static void testFillTreeToMaxCapacity() {
        Map2D map = Map2D.getInstance();
        for (int i = 0; i < Map2D.MAX_PLACES; i++) {
            map.insert(new Place(new Point(200, 200), "Place " + i, new ServiceType[] { ServiceType.STORE }));
        }
        System.out.println("Filled tree to its maximum capacity.");
    }

    private static void testInsertToFullTree() {
        Map2D map = Map2D.getInstance();
        testFillTreeToMaxCapacity();
        boolean result = map
                .insert(new Place(new Point(200, 200), "Extra Place", new ServiceType[] { ServiceType.GYM }));
        System.out.println("Insert to full node: " + result);
    }

    private static void testRemoveFromFullTree() {
        Map2D map = Map2D.getInstance();
        for (int i = 0; i < Map2D.MAX_PLACES - 1; i++) {
            map.insert(new Place(new Point(200, 200), "Place " + i, new ServiceType[] { ServiceType.STORE }));
        }
        map.insert(new Place(new Point(), "Place remove", new ServiceType[] { ServiceType.GYM }));
        Place place = new Place();
        place.setName("Place remove");
        boolean result = map.remove(place);
        System.out.println("Remove from full tree: " + !result);
    }

    private static void testInsertToFullNode() {
        Map2D map = Map2D.getInstance();
        map.insert(new Place(new Point(150, 250), "Place 2", new ServiceType[] { ServiceType.HOSPITAL }));
        map.insert(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        map.insert(new Place(new Point(500, 600), "Place 4", new ServiceType[] { ServiceType.HOTEL }));
        map.insert(new Place(new Point(700, 800), "Place 5", new ServiceType[] { ServiceType.SCHOOL }));
        map.display();
        // node full data, started to insert.
        System.out.println("After inserting: ");
        map.insert(new Place(new Point(900, 1000), "Place 5", new ServiceType[] { ServiceType.GYM }));
        map.display();

    }

    private static void testRemoveFromFullNode() {
        Map2D map = Map2D.getInstance();
        map.insert(new Place(new Point(150, 250), "Place 2", new ServiceType[] { ServiceType.HOSPITAL }));
        map.insert(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        map.insert(new Place(new Point(500, 600), "Place 4", new ServiceType[] { ServiceType.HOTEL }));
        map.insert(new Place(new Point(700, 800), "Place 5", new ServiceType[] { ServiceType.SCHOOL }));
        map.display();
        System.out.println("After inserting: ");
        boolean result = map
                .remove(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        System.out.println("Remove from full node: " + result);
        map.display();
    }

    private static void testInsertToNonFullNode() {
        Map2D map = Map2D.getInstance();
        map.insert(new Place(new Point(150, 250), "Place 2", new ServiceType[] { ServiceType.HOSPITAL }));
        map.insert(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        map.insert(new Place(new Point(500, 600), "Place 4", new ServiceType[] { ServiceType.HOTEL }));
        map.display();
        // node full data, started to insert.
        System.out.println("After inserting: ");
        map.insert(new Place(new Point(900, 1000), "Place 5", new ServiceType[] { ServiceType.GYM }));
        map.display();
    }

    private static void testRemoveFromNonFullNode() {
        Map2D map = Map2D.getInstance();
        map.insert(new Place(new Point(150, 250), "Place 2", new ServiceType[] { ServiceType.HOSPITAL }));
        map.insert(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        map.insert(new Place(new Point(500, 600), "Place 4", new ServiceType[] { ServiceType.HOTEL }));
        map.display();
        System.out.println("After inserting: ");
        boolean result = map
                .remove(new Place(new Point(300, 400), "Place 3", new ServiceType[] { ServiceType.RESTAURANT }));
        System.out.println("Remove from full node: " + result);
        map.display();
    }

    private static void testInsertOutsideBoundary() {
        ;
        Map2D map = Map2D.getInstance();
        boolean result = map.insert(new Place(new Point(Map2D.MAP_SIDE + 100, Map2D.MAP_SIDE + 100),
                "Out of Bounds Place", new ServiceType[] { ServiceType.ATM }));
        System.out.println("Insert to a box outside boundary: " + result);
    }

    private static void testAddServiceToEmptyList() {
        Place place = new Place();
        place.setLocation(new Point(0, 0));
        place.setName("Empty Cafe");
        System.out.println(place);
        boolean result = place.addService(ServiceType.CAFE);
        System.out.println("Add 1 service to an empty service list: " + result);
        System.out.println(place);
    }

    private static void testRemoveServiceFromEmptyList() {
        Place place = new Place(new Point(0, 0), "Empty Cafe", new ServiceType[] {});
        boolean result = place.removeService(ServiceType.CAFE);
        System.out.println("Remove 1 service from an empty service list: " + result);
    }

    private static void testFillServiceListToMaxCapacity() {
        boolean check = false;
        int count = 0;
        Place place = new Place();
        place.setLocation(new Point(0, 0));
        place.setName("Empty Cafe");
        for (int i = 0; i < Place.MAX_SERVICES; i++) {
            place.addService(ServiceType.values()[i % ServiceType.values().length]); // Adding unique services assuming
                                                                                     // enough types
            count++;
        }
        if (count == 9)
            check = true;
        System.out.println("Fill service to max capacity: " + !check);

    }

    private static void testAddServiceToFullList() {
        Place place = new Place(new Point(0, 0), "Popular Cafe",
                new ServiceType[] { ServiceType.ATM, ServiceType.HOSPITAL, ServiceType.RESTAURANT, ServiceType.CAFE,
                        ServiceType.HOTEL, ServiceType.SCHOOL, ServiceType.PARK, ServiceType.CINEMA, ServiceType.GYM,
                        ServiceType.STORE });
        System.out.println(place);
        boolean result = place.addService(ServiceType.LIBRARY); // Attempt to add another
        System.out.println("Add 1 service to a full service list (expect failure): " + !result);
    }

    private static void testRemoveServiceFromFullList() {
        Place place = new Place(new Point(0, 0), "Popular Cafe",
                new ServiceType[] { ServiceType.ATM, ServiceType.HOSPITAL, ServiceType.RESTAURANT, ServiceType.CAFE,
                        ServiceType.HOTEL, ServiceType.SCHOOL, ServiceType.PARK, ServiceType.CINEMA, ServiceType.GYM,
                        ServiceType.STORE });
        boolean result = place.removeService(ServiceType.HOTEL);
        System.out.println(place);
        System.out.println("Remove 1 service from a full service list: " + result);
    }

    private static void testAddNonExistentService() {
        Place place = new Place();
        place.setLocation(new Point(0, 0));
        place.setName("Empty Cafe");
        System.out.println(place);
        boolean result = place.addService(ServiceType.CAFE);
        System.out.println("Add 1 service to an empty service list: " + result);
        System.out.println(place);
        System.out.println("Point 1 has services: " + place.getServices().get(0));
        System.out.println("Adding HOSPITAL: " + place.addService(ServiceType.HOSPITAL));
        System.out.println("Adding SCHOOL: " + place.addService(ServiceType.SCHOOL));
        System.out.println("Adding RESTAURANT: " + place.addService(ServiceType.RESTAURANT));
        System.out.println(place);
    }

    private static void testRemoveNonExistentService() {
        Place place = new Place(new Point(0, 0), "Cafe", new ServiceType[] { ServiceType.ATM });
        boolean result = place.removeService(ServiceType.HOTEL);
        System.out.println("Remove 1 service that does not exist in the service list: " + result);
    }

    private static void testAddService() {
        Place place = new Place(new Point(100, 200), "Place 1", new ServiceType[] { ServiceType.CAFE });
        System.out.println(place);
        boolean result = place.addService(ServiceType.HOSPITAL);
        System.out.println("Add 1 service to a service list: " + result);
        System.out.println(place);
    }

    private static void testRemoveService() {
        Place place = new Place(new Point(0, 0), "Cafe", new ServiceType[] { ServiceType.CAFE });
        boolean result = place.removeService(ServiceType.CAFE);
        System.out.println("Remove 1 service from the service list: " + result);
    }

    private static void testSearchAllWithinBox(Map2D map) {
        System.out.println("Testing search all within box...");
        Place point_1 = new Place(new Point(400, 400), "Place 1", new ServiceType[] { ServiceType.CAFE });
        Place placecenter = point_1;
        BoundingRectangle box = BoundingRectangle.getInstance();
        box.setDistanceType(BoundingRectangle.DistanceType.WALKING);
        box.adjust(placecenter, -1);
        map.search(box, null);
        box.showPlaces();
    }

    private static void testSearchWithinBoxWithParams(Map2D map) {
        map.displayData();
        System.out.println("Testing search within box with specific parameters...");
        Place point_1 = new Place(new Point(400, 400), "Place 1", new ServiceType[] { ServiceType.CAFE });
        Place placecenter = point_1;
        BoundingRectangle box = BoundingRectangle.getInstance();
        box.setDistanceType(BoundingRectangle.DistanceType.WALKING); // 100
        box.adjust(placecenter, -1);
        Place searchCriteria = new Place();
        searchCriteria.setName("Place 2");
        map.search(box, searchCriteria);
        box.showPlaces();
    }

    private static void testSearchWithParamsNoBox(Map2D map) {
        BoundingRectangle box = BoundingRectangle.getInstance();
        box.clear();
        System.out.println("Testing search with parameters without specifying a box...");
        Place searchCriteria = new Place();
        searchCriteria.setName("Place 2");
        map.search(box, searchCriteria);
        box.showPlaces();
    }

    private static void testSearchNoIntersect(Map2D map) {
        map.displayData();
        System.out.println("Testing search within box with specific parameters...");
        Place point_1 = new Place(new Point(10000, 20000), "Place 1", new ServiceType[] { ServiceType.CAFE });
        Place placecenter = point_1;
        BoundingRectangle box = BoundingRectangle.getInstance();
        box.setDistanceType(BoundingRectangle.DistanceType.NEAR); // 100
        box.adjust(placecenter, -1);
        Place searchCriteria = new Place();
        searchCriteria.setName("Place 2");
        map.search(box, searchCriteria);
        box.showPlaces();
    }

    private static void testSearchIntersectsMultipleNodes(Map2D map) {
        System.out.println("Testing search that intersects multiple nodes...");
        Place point_1 = new Place(new Point(400, 400), "Place 1", new ServiceType[] { ServiceType.CAFE });
        Place placecenter = point_1;
        BoundingRectangle box = BoundingRectangle.getInstance();
        box.setDistanceType(BoundingRectangle.DistanceType.WALKING);
        box.adjust(placecenter, -1);
        Place searchCriteria = new Place();
        searchCriteria.setName("Place 3");
        map.search(box, searchCriteria);
        box.showPlaces();
    }
}
