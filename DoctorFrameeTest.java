import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorFrameeTest {

    private DoctorFramee doctorFramee;

    // Runs before each test. Initializes the DoctorFramee instance.
    @BeforeEach
    public void setUp() {
        doctorFramee = new DoctorFramee(); // Creates the frame and initializes components
    }

    // Checks if the JTextArea for displaying doctor information is properly initialized.
    @Test
    public void testTextAreaIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = DoctorFramee.class.getDeclaredField("textArea"); // Access private field
        field.setAccessible(true);
        JTextArea textArea = (JTextArea) field.get(doctorFramee);
        assertNotNull(textArea, "Text area should be initialized.");
    }
}
