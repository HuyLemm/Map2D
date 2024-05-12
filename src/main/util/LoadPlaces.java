package main.util;

import main.dataStructure.Point;
import main.model.Map2D;
import main.model.Place;
import main.model.ServiceType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is responsible for loading places from a file into a Map2D object.
 * It reads the file line by line, parses the data, and creates Place objects
 * with the extracted information. The created Place objects are then inserted
 * into the Map2D object.
 * 
 * The file format should be as follows:
 * - Each line represents a place.
 * - Each line should contain four comma-separated values: name, services,
 * x-coordinate, and y-coordinate.
 * - The services should be pipe-separated (|) values.
 * 
 * Example file content:
 * Place1,PARK|CAFE,100,200
 * Place2,RESTAURANT|HOTEL,300,400
 * 
 * The LoadPlaces class provides a static method 'loadPlacesFromFile' that takes
 * the filename and a Map2D object as parameters. It reads the file, creates
 * Place objects, and inserts them into the Map2D object.
 * 
 * The class also contains a main method for testing the loading functionality.
 * It creates a Map2D object, calls the 'loadPlacesFromFile' method, and
 * displays
 * the loaded data to verify the correctness.
 * 
 * Note: This class assumes that the file is in the correct format and does not
 * handle any exceptions related to incorrect file format or missing data.
 * 
 */
public class LoadPlaces {

    public static void loadPlacesFromFile(String filename, Map2D map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String[] servicesStr = parts[1].split("\\|");
                    ServiceType[] services = new ServiceType[servicesStr.length];
                    for (int i = 0; i < servicesStr.length; i++) {
                        servicesStr[i] = servicesStr[i].replace(" ", "_");
                        services[i] = ServiceType.valueOf(servicesStr[i].trim());
                    }
                    int x = Integer.parseInt(parts[2].trim());
                    int y = Integer.parseInt(parts[3].trim());
                    Place place = new Place(new Point(x, y), name, services);
                    map.insert(place);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map2D map = Map2D.getInstance();
        loadPlacesFromFile("src/resources/places.txt", map);
        // Display loaded data to verify
        map.displayData();
    }
}
