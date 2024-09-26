# Vending Machine LLD

This is a simple implementation of a Vending Machine using Java. The Vending Machine allows users to select and purchase items, and administrators to manage the inventory. It provides a command-line interface for interaction.

## Code Files

The code files are organized under the following packages:
- `org.anuva04`: The main package that contains the `Main` class as the entry point of the program.
  - `Main.java`: **Entry point** of the program. It initializes the Vending Machine and starts the application.


- `org.anuva04.Controllers`: Contains the `VendingMachine`, `UserOperations`, and `AdminOperations` classes.
  - `VendingMachine.java`: Simulates the display and input options (keypad) of the Vending Machine. It initializes the machine and handles user and admin operations.
  - `UserOperations.java`: Implements the methods available to users. It displays the available items, handles user input, and performs the transaction.
  - `AdminOperations.java`: Implements the methods available to administrators. It handles admin authentication and provides functionality to manage the inventory.
  

- `org.anuva04.Models`: Contains the `Item` and `Tray` classes.
  - `Item.java`: Defines the `Item` class, representing a physical item that can be bought by a user.
  - `Tray.java`: Defines the `Tray` class, representing a tray in each slot of the Vending Machine. It contains items of the same type and manages item addition, removal, and retrieval.


- `org.anuva04.Constants`: Contains the `Constants` class that provides environment variables and secrets.
  - `Constants.java`: Provides environment variables and secrets, such as an emergency message and an admin password.

## Usage

To use the Vending Machine, follow these steps:

1. Ensure you have **JDK 11** and **Maven** installed on your system.
2. Clone the project repository and navigate to the project's root directory.
3. Build the project using the command: `mvn clean install`.
4. Run the program using the command: `mvn exec:java -Dexec.mainClass="org.anuva04.Main"`.
5. The program will display a home screen with two options: "1: User" and "2: Admin".
6. Proceed with the desired option as explained in the previous version of the README.