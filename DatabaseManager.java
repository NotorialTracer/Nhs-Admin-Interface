//this is just example code to test the database connection like on moodle
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        db.testConnection();
    }

    private void testConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/gp_information_system?user=root&password=");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT FirstName, Surname, DOB FROM patientinformation");
            while (resultSet.next())
                System.out.println(resultSet.getString("FirstName") + " - " + resultSet.getString("Surname") + " - " + resultSet.getString("DOB"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
