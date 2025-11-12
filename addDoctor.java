import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class addDoctor implements ActionListener {
    private JLabel patient, dob, phone, firstname, surname, specialisation, address, patientID;
    private JTextField tphone, tfname, tnsname, tspec, thome, tpatID;
    private JButton addDoctor;
    private JComboBox<String> c1;
    private JTextArea resultArea;

    private JDateChooser tdob;


    private static final String URL = "jdbc:mysql://localhost/gp_information_system";
    private static final String USER = "root";  // Change to your MySQL username
    private static final String PASSWORD = "";  // Change to your MySQL password


    public addDoctor() {
        // Create JFrame
        JFrame frame = new JFrame("Add New Doctor");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0, 0, 120));
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = new JLabel("Add Doctor", SwingConstants.CENTER);
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

        //Specialisation
        specialisation = new JLabel("Specialisation:");
        specialisation.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(specialisation, gbc);

        tspec = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tspec, gbc);


        // Address
        address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(address, gbc);

        thome = new JTextField(20);
        gbc.gridx = 1;
        panel.add(thome, gbc);


        // Phone Number
        phone = new JLabel("Phone Number:");
        phone.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(phone, gbc);

        tphone = new JTextField(20);
        gbc.gridx = 1;
        panel.add(tphone, gbc);


// Patient Selection
        patient = new JLabel("Select Patient:");
        patient.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add(patient, gbc);

        //Patient Drop down
        c1 = new JComboBox<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement("SELECT FirstName, Surname, Patient_ID FROM PatientInformation");
             java.sql.ResultSet rs = pst.executeQuery()) {


            // Loop through each row in the ResultSet and retrieve doctor names
            // Format them as 'Full name and patientID' and add to the JComboBox
            while (rs.next()) {
                String fullName = rs.getString("FirstName") + " " + rs.getString("Surname");
                String patientID = rs.getString("Patient_ID");
                String display = fullName + " (" + patientID + ")";
                c1.addItem(display);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading patients: " + ex.getMessage());
        }

        gbc.gridx = 1;
        panel.add(c1, gbc);


        // Add Patient Button
        addDoctor = new JButton("Add Doctor");
        addDoctor.setFont(new Font("Arial", Font.BOLD, 16));
        addDoctor.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addDoctor, gbc);

        // Create Results Panel (New Section)
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Selected Doctor Details"));

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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDoctor) {
            java.sql.Date dobSql = null;
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pst = con.prepareStatement(
                         "INSERT INTO Doctor_Information (Doctor_ID,FirstName, Surname, DOB, Specialisation, Address, Assigned_Patient, Phone_Number) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                java.util.Date dobUtil = tdob.getDate();
                if (dobUtil == null) {
                    JOptionPane.showMessageDialog(null, "Please select a valid Date of Birth.");
                    return;
                }
                dobSql = new java.sql.Date(dobUtil.getTime());
                String doctorID = generateDoctorID();

                pst.setString(1, doctorID); // Doctor_ID
                pst.setString(2, tfname.getText().trim()); // FirstName
                pst.setString(3, tnsname.getText().trim()); // Surname
                pst.setDate(4, dobSql); // DOB
                pst.setString(5, tspec.getText().trim()); // Specialisation
                pst.setString(6, thome.getText().trim()); // Address
                pst.setString(7, tphone.getText().trim()); // Phone



                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, " Patient added to the database!");


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " Database error: " + ex.getMessage());

                String phoneNumber = tphone.getText().trim();


                if (!phoneNumber.matches("\\d{10}")) {  // Ensures exactly 10 digits
                    System.out.println("️ Error: Phone number must be exactly 10 digits!");
                    resultArea.setText("️ Error: Phone number must be exactly 10 digits!");
                    return;
                }


                // Validate Input
                if (tfname.getText().trim().isEmpty() || tnsname.getText().trim().isEmpty() || tphone.getText().trim().isEmpty()) {
                    System.out.println("️ Error: Please fill in all fields!");
                    resultArea.setText(" Error: Please fill in all fields!");
                    return;
                }


                // Store Selected Data
                String doctorData = " Patient Registered:\n"
                        + "────────────────────────────────\n"
                        + "First Name: " + tfname.getText() + "\n"
                        + "Surname:" + tnsname.getText() + "\n"
                        + "D.O.B: " + dobSql.toString() + "\n"
                        + "Specialisation:" + tspec.getText() + "\n"
                        + "Address:" + thome.getText() + "\n"
                        + "Assigned Patient: " + extractPatientID((String) c1.getSelectedItem()) + "\n"
                        + "Phone Number: " + tphone.getText() + "\n"
                        + "────────────────────────────────\n";

                // Print to Terminal
                System.out.println(doctorData);

                // Display in GUI in the new section
                resultArea.setText(doctorData);


            }

        }
    }

    private String extractPatientID(String displayText) {
        if (displayText == null || !displayText.contains("(")) return "";
        return displayText.substring(displayText.indexOf("(") + 1, displayText.indexOf(")"));

    }
    private String generateDoctorID() {
        String newID = "D0011";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pst = con.prepareStatement("SELECT Doctor_ID FROM Doctor_Information ORDER BY Doctor_ID DESC LIMIT 1");
             java.sql.ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                String lastID = rs.getString("Doctor_ID");
                int num = Integer.parseInt(lastID.substring(1));
                num++;
                newID = String.format("D%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newID;
    }





    public static void main(String[] args) {
        new addDoctor();
    }

}


