import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {

    @Test
    public void testAdminLoginSuccess() { Main signin = new Main(); String username = "Username"; String password = "Password";

        // Check if admin login is successful
        boolean isLoginSuccessful = validateAdminLogin(username, password);
        assertTrue(isLoginSuccessful, "Admin login should be successful.");
    }

    @Test
    public void testAdminLoginFailure() { Main signin = new Main(); String username = "WrongAdmin"; String password = "WrongPassword";

        // Check if incorrect admin login fails
        boolean isLoginSuccessful = validateAdminLogin(username, password);
        assertFalse(isLoginSuccessful, "Admin login should fail with incorrect credentials.");
    }

    // Mock login validation
    private boolean validateAdminLogin(String username, String password) {return "Admin".equals(username) && "Password".equals(password);}
}
