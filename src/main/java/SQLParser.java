import java.sql.*;

public class SQLParser {

    static Connection connection = null;

    // Singleton design pattern for class SQLParser
    private static final SQLParser instance = new SQLParser();

    private SQLParser() {}

    public static SQLParser getInstance() {

        return instance;
    }

    public static void connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC driver registration successful");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found. Check Maven dependency");
            e.printStackTrace();
            return;
        }

        try {
            // ToDo read account data from file (xml/json) and put it into .gitignore
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/phobos?serverTimezone=UTC",
                    "phobos", "phobos91.PzG");
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
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String queryStatement) {
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
