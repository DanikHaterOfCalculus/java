import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.connect();
        if (connection != null) {
            ManagementSystem managementSystem = new ManagementSystem(connection);
            runCarRentalSystem(managementSystem);
        }
    }

    private static void runCarRentalSystem(ManagementSystem managementSystem) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    managementSystem.addNewCar();
                    break;
                case 2:
                    managementSystem.rentACar();
                    break;
                case 3:
                    managementSystem.displayAvailableCars();
                    break;
                case 4:
                    managementSystem.displayRentedVehicles();
                    break;
                case 5:
                    managementSystem.displayRentingHistory();
                    break;
                case 6:
                    managementSystem.returnRentedCar();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nCar rental management system:");
        System.out.println("1. Add a new vehicle");
        System.out.println("2. Rent a vehicle");
        System.out.println("3. Display available vehicles");
        System.out.println("4. Display rented vehicles");
        System.out.println("5. History of renting");
        System.out.println("6. Return a vehicle");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }
}
