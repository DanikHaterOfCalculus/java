import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:27030/Carrental";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Nurlan25";

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL server.");
            e.printStackTrace();
        }
        return connection;
    }
    public static void disconnect(Connection connection) {
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
}