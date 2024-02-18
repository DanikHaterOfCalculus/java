import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagementSystem {
    private List<Vehicle> vehicles;
    private List<Rent> checkedoutRentals;
    private TransactionHistory transactionHistory;
    private Connection connection;
    private static final Scanner scanner = new Scanner(System.in);

    public ManagementSystem(Connection connection) {
        this.vehicles = new ArrayList<>();
        this.checkedoutRentals = new ArrayList<>();
        this.transactionHistory = new TransactionHistory(connection);
        this.connection = DatabaseConnection.connect();
        createRentAccountTable();
        vehicles.add(new Car("Toyota Camry", 2021, 63000));
        vehicles.add(new Car("Honda Accord", 2020, 30000));
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.println("Vehicle added");
    }

    public void getAllVehicles() {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            System.out.println((i + 1) + ". Brand: " + vehicle.getBrand() + ", Year: " + vehicle.getYear() + ", Mileage: " + vehicle.getMileage());
        }
    }

    public List<Vehicle> getAllVehiclesList() {
        return vehicles;
    }

    public void checkOutVehicle(Rent rent) {
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().equals(rent.getVehicle().getBrand())) {
                found = true;
                vehicles.remove(vehicle);
                checkedoutRentals.add(rent);
                System.out.println("Vehicle checked out successfully!");
                addToTransactionHistory(rent, "Rent");
                break;
            }
        }
        if (!found) {
            System.out.println("Vehicle is not available for rent.");
        }
    }

    public void displayRentedVehicles() {
        if (checkedoutRentals.isEmpty()) {
            System.out.println("No vehicles rented yet.");
        } else {
            System.out.println("Rented vehicles:");
            for (Rent checkedOutRental : checkedoutRentals) {
                System.out.println("Customer: " + checkedOutRental.getCustomer() + ", Vehicle: " + checkedOutRental.getVehicle() + ", Duration: " + checkedOutRental.getDuration() + " days");
                System.out.println("-------------------------");
            }
        }
    }

    public Rent findRent(String customerName, String vehicleBrand) {
        for (Rent rent : checkedoutRentals) {
            if (rent.getCustomer().equals(customerName) && rent.getVehicle().getBrand().equals(vehicleBrand)) {
                return rent;
            }
        }
        return null;
    }

    public void returnVehicle(Rent rent) {
        boolean found = checkedoutRentals.remove(rent);
        if (found) {
            vehicles.add(rent.getVehicle());
            System.out.println("Vehicle returned successfully!");
            addToTransactionHistory(rent, "Return");
        } else {
            System.out.println("This vehicle was not rented from our system.");
        }
    }

    public void addToTransactionHistory(Rent rent, String actionType) {
        if (actionType.equals("Return")) {
            transactionHistory.addTransaction(actionType, rent.getVehicle().getBrand(), rent.getCustomer());
        }
        transactionHistory.addTransaction("Rent", rent.getVehicle().getBrand(), rent.getCustomer());
        try {
            String sql = "INSERT INTO rent_account (AccountName, Vehicle, Year, Mileage, Duration, RentDate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, rent.getCustomer());
                statement.setString(2, rent.getVehicle().getBrand());
                statement.setInt(3, rent.getVehicle().getYear());
                statement.setInt(4, rent.getVehicle().getMileage());
                statement.setInt(5, rent.getDuration());
                statement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createRentAccountTable() {
        if (connection != null) {
            try {
                String sql = "CREATE TABLE IF NOT EXISTS rent_account (" +
                        "id SERIAL PRIMARY KEY," +
                        "AccountName VARCHAR(100)," +
                        "Vehicle VARCHAR(100)," +
                        "Year INT," +
                        "Mileage INT," +
                        "Duration INT," +
                        "RentDate DATE" +
                        ")";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                    System.out.println("'rent_account' table created successfully.");
                }
            } catch (SQLException e) {
                System.out.println("Error creating 'rent_account' table: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to create 'rent_account' table because the database connection is null.");
        }
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void addNewCar() {
        System.out.println("Enter vehicle brand:");
        String brand = scanner.nextLine();
        System.out.println("Enter vehicle year:");
        int year = scanner.nextInt();
        System.out.println("Enter vehicle mileage:");
        int mileage = scanner.nextInt();
        scanner.nextLine();
        addVehicle(new Car(brand, year, mileage));
    }

    public void rentACar() {
        System.out.println("Enter your name:");
        String customerName = scanner.nextLine();
        System.out.println("Available vehicles:");
        getAllVehicles();
        System.out.println("Enter the number of the vehicle you want to rent:");
        int carChoice = scanner.nextInt();
        scanner.nextLine();
        if (carChoice >= 1 && carChoice <= getAllVehiclesList().size()) {
            Vehicle selectedVehicle = getAllVehiclesList().get(carChoice - 1);
            System.out.println("Enter rental duration (in days):");
            int duration = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            Rent rent = new Rent(customerName, selectedVehicle, duration);
            checkOutVehicle(rent);
        } else {
            System.out.println("Invalid car selection.");
        }
    }

    public void displayAvailableCars() {
        System.out.println("Available cars:");
        getAllVehicles();
    }

    public void displayRentingHistory() {
        System.out.println("History of renting:");
        getTransactionHistory().displayTransactionHistory();
    }

    public void returnRentedCar() {
        System.out.println("Return a rented car:");
        System.out.println("Enter your name:");
        String returnCustomerName = scanner.nextLine();
        System.out.println("Enter the brand of the car you want to return:");
        String returnCarBrand = scanner.nextLine();
        Rent returnRent = findRent(returnCustomerName, returnCarBrand);
        if (returnRent != null) {
            returnVehicle(returnRent);
        } else {
            System.out.println("You haven't rented a car with the specified details.");
        }
    }
}
