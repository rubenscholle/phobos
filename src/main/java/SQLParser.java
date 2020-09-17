import java.sql.*;

public class SQLParser {

    static Connection connection = null;

    // Singleton design pattern for class SQLParser
    private SQLParser() {}

    public static void connect() {

        PropertiesInjector propertiesInjector = new PropertiesInjector("src/main/resources/properties.xml");
        // Establish connection to a specific database
        try {
            Class.forName(propertiesInjector.getDb_driver());
            System.out.println("JDBC driver registration successful");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found. Check Maven dependency");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(
                    propertiesInjector.getDb_server(),
                    propertiesInjector.getDb_user(),
                    propertiesInjector.getDb_password());
            if (connection != null) {
                System.out.println("Connection successful");
            } else {
                System.out.println("Connection failed");
            }
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    public static void disconnect() {

        // Close current database connection
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }  else {
                System.out.println("Connection closing failed. No connection established yet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet select(String tableName) {

        // SELECT query to currently connected database
        String queryStatement;
        PreparedStatement sqlStatement;
        ResultSet result = null;

        try {
            queryStatement = "SELECT * FROM " + tableName;
            sqlStatement = connection.prepareStatement(queryStatement);
            result = sqlStatement.executeQuery();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void insert(String queryStatement) {

        // INSERT query to currently connected database
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String queryStatement) {

        // UPDATE query to currently connected database
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
