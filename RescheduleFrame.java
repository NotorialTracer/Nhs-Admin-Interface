import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import javax.swing.SpinnerDateModel;

public class RescheduleFrame extends JFrame {
    private JComboBox<String> patientIdCombo;
    private JButton rescheduleButton, removeBookingButton;
    private JPanel reschedulePanel;
    private JLabel titleLbl, selectPatientLbl, doctorDetailsLbl, selectDateLbl, selectTimeLbl;
    private JLabel currentBookingLbl, newBookingLbl;
    private JDateChooser dateChooser, newDateChooser;
    private JSpinner timeSpinner, newTimeSpinner;

    public RescheduleFrame() {
        setTitle("Reschedule Booking"); // Set title
        setSize(500, 550);  // Adjusted the size to fit new layout
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the window when clicked
        getContentPane().setBackground(new Color(214, 235, 248));

        //

        reschedulePanel = new JPanel();
        reschedulePanel.setLayout(null);
        reschedulePanel.setBounds(0, 0, 700, 75);
        reschedulePanel.setBackground(new Color(0, 0, 102));
        add(reschedulePanel);

        //Adds a title to the top panel with font and colour
        titleLbl = new JLabel("Reschedule Booking");
        titleLbl.setFont(new Font("Arial", Font.BOLD, 18));
        titleLbl.setBounds(8, 50, 260, 20);
        titleLbl.setForeground(Color.white);
        reschedulePanel.add(titleLbl);

        //Adds a label for current booking
        currentBookingLbl = new JLabel("Current Booking");
        currentBookingLbl.setBounds(5, 85, 150, 30);
        currentBookingLbl.setFont(new Font("Arial", Font.BOLD, 14));
        add(currentBookingLbl);

        // Label and combobox for selecting patient ID
        selectPatientLbl = new JLabel("Select a Patient ID:");
        selectPatientLbl.setBounds(5, 115, 150, 30);
        add(selectPatientLbl);

        patientIdCombo = new JComboBox<>();
        patientIdCombo.setBounds(120, 120, 200, 20);
        add(patientIdCombo);
        loadPatientIDs(); //loads patients IDs from the database
        patientIdCombo.addActionListener(e -> updateDoctorInfo());

        //label for assigned doctorinfo
        doctorDetailsLbl = new JLabel("Assigned Doctor: ");
        doctorDetailsLbl.setBounds(5, 150, 400, 30);
        add(doctorDetailsLbl);

        // Select a Date and Time for Current Booking
        selectDateLbl = new JLabel("Select a Date: ");
        selectDateLbl.setBounds(5, 180, 150, 30);
        add(selectDateLbl);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(120, 185, 150, 20);
        dateChooser.setDateFormatString("dd-MM-yyyy");
        add(dateChooser);

        selectTimeLbl = new JLabel("Select a Time: ");
        selectTimeLbl.setBounds(5, 220, 150, 30);
        add(selectTimeLbl);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        Date startingTime = calendar.getTime();

        SpinnerDateModel spinnerModel = new SpinnerDateModel(startingTime, null, null, Calendar.MINUTE);
        timeSpinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setBounds(120, 225, 150, 20);
        add(timeSpinner);

        // label for new booking section
        newBookingLbl = new JLabel("New Booking");
        newBookingLbl.setBounds(5, 270, 150, 30);
        newBookingLbl.setFont(new Font("Arial", Font.BOLD, 14));
        add(newBookingLbl);

        // Select sate and time for new booking
        selectDateLbl = new JLabel("Select a Date: ");
        selectDateLbl.setBounds(5, 300, 150, 30);
        add(selectDateLbl);

        newDateChooser = new JDateChooser();
        newDateChooser.setBounds(120, 305, 150, 20);
        newDateChooser.setDateFormatString("dd-MM-yyyy");
        add(newDateChooser);

        // Label for selecting time for new booking
        selectTimeLbl = new JLabel("Select a Time: ");
        selectTimeLbl.setBounds(5, 340, 150, 30);
        add(selectTimeLbl);

        newTimeSpinner = new JSpinner(new SpinnerDateModel(startingTime, null, null, Calendar.MINUTE));
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(newTimeSpinner, "HH:mm");
        newTimeSpinner.setEditor(timeEditor2);
        newTimeSpinner.setBounds(120, 345, 150, 20);
        add(newTimeSpinner);

        // reschedule and remove booking buttons
        rescheduleButton = new JButton("Reschedule Booking");
        rescheduleButton.setBounds(5, 380, 150, 30);
        add(rescheduleButton);

        // remove booking button
        removeBookingButton = new JButton("Remove Booking");
        removeBookingButton.setBounds(160, 380, 150, 30);
        add(removeBookingButton);

        //action listeners for buttons
        rescheduleButton.addActionListener(e -> rescheduleBooking());
        removeBookingButton.addActionListener(e -> removeBooking());

        setVisible(true);
    }

    // loads patients IDs into the combo box
    private void loadPatientIDs() {
        ArrayList<String> patientIDs = Database1.getPatientID();
        for (String patientID : patientIDs) {
            patientIdCombo.addItem(patientID);
        }
    }

    //updates doctor information when patient ID is selected
    private void updateDoctorInfo() {
        String selectedPatientID = (String) patientIdCombo.getSelectedItem();
        if (selectedPatientID != null) {
            String assignedDoctor = Database1.getAssignedDoctor(selectedPatientID);
            doctorDetailsLbl.setText("Assigned Doctor: " + assignedDoctor);
        }
    }

    //Reschedule booking by updating the database
    private void rescheduleBooking() {
        String selectedPatientID = (String) patientIdCombo.getSelectedItem();
        String assignedDoctor = doctorDetailsLbl.getText().replace("Assigned Doctor: ", "");  // Extract the doctor name from the label
        Date selectedDate = dateChooser.getDate();
        Date selectedTime = (Date) timeSpinner.getValue();

        if (selectedPatientID == null || assignedDoctor == null || selectedDate == null || selectedTime == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }


        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
        java.sql.Time sqlTime = new java.sql.Time(selectedTime.getTime());

        //call the method to update the booking
        boolean success = Database1.updateBooking(selectedPatientID, assignedDoctor, sqlDate, sqlTime);
        if (success) {
            JOptionPane.showMessageDialog(this, "Booking successfully rescheduled.");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to reschedule booking.");
        }
    }


    private void removeBooking() {
        String selectedPatientID = (String) patientIdCombo.getSelectedItem();
        String assignedDoctor = doctorDetailsLbl.getText().replace("Assigned Doctor: ", "");  //extract the doctor name from the label
        Date selectedDate = dateChooser.getDate();
        Date selectedTime = (Date) timeSpinner.getValue();

        if (selectedPatientID == null || assignedDoctor == null || selectedDate == null || selectedTime == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }


        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
        java.sql.Time sqlTime = new java.sql.Time(selectedTime.getTime());

        //call the method to remove the booking
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this booking?", "Remove Booking", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = Database1.removeBooking(selectedPatientID, assignedDoctor, sqlDate, sqlTime);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking successfully removed.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove booking.");
            }
        }
    }

}


