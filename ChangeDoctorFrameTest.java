import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeDoctorFrameTest {

    private ChangeDoctorFrame changeDoctorFrame;

    // Runs before each test. Initializes the ChangeDoctorFrame instance.
    @BeforeEach
    public void setUp() {
        changeDoctorFrame = new ChangeDoctorFrame(); // Creates the frame and initializes components
    }

    // Checks if the patient ID combo box is properly initialized.
    @Test
    public void testPatientIdComboBoxIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChangeDoctorFrame.class.getDeclaredField("patientId"); // Access private field
        field.setAccessible(true);
        JComboBox<String> patientComboBox = (JComboBox<String>) field.get(changeDoctorFrame);
        assertNotNull(patientComboBox, "Patient ID ComboBox should be initialized.");
    }

    // Checks if the doctor ID combo box is properly initialized.
    @Test
    public void testDoctorIdComboBoxIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChangeDoctorFrame.class.getDeclaredField("DoctorId"); // Access private field
        field.setAccessible(true);
        JComboBox<String> doctorComboBox = (JComboBox<String>) field.get(changeDoctorFrame);
        assertNotNull(doctorComboBox, "Doctor ID ComboBox should be initialized.");
    }

    // Checks if the "Change Doctor" button is properly initialized.
    @Test
    public void testChangeDoctorButtonIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChangeDoctorFrame.class.getDeclaredField("changeButton"); // Access private field
        field.setAccessible(true);
        JButton changeDoctorButton = (JButton) field.get(changeDoctorFrame);
        assertNotNull(changeDoctorButton, "Change Doctor button should be initialized.");
    }

    // Checks if the doctor details label is properly initialized.
    @Test
    public void testDoctorDetailsLabelIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChangeDoctorFrame.class.getDeclaredField("doctorIdLabel"); // Access private field
        field.setAccessible(true);
        JLabel doctorLabel = (JLabel) field.get(changeDoctorFrame);
        assertNotNull(doctorLabel, "Doctor details label should be initialized.");
    }
}
