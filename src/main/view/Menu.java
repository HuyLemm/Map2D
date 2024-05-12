package main.view;

import main.dataStructure.*;
import main.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class represents the menu for the Map Services application.
 * It provides options for inserting, removing, editing, and displaying places
 * on the map.
 * The menu is displayed in the console and user input is used to perform the
 * desired actions.
 * The menu also allows loading places from a file and displaying the tree map.
 * 
 * The class uses the Map2D and BoundingRectangle classes to store and
 * manipulate places and their locations.
 * It also uses the Place and ServiceType classes to represent individual places
 * and their services.
 * 
 * The menu is implemented using a switch statement and various helper methods.
 * Each menu option is associated with a specific action, such as inserting a
 * place or removing a place.
 * The menu also provides a return to main menu option to navigate back to the
 * main menu after completing an action.
 */
public class Menu {

    private final Scanner scanner;
    private Map2D map;
    private BoundingRectangle box;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.map = Map2D.getInstance();
        this.box = BoundingRectangle.getInstance();
    }

    public void header(String name) {
        System.out.println("=".repeat(40));
        System.out.println("*** " + name + " ***");
        System.out.println("=".repeat(40));
    }

    public void mainMenu() {
        header("Welcome to Map Services");
        System.out.println("Please choose one option: ");
        System.out.println("1. Insert a Place");
        System.out.println("2. Remove a Place");
        System.out.println("3. Edit a Place");
        System.out.println("4. Display Places");
        System.out.println("5. Search Places");
        System.out.println("6. Exit");
        System.out.println("7. Load Places");
        System.out.println("8. Display Tree");
        System.out.print("> Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> insertMenu();
            case 2 -> removeMenu();
            case 3 -> editMenu();
            case 4 -> displayMenu();
            case 5 -> searchMenu();
            case 6 -> exit();
            case 7 -> loadPlaces();
            case 8 -> displayTree();
            default -> System.out.println("Invalid choice");

        }
    }

    private void displayTree() {
        header("TREE MAP");
        map.display();
        returnToMain();
    }

    private void loadPlaces() {
        String fileName = "src/resources/places.txt";
        header("LOADING MAP...");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == Node.MAX_CAPACITY) {
                    String name = parts[0];
                    String[] servicesStr = parts[1].split("\\|");
                    ServiceType[] services = new ServiceType[servicesStr.length];
                    for (int i = 0; i < servicesStr.length; i++) {
                        servicesStr[i] = servicesStr[i].replace(" ", "_");
                        services[i] = ServiceType.valueOf(servicesStr[i].trim());
                    }
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    Place place = new Place(new Point(x, y), name, services);
                    if (map.insert(place)) {
                        System.out.println("Inserted " + place);
                        System.out.println(map.size());
                    } else {
                        System.out.println("Error inserting " + place);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading places from file.");
            e.printStackTrace();
        } finally {
            returnToMain();
        }
    }

    private void returnToMain() {
        try {
            System.out.println("Returning to Main Menu...");
            Thread.sleep(2000);
            mainMenu();
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void exit() {
        System.exit(0);
    }

    // Place(int id, String name, int x, int y, services)

    private String fieldBox(String field) {
        System.out.print(">> Enter " + field + ": ");
        return scanner.nextLine().trim();
    }

    private <T extends Enum<T>> Optional<T> pickAnEnum(Class<T> enumClass) {
        System.out.println("Choose one of the following options: ");

        T[] enums = enumClass.getEnumConstants();
        for (int i = 0; i < enums.length; i++) {
            System.out.println((i + 1) + ". " + enums[i]);
        }

        String choiceStr = fieldBox("your choice");
        try {
            int choice = Integer.parseInt(choiceStr);
            if (choice < 1 || choice > enums.length) {
                System.out.println("Invalid choice!");
                return Optional.empty();
            } else {
                return Optional.of(enums[choice - 1]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return Optional.empty();
        }
    }

    public void insertMenu() {
        header("INSERT A PLACE");
        System.out.println("Please enter place details:");
        String name = fieldBox("name of the place");
        int x = Integer.parseInt(fieldBox("x-coordinate"));
        int y = Integer.parseInt(fieldBox("y-coordinate"));

        Place newPlace = new Place();
        newPlace.setLocation(new Point(x, y));
        newPlace.setName(name);
        addServiceMenu(newPlace);

        System.out.println("Inserting *" + newPlace.getName() + "* into the map...");
        if (map.insert(newPlace)) {
            System.out.println("Insert a Place successfully!");
        } else {
            System.out.println("Insertion failed.");
        }
        returnToMain();

    }

    private void addServiceMenu(Place place) {
        while (true) {
            System.out.println(">>> Add a service <<<");
            if (place.isFullService()) {
                System.out.println("Number of services has reached maximum capacity (10).");
                break;
            }

            Optional<ServiceType> serviceOptional = pickAnEnum(ServiceType.class);
            if (serviceOptional.isEmpty()) {
                System.out.println("No valid service type found.");
                break;
            }

            ServiceType service = serviceOptional.get();
            if (place.addService(service)) {
                System.out.println(service + " is added.");
                System.out.println(place);
            } else {
                System.out.println("Duplicate service or Services is full for this place.");
            }

        }
    }

    private void removeServiceMenu(Place place) {
        while (true) {
            System.out.println(">>> Remove a service <<<");
            ArrayList<ServiceType> services = place.getServices();
            if (services.isEmpty()) {
                System.out.println("This place has no services.");
                break;
            }
            System.out.println("Choose a service to remove: ");
            for (int i = 0; i < place.getServices().size(); i++) {
                System.out.println(i + 1 + ". " + services.get(i));
            }
            String choiceStr = fieldBox("your choice");
            if (choiceStr.isBlank())
                break;
            int choice = Integer.parseInt(choiceStr);
            if (choice < 1 || choice > place.getServices().size()) {
                System.out.println("Invalid choice!");
                break;
            }
            ServiceType serviceToRemove = services.get(choice - 1);
            place.removeService(serviceToRemove);
            System.out.println("Service " + serviceToRemove + " is removed!");
            System.out.println("Place is updated: " + place);
        }
    }

    public Place createPartialPlace() {

        System.out.println("Attribute of the place: ");
        System.out.println("1. Name");
        System.out.println("2. Location");
        System.out.println("3. Service");
        System.out.println("4. Search all places");
        int choice = Integer.parseInt(fieldBox("your choice: "));

        Place partialPlace = new Place();
        switch (choice) {
            case 1:
                String name = fieldBox("name of the place: ");
                partialPlace.setName(name);
                break;
            case 2:
                int x = Integer.parseInt(fieldBox("x-coordinate: "));
                int y = Integer.parseInt(fieldBox("y-coordinate: "));
                partialPlace.setLocation(new Point(x, y));
                break;
            case 3:
                addServiceMenu(partialPlace);
                break;
            case 4:
                partialPlace = null;
                break;
            default:
                System.out.println("Wrong input.");
        }
        System.out.println("Partial Place created.");
        System.out.println(partialPlace);
        return partialPlace;
    }

    public void removeMenu() {
        header("REMOVE A PLACE");

        Place placeToRemove = getPlace();
        System.out.println("Place to removed: " + placeToRemove);
        if (placeToRemove == null) {
            System.out.println("Place not found!");
            removeMenu();
        }
        boolean isRemoved = map.remove(placeToRemove);
        if (isRemoved) {
            System.out.println("Remove a Place successfully!");
        } else {
            System.out.println("Place not found");
        }

        returnToMain();
    }

    public void editMenu() {
        header("EDIT A PLACE");
        setBoundingBox();
        Place placeToEdit = getPlace();
        if (placeToEdit == null) {
            System.out.println("Place not found!");
            editMenu();
        }
        System.out.println("Found the place: " + placeToEdit);
        System.out.println("Do you want to add or remove a service?");
        String operation = fieldBox("ADD or REMOVE");
        if (operation.equalsIgnoreCase("ADD")) {
            addServiceMenu(placeToEdit);
        } else if (operation.equalsIgnoreCase("REMOVE")) {
            removeServiceMenu(placeToEdit);
        }

        returnToMain();
    }

    public void displayMenu() {
        header("Map Structure");
        map.displayData();
        returnToMain();
    }

    public void searchMenu() {
        header("FIND PLACES");
        setBoundingBox();
        Place placeToCompare = createPartialPlace();
        ArrayList<Place> results = map.search(box, placeToCompare);
        box.showPlaces();
        returnToMain();
    }

    private void setBoundingBox() {
        System.out.println("Center: " + box.getPlaceCenter());
        System.out.println("Bounding box: " + box.getBoundary());
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Change location and distance");
        System.out.println("2. Change Sort Option");
        System.out.println("3. Search for the whole map.");
        System.out.println("4. Skip");

        String choiceStr = fieldBox("your choice: ");
        if (choiceStr.isBlank())
            returnToMain();
        int choice = Integer.parseInt(choiceStr);
        if (choice == 1) {
            int x = Integer.parseInt(fieldBox("x-coordinate: "));
            int y = Integer.parseInt(fieldBox("y-coordinate: "));
            int distance = Integer.parseInt(fieldBox("Distance: (-1 for picking Type)"));
            if (distance == -1) {
                Optional<BoundingRectangle.DistanceType> opt = pickAnEnum(BoundingRectangle.DistanceType.class);

                if (opt.isEmpty()) {
                    System.out.println("Invalid distance!");
                    returnToMain();
                }
                BoundingRectangle.DistanceType distanceType = opt.get();
                box.setDistanceType(distanceType);
                Place center = new Place();
                center.setLocation(new Point(x, y));
                box.adjust(center, distance);
            }
            // TO-DO
            Place center = new Place();
            center.setLocation(new Point(x, y));
            box.adjust(center, distance);
        } else if (choice == 2) {
            Optional<BoundingRectangle.SortOption> sortOptional = pickAnEnum(BoundingRectangle.SortOption.class);
            if (sortOptional.isPresent()) {
                BoundingRectangle.SortOption sortOption = sortOptional.get();
                box.setSortOption(sortOption);
                System.out.println("Set sort option to " + sortOption);
            } else {
                System.out.println("Failed to get sort option!");
            }
        } else if (choice == 3) {
            box.clear();
            System.out.println("Set to searching for the whole map.");
        } else if (choice == 4) {
            return;
        }
        System.out.println(box);
    }

    private Place getPlace() {
        Place placeToCompare = createPartialPlace();
        box.clear();
        ArrayList<Place> results = map.search(box, placeToCompare);
        if (results.isEmpty()) {
            return null;
        }
        results.display();
        int placeIndex = Integer.parseInt(fieldBox("your choice: "));
        return results.get(placeIndex - 1);
    }

}
