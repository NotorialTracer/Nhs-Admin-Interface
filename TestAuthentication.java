import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAuthentication {

    @Test
    public void testSuccessfulLogin() { Main signin = new Main(); String username = "Admin"; String password = "Password";

        // Check if correct credentials pass authentication
        boolean isAuthenticated = authenticate(username, password);
        assertTrue(isAuthenticated, "Login should be successful with correct credentials.");
    }

    @Test
    public void testIncorrectUsername() { Main signin = new Main(); String username = "WrongUser"; String password = "Password";

        // Check if wrong username fails authentication
        boolean isAuthenticated = authenticate(username, password);
        assertFalse(isAuthenticated, "Login should fail with an incorrect username.");
    }

    @Test
    public void testIncorrectPassword() { Main signin = new Main(); String username = "Admin"; String password = "WrongPassword";

        // Check if wrong password fails authentication
        boolean isAuthenticated = authenticate(username, password);
        assertFalse(isAuthenticated, "Login should fail with an incorrect password.");
    }

    @Test
    public void testIncorrectUsernameAndPassword() { Main signin = new Main(); String username = "WrongUser"; String password = "WrongPassword";

        // Check if both wrong username and password fail authentication
        boolean isAuthenticated = authenticate(username, password);
        assertFalse(isAuthenticated, "Login should fail when both username and password are incorrect.");
    }

    // Mock authentication
    private boolean authenticate(String username, String password) {return "Admin".equals(username) && "Password".equals(password);}
}
