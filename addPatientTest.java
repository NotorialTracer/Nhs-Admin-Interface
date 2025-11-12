import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class addPatientTest {

    private addPatient addPatientFrame;

    // Runs before each test. Initializes the addPatient instance.
    @BeforeEach
    public void setUp() {
        addPatientFrame = new addPatient(); // Creates the frame and initializes components
    }

    // Checks if the first name text field is properly initialized.
    @Test
    public void testFirstNameFieldIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = addPatient.class.getDeclaredField("tfname"); // Access private field
        field.setAccessible(true);
        JTextField firstNameField = (JTextField) field.get(addPatientFrame);
        assertNotNull(firstNameField, "First name text field should be initialized.");
    }

    // Checks if the surname text field is properly initialized.
    @Test
    public void testSurnameFieldIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = addPatient.class.getDeclaredField("tnsname"); // Access private field
        field.setAccessible(true);
        JTextField surnameField = (JTextField) field.get(addPatientFrame);
        assertNotNull(surnameField, "Surname text field should be initialized.");
    }

    // Checks if the patient ID text field is properly initialized.
    @Test
    public void testPatientIdFieldIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = addPatient.class.getDeclaredField("tpatID"); // Access private field
        field.setAccessible(true);
        JTextField patientIdField = (JTextField) field.get(addPatientFrame);
        assertNotNull(patientIdField, "Patient ID text field should be initialized.");
    }

    // Checks if the doctor selection combo box is properly initialized.
    @Test
    public void testDoctorComboBoxIsInitialized() throws NoSuchFieldException, IllegalAccessException {
        Field field = addPatient.class.getDeclaredField("c1"); // Access private field
        field.setAccessible(true);
        JComboBox<String> doctorComboBox = (JComboBox<String>) field.get(addPatientFrame);
        assertNotNull(doctorComboBox, "Doctor selection ComboBox should be initialized.");
    }
}
