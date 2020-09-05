import java.sql.*;

public class SQLParser {

    static Connection connection = null;

    public static void makeJDBCConnection() {

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
        } catch(SQLException e) {
            System.out.println("Connection error");
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

    public static void insert(String tableName, String valueString) {
        String queryStatement = "INSERT INTO " + tableName + " VALUES " + valueString;
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String tableName, int id, String columnName, String valueString) {
        String queryStatement = "UPDATE " + tableName +
                " SET " + columnName +
                " = " + valueString +
                " WHERE id = " + id;
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(String tableName, int id, String columnName, int value) {
        String queryStatement = "UPDATE " + tableName +
                " SET " + columnName +
                " = " + value +
                " WHERE id = " + id;
        PreparedStatement sqlStatement;

        try {
            sqlStatement = connection.prepareStatement(queryStatement);
            sqlStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
