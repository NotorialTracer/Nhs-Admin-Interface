import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class addPatient implements ActionListener {
    private JLabel doctor, dob, gender, phone, firstname, surname, resultLabel, job, address, emergency, surgeries, conditions, allergies, patientID;
    private JTextField  tphone, tfname, tnsname, tjob, thome, tother, tsurg, tcond, talg, tpatID;
    private JButton addPatient;
    private JComboBox<String> c1;
    private JRadioButton male, female;
    private JTextArea resultArea;

    private JDateChooser tdob;


    private static final String URL = "jdbc:mysql://localhost/gp_information_system";
    private static final String USER = "root";  // Change to your MySQL username
    private static final String PASSWORD = "";  // Change to your MySQL password


    public addPatient() {
        // Create JFrame
        JFrame frame = new JFrame("Add New Patients");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0, 0, 120));
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = new JLabel("Add Patient", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        panel1.add(title);
        frame.add(panel1, BorderLayout.NORTH);

        // Main Content Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(214, 235, 248));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Name Field
        firstname = new JLabel("First Name:");
        firstname.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(firstname, gbc);

        tfname = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tfname, gbc);

        surname = new JLabel("Surname:");
        surname.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(surname, gbc);

        tnsname = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tnsname, gbc);


        // Date of Birth
        dob = new JLabel("D.O.B:");
        dob.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(dob, gbc);


        tdob = new JDateChooser();
        tdob.setDateFormatString("yyyy-MM-dd");
        tdob.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(tdob, gbc);

        //Occupation
        job = new JLabel("Occupation:");
        job.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(job, gbc);

        tjob = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tjob, gbc);


        // Address
        address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(address, gbc);

        thome = new JTextField(20);
        gbc.gridx = 1;
        panel.add(thome, gbc);

        // Emergency Contact
        emergency = new JLabel("Emergency Contact");
        emergency.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(emergency, gbc);

        tother = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tother, gbc);


        //Allergies
        allergies = new JLabel("Allergies");
        allergies.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(allergies, gbc);
        talg = new JTextField(20);
        gbc.gridx = 1;
        panel.add(talg, gbc);

        // Past Surgeries
        surgeries = new JLabel("Past Surgeries");
        surgeries.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(surgeries, gbc);

        tsurg = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tsurg, gbc);

        //Conditions
        conditions = new JLabel("Conditions");
        conditions.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(conditions, gbc);

        tcond = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tcond, gbc);


        // Phone Number
        phone = new JLabel("Phone Number:");
        phone.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(phone, gbc);

        tphone = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tphone, gbc);

        // Gender Selection
        gender = new JLabel("Gender:");
        gender.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(gender, gbc);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        genderPanel.add(male);
        genderPanel.add(female);

        gbc.gridx = 1;
        panel.add(genderPanel, gbc);

        // Doctor Selection
        doctor = new JLabel("Select Doctor:");
        doctor.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add(doctor, gbc);


        // Doctor dropdown (ComboBox)
        c1 = new JComboBox<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement("SELECT FirstName, Surname, Doctor_ID FROM Doctor_Information")) {

            ResultSet rs = pst.executeQuery();

            // Loop through each row in the ResultSet and retrieve doctor names
            // Format them as 'Dr FirstName Surname' and add to the JComboBox
            while (rs.next()) {
                String fullName = "Dr" + " " + rs.getString("FirstName") + " " + rs.getString("Surname");
                String display = fullName;
                c1.addItem(display);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading doctors: " + ex.getMessage());
        }



        gbc.gridx = 1;
        panel.add(c1, gbc);

        // Add Patient Button
        addPatient = new JButton("Add Patient");
        addPatient.setFont(new Font("Arial", Font.BOLD, 16));
        addPatient.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addPatient, gbc);

        // Create Results Panel (New Section)
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Selected Patient Details"));

        resultArea = new JTextArea(8, 50);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        resultArea.setEditable(false);
        resultArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Add both form panel and results panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Handle Add Patient button click
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatient) {
            // Validate BEFORE DB insert
            String phoneNumber = tphone.getText().trim();
            String emergencyContact = tother.getText().trim();

            if (!phoneNumber.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Error: Phone number must be exactly 10 digits!");
                return;
            }
            // Validate emergency contact number is exactly 10 digits
            if (!emergencyContact.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Error: Emergency contact must be exactly 10 digits!");
                return;
            }

            // Check required fields are not empty (First Name and Surname)
            if (tfname.getText().trim().isEmpty() || tnsname.getText().trim().isEmpty() || tphone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: Please fill in all required fields!");
                return;
            }

            // Check if gender has been selected
            if (!male.isSelected() && !female.isSelected()) {
                JOptionPane.showMessageDialog(null, "Error: Please select a gender!");
                return;
            }
            // Try inserting the patient into the database
            java.sql.Date dobSql = null;
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pst = con.prepareStatement(
                         "INSERT INTO PatientInformation (FirstName,Surname, DOB, Occupation, Address, Emergency_Contact, Assigned_Doctor, Patient_ID, Past_Surgeries, Allergies, Conditions, Phone_Number) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                java.util.Date dobUtil = tdob.getDate();
                if (dobUtil == null) {
                    JOptionPane.showMessageDialog(null, "Please select a valid Date of Birth.");
                    return;
                }
                dobSql = new java.sql.Date(dobUtil.getTime());

                String generatedID = generatePatientID(tfname.getText().trim(), tnsname.getText().trim());

                // Set query parameters
                pst.setString(1, tfname.getText().trim());
                pst.setString(2, tnsname.getText().trim());
                pst.setDate(3, dobSql);
                pst.setString(4, tjob.getText().trim());
                pst.setString(5, thome.getText().trim());
                pst.setString(6, tother.getText().trim());
                //adding code to use the new database
                String selectedDoctor = (String) c1.getSelectedItem();
                String doctorID = getDoctorIDFromName(selectedDoctor);
                pst.setString(7, doctorID);

                pst.setString(8, generatedID);
                pst.setString(9, talg.getText().trim());
                pst.setString(10, tsurg.getText().trim());
                pst.setString(11, tcond.getText().trim());
                pst.setString(12, tphone.getText().trim());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Patient added to the database!");

                // Show patient details in the result area
                String genderText = male.isSelected() ? "Male" : "Female";
                String patientData = " Patient Registered:\n"
                        + "────────────────────────────────\n"
                        + "First Name: " + tfname.getText() + "\n"
                        + "Surname: " + tnsname.getText() + "\n"
                        + "D.O.B: " + dobSql.toString() + "\n"
                        + "Occupation: " + tjob.getText() + "\n"
                        + "Address: " + thome.getText() + "\n"
                        + "Emergency Contact: " + tother.getText() + "\n"
                        + "Patient_ID: " + generatedID + "\n"
                        + "Allergies: " + talg.getText() + "\n"
                        + "Past Surgeries: " + tsurg.getText() + "\n"
                        + "Conditions: " + tcond.getText() + "\n"
                        + "Phone Number: " + tphone.getText() + "\n"
                        + "Gender: " + genderText + "\n"
                        + "Doctor Assigned: " + c1.getSelectedItem() + "\n"
                        + "────────────────────────────────\n";

                System.out.println(patientData);
                resultArea.setText(patientData);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                ex.printStackTrace();
            }


        }
    }

    /**
     * Making a helper method to get the Doctor_ID with the doctors name
     **
     * @param fullName the full name of the doctor in the format "Dr FirstName Surname"
     * @return the Doctor_ID as a string if it is found otherwise we return null
     */
    private String getDoctorIDFromName(String fullName) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             //writing an sql query to get the doctors id that matches the doctors id
             PreparedStatement doctorQuery = con.prepareStatement("SELECT Doctor_ID FROM Doctor_Information WHERE CONCAT('Dr ', FirstName, ' ', Surname) = ?")) {

            doctorQuery.setString(1, fullName);
            ResultSet rs = doctorQuery.executeQuery();

            if (rs.next()) {
                return rs.getString("Doctor_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String extractDoctorName(String displayText) {
        if (displayText == null || !displayText.contains("(")) return displayText;
        return displayText.substring(0, displayText.indexOf(" (")).trim();
    }

    // Method to generate a random patient ID with initials, 4-digit number and 2 random letters
    private String generatePatientID(String firstName, String surname) {
        String initials = firstName.substring(0, 1).toUpperCase() + surname.substring(0, 1).toUpperCase();
        int randomNumber = (int) (Math.random() * 9000) + 1000; // 1000 to 9999
        char letter1 = (char) ('A' + Math.random() * 26);
        char letter2 = (char) ('A' + Math.random() * 26);
        return initials + "-" + randomNumber + letter1 + letter2;
    }


    public static void main (String[]args){
        new addPatient();
    }

}



