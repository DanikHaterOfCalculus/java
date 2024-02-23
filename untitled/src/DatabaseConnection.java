import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:27030/Carrental";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Nurlan25";
    private Connection connection;

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL server.");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        return singleton.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the PostgreSQL server.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection.");
                e.printStackTrace();
            }
        }
    }

    private static class singleton {//SINGLETON PATTERN
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static Connection connect() {
        return getInstance().getConnection();
    }
}