import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class PatientFrameTest {

    private PatientFrame patientFrame;

    // This method runs before each test. It initializes the PatientFrame instance.
    @BeforeEach
    public void setUp() {
        patientFrame = new PatientFrame(); // Creates the frame and initializes components
    }

    // This test checks if the JTextArea for displaying patient information is properly initialized.
    @Test
    public void testTextAreaIsInitialized() {
        JTextArea textArea = patientFrame.textArea; // Get the text area from PatientFrame
        assertNotNull(textArea, "Text area should be initialized."); // Ensure it is not null
    }
}
