import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFrameTest {

    private BookingFrame bookingFrame;

    // Runs before each test. Initializes the BookingFrame instance.
    @BeforeEach
    public void setUp() {
        bookingFrame = new BookingFrame(); // Creates the frame and initializes components
    }

    // Checks if the patient ID combo box is properly initialized.
    @Test
    public void testPatientIdComboBoxIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = BookingFrame.class.getDeclaredField("patientIdCombo"); // Access private field
        field.setAccessible(true);
        JComboBox<String> patientComboBox = (JComboBox<String>) field.get(bookingFrame);
        assertNotNull(patientComboBox, "Patient ID ComboBox should be initialized.");
    }

    // Checks if the doctor ID label is properly initialized.
    @Test
    public void testDoctorIdLabelIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = BookingFrame.class.getDeclaredField("doctorIdLabel"); // Access private field
        field.setAccessible(true);
        JLabel doctorLabel = (JLabel) field.get(bookingFrame);
        assertNotNull(doctorLabel, "Doctor ID Label should be initialized.");
    }

    // Checks if the submit booking button is properly initialized.
    @Test
    public void testSubmitButtonIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = BookingFrame.class.getDeclaredField("submitBooking"); // Access private field
        field.setAccessible(true);
        JButton submitButton = (JButton) field.get(bookingFrame);
        assertNotNull(submitButton, "Submit Booking button should be initialized.");
    }
}
