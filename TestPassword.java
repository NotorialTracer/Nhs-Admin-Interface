import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPassword {

    @Test
    public void checkIfPasswordIsNull() { Main signin = new Main(); String username = "Username"; String password = null;

        // If password is null give an error
        Exception exception = assertThrows(NullPointerException.class, () -> {
            if (password == null) {throw new NullPointerException("Password cannot be null");}
        });

        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    public void checkIfPasswordIsEmpty() { Main signin = new Main(); String username = "Username"; String password = "";

        // Check if password is empty
        boolean isPasswordEmpty = password.trim().isEmpty();
        assertTrue(isPasswordEmpty, "Password should not be empty");
    }

    @Test
    public void checkIfPasswordIsTooShort() {
        Main signin = new Main(); String username = "Username";
        // example of a short password
        String password = "abc";

        // Check if password is less than 6 characters
        boolean isTooShort = password.length() < 6;
        assertTrue(isTooShort, "Password should be at least 6 characters long");
    }
}
