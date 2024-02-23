import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private Connection connection;

    public TransactionHistory(Connection connection) {
        this.connection = connection;
        createTransactionTable();
    }

    public void addTransaction(String action, String car, String customer) {
        try {
            String sql = "INSERT INTO transaction (timestamp, action, car, customer) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, LocalDateTime.now());
                statement.setString(2, action);
                statement.setString(3, car);
                statement.setString(4, customer);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTransactionHistory() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transaction");
            System.out.println("\nTransaction History:");
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("id");
                LocalDateTime timestamp = resultSet.getObject("timestamp", LocalDateTime.class);
                String action = resultSet.getString("action");
                String car = resultSet.getString("car");
                String customer = resultSet.getString("customer");
                System.out.println("Transaction ID: " + transactionId);
                System.out.println("Timestamp: " + timestamp);
                System.out.println("Action: " + action);
                System.out.println("Car: " + car);
                System.out.println("Customer: " + customer);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTransactionTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "transaction", null);
            if (!resultSet.next()) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("CREATE TABLE transaction (" +
                            "id SERIAL PRIMARY KEY, " +
                            "timestamp TIMESTAMP, " +
                            "action VARCHAR(255), " +
                            "car VARCHAR(255), " +
                            "customer VARCHAR(255))");
                    System.out.println("Table 'transaction' created successfully.");
                }
            } else {
                System.out.println("Table 'transaction' already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}