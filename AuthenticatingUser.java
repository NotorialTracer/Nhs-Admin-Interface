import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticatingUser {

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM AdminLogin WHERE Username = ? AND Password = ?";
        try (Connection connection = Database1.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}