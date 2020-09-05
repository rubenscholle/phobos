import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
