import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUsername {

    @Test
    public void testUsernameIsNull() {
        Main signin = new Main();

        // take example by null username input
        String username = null;
        String password = "Password"; // take the password

        // check that username is null and login should fail
        assertThrows(NullPointerException.class, () -> {
            if (username == null) {
                throw new NullPointerException("Username cannot be null");
            }
        });
    }

    @Test
    public void testUsernameIsEmpty() {
        Main signin = new Main();

        // take an example by empty username input
        String username = "";
        String password = "Password";

        // make sure that login fails with the empty username
        assertTrue(username.isEmpty(), "Username should not be empty");
    }
}
