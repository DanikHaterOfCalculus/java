import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;
public class ManagementSystem {
    private List<Car> cars;
    private List<Rent> checkedoutcars;
    private TransactionHistory transactionHistory;
    private Connection connection;
    public ManagementSystem(Connection connection) {
        this.cars = new ArrayList<>();
        this.checkedoutcars = new ArrayList<>();
        this.transactionHistory = new TransactionHistory(connection);
        this.connection = DatabaseConnection.connect();
        createrentaccountTable();
        cars.add(new Car("Toyota Camry", 2021, 63000));
        cars.add(new Car("Honda Accord", 2020, 30000));
    }
    public void addcar(Car car) {
        cars.add(car);
        System.out.println("Car added");
    }
    public void getallcars() {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.println((i + 1) + ". Brand: " + car.getBrand() + ", Year: " + car.getYear() + ", Mileage: " + car.getMileage());
        }
    }
    public List<Car> GetAllcars() {
        return cars;
    }
    public void checkOutCar(Rent rent) {
        boolean found = false;
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car.getBrand().equals(rent.getCar().getBrand())) {
                found = true;
                cars.remove(car);
                checkedoutcars.add(rent);
                System.out.println("Car checked out successfully!");
                addToTransactionHistory(rent);
                break;
            }
        }
        if (!found) {
            System.out.println("Car is not available for rent.");
        }
    }
    public void displayrentedCars() {
        if (checkedoutcars.isEmpty()) {
            System.out.println("No cars rented yet.");
        } else {
            System.out.println("Rented cars:");
            for (Rent checkedOutRent : checkedoutcars) {
                System.out.println("Customer: " + checkedOutRent.getCustomer() + ", Car: " + checkedOutRent.getCar() + ", Duration: " + checkedOutRent.getDuration() + " days");
                System.out.println("-------------------------");
            }
        }
    }
    public void addToTransactionHistory(Rent rent) {
        transactionHistory.addTransaction("Rent", rent.getCar().getBrand(), rent.getCustomer());
        try {
            String sql = "INSERT INTO RentAccount (AccountName, Car, Year, Milleage, Duration, RentDate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, rent.getCustomer());
                statement.setString(2, rent.getCar().getBrand());
                statement.setInt(3, rent.getYear());
                statement.setInt(4, rent.getCar().getMileage());
                statement.setInt(5, rent.getDuration());
                statement.setDate(6, new java.sql.Date(System.currentTimeMillis()));  // Use current date for RentDate
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
    private void createrentaccountTable() {
        if (connection != null) {
            try {
                String sql = "CREATE TABLE IF NOT EXISTS rentaccount (" +
                        "id SERIAL PRIMARY KEY," +
                        "AccountName VARCHAR(100)," +
                        "Car VARCHAR(100)," +
                        "Year INT," +
                        "Milleage INT," +
                        "Duration INT," +
                        "RentDate DATE" +
                        ")";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                    System.out.println("'rentaccount' table created successfully.");
                }
            } catch (SQLException e) {
                System.out.println("Error creating 'rentaccount' table: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to create 'rentaccount' table because the database connection is null.");
        }
    }

    private void closeDatabaseConnection() {
        DatabaseConnection.disconnect(connection);
    }
}







