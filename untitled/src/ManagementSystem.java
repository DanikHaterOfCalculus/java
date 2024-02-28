import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.Iterator;


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
        loadCarsFromDatabase();
        loadCheckedOutVehiclesFromDatabase();


    }

    public void addNewCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter vehicle brand:");
        String brand = scanner.nextLine();
        System.out.println("Enter vehicle year:");
        int year = scanner.nextInt();
        System.out.println("Enter vehicle mileage:");
        int mileage = scanner.nextInt();
        scanner.nextLine();

        try {
            Vehicle newCar = VehicleFactory.createCar(brand, year, mileage);
            String sql = "INSERT INTO car (Brand, year, mileage) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, newCar.getBrand());
                statement.setInt(2, newCar.getYear());
                statement.setInt(3, newCar.getMileage());
                statement.executeUpdate();
            }
            System.out.println("Car added successfully!");
            loadCarsFromDatabase();
            loadCheckedOutVehiclesFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add car.");
        }
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
    private void loadCheckedOutVehiclesFromDatabase() {
        checkedoutRentals.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rent_account");
            while (resultSet.next()) {
                String customer = resultSet.getString("AccountName");
                String brand = resultSet.getString("Vehicle");
                int year = resultSet.getInt("Year");
                int mileage = resultSet.getInt("Mileage");
                int duration = resultSet.getInt("Duration");
                Rent rent = new Rent(customer, new Car(brand, year, mileage), duration);
                Iterator<Vehicle> iterator = vehicles.iterator();
                while (iterator.hasNext()) {
                    Vehicle vehicle = iterator.next();
                    if (vehicle.getBrand().equals(brand)) {
                        iterator.remove();
                        break;
                    }
                }

                checkedoutRentals.add(rent);
            }
            System.out.println("Checked-out vehicles loaded successfully from the database.");
        } catch (SQLException e) {
            System.out.println("Failed to load checked-out vehicles from the database.");
            e.printStackTrace();
        }
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
        loadCheckedOutVehiclesFromDatabase();
    }

    private void loadCarsFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Car");
            while (resultSet.next()) {
                String brand = resultSet.getString("Brand");
                int year = resultSet.getInt("Year");
                int mileage = resultSet.getInt("Mileage");
                vehicles.add(new Car(brand, year, mileage));
            }
            System.out.println("Cars loaded successfully from the database.");
        } catch (SQLException e) {
            System.out.println("Failed to load cars from the database.");
            e.printStackTrace();
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
            boolean alreadyExists = false;
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getBrand().equals(rent.getVehicle().getBrand())) {
                    alreadyExists = true;
                    break;
                }
            }
            if (!alreadyExists) {
                vehicles.add(rent.getVehicle());
                System.out.println("Vehicle returned successfully!");
                addToTransactionHistory(rent, "Return");
                removeReturnedVehicleFromDatabase(rent);
                loadCheckedOutVehiclesFromDatabase();
            } else {
                System.out.println("This vehicle is already available for rent.");
            }
        } else {
            System.out.println("This vehicle was not rented from our system.");
        }
    }




    public void addToTransactionHistory(Rent rent, String actionType) {
        if (actionType.equals("Rent")) {
            transactionHistory.addTransaction("Rent", rent.getVehicle().getBrand(), rent.getCustomer());
        } else if (actionType.equals("Return")) {
            transactionHistory.addTransaction("Return", rent.getVehicle().getBrand(), rent.getCustomer());
        }
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
            checkedoutRentals.add(rent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeReturnedVehicleFromDatabase(Rent rent) {
        try {
            String sql = "DELETE FROM rent_account WHERE AccountName = ? AND Vehicle = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, rent.getCustomer());
                statement.setString(2, rent.getVehicle().getBrand());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createRentAccountTable() {
        if (connection != null) {
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, "rent_account", null);
                if (!resultSet.next()) {
                    String sql = "CREATE TABLE rent_account (" +
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
                } else {
                    System.out.println("'rent_account' table already exists.");
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
            scanner.nextLine();
            Rent rent = new Rent(customerName, selectedVehicle, duration);
            checkOutVehicle(rent);
        } else {
            System.out.println("Invalid car selection.");
        }
    }

    public void displayAvailableCars() {
        boolean availableCarsExist = false;
        System.out.println("Available cars:");
        for (Vehicle vehicle : vehicles) {
            if (!isVehicleRented(vehicle)) {
                availableCarsExist = true;
                System.out.println("Brand: " + vehicle.getBrand() + ", Year: " + vehicle.getYear() + ", Mileage: " + vehicle.getMileage());
            }
        }
        if (!availableCarsExist) {
            System.out.println("No cars available for rent at the moment.");
        }
    }


    private boolean isVehicleRented(Vehicle vehicle) {
        for (Rent rent : checkedoutRentals) {
            if (rent.getVehicle().equals(vehicle)) {
                return true;
            }
        }
        return false;
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