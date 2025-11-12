import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    //creating an instance of the app and the login button to test it
    private Main signInPageTest;
    private JButton signInButtonTest;

    @BeforeEach
    public void setUp() {
        //starting the app and initalizing it
        signInPageTest = new Main();
        signInPageTest.main(new String[]{});

        //getting the login button using the getter in the SigninMain class
        signInButtonTest = signInPageTest.getLoginButton();
    }

    //making a test to check if the login button exists
    @Test
    public void testIfSignInButtonExists() {
        assertNotNull(signInButtonTest, "There is no login button, it should be here");
    }

    //making a test to check if the login button works
    @Test
    public void testIfSignInButtonWorks() {
        assertNotNull(signInButtonTest, "There is no login button, it should be here");

        //clicking the login button
        signInButtonTest.doClick();
    }
}
