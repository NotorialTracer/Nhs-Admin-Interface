import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class homePageTest {
    //creating an instance of the app and the viewpatients button to test it
    private homePage MainCopy;
    private JMenuItem viewPatientsButtonTest;

    @BeforeEach
    public void setUp() {
        //starting the app and initializing it
        MainCopy = new homePage();
        MainCopy.main(new String[]{});

        //getting the login button using the getter in the Main
        viewPatientsButtonTest = MainCopy.getViewPatientsButton();
    }

    //making a test to check if the viewpatients button exists
    @Test
    public void testIfViewPatientsButtonExists() {
        // Check that the button was found and is not null
        assertNotNull(viewPatientsButtonTest, "The 'View Patients' button should be present.");
    }

    //making a test to check if the viewpatients button works
    @Test
    public void testIfClickViewPatientsButton() {
        assertNotNull(viewPatientsButtonTest, "The 'View Patients' button should be present.");

        //clicking the patients button
        viewPatientsButtonTest.doClick();
    }

}
