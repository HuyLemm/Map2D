# Map2D Application by GROUP21 

## Video Demonstration
Youtube Link - https://youtu.be/lDwtWXCH3i8

## Overview
The Map2D application is a Java-based system designed to manage geographical places on a 2D map. It allows users to add, remove, edit, and display places, as well as perform detailed searches based on various attributes such as location, services provided, and name. The system utilizes a quad-tree data structure for efficient spatial indexing and querying.

## Features
- **Manage Places**: Insert, remove, and edit places with attributes like name, location, and services.
- **Display Options**: Visualize places and their information on the map.
- **Search Functionality**: Perform advanced searches within a defined bounding box or across the entire map, with options to sort by distance or name.
- **Interactive Menu**: Navigate through the application using a user-friendly console-based menu system.
- **File Loading**: Populate the map with places from a predefined file, facilitating easy data management.

## Installation
1. **Prerequisites**:
    - Java JDK 11 or later must be installed on your machine.
    - Ensure Java is properly set in your system's PATH.

2. **Clone the Repository**:
   ```
   git clone https://github.com/temmmy/COSC2658_ASM3_G21.git
   ```

3. **Compile the Source Code**:
   Navigate to the source directory and compile the Java files:
   ```
   javac main/*.java main/model/*.java main/view/*.java main/dataStructure/*.java
   ```

4. **Run the Application**:
   Execute the main class to launch the application:
   ```
   java main.Application
   ```

## Usage
Upon running the application, you will be greeted with the main menu which offers the following options:

1. **Insert a Place**: Add a new place by specifying its name, location, and the services it offers.
2. **Remove a Place**: Remove an existing place from the map.
3. **Edit a Place**: Modify the details of an existing place.
4. **Display Places**: Show all places currently loaded on the map.
5. **Search Places**: Search for places based on different criteria within a specified area or across the entire map.
6. **Exit**: Terminate the application.
7. **Load Places**: Load places from a file.
8. **Display Tree**: Visualize the underlying quad-tree structure.

Follow the on-screen prompts to navigate through the various functionalities.

## Code Structure
The application is divided into several packages:
- **main.model**: Contains the core data structures and logic for place management and quad-tree operations.
- **main.view**: Houses the user interface components, specifically the menu system for interacting with the application.
- **main.dataStructure**: Includes custom implementations of data structures used throughout the application.
- **main.util**: Contains utility classes and functions used throughout the application.

## Contributing
Contributions are welcome. Please fork the repository, make your changes, and submit a pull request.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.

## Authors
- GROUP 15
    - Nguyen Chi Nghia - Contributing score: 5
    - Duong Viet Hoang - Contributing score: 5
    - Nguyen Huy Anh - Contributing score: 5
    - Vu Tien Quang - Contributing score: 5

Feel free to reach out to any of the contributors for questions or discussions regarding the project.

