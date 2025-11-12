import javax.swing.*;
import java.awt.*;
import java.sql.*;

// JFrame to display all doctors information from the database
public class DoctorFramee extends JFrame {
    JTextArea textArea;
    // constructor for the frame
    public DoctorFramee() {
        setTitle("Doctor Information"); //sets the title
        setSize(500, 500);//sets the size of the window
        setLayout(new BorderLayout());

        //area to display the doctors information
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        // fetches data from the database and displays it
        fetchAndDisplayDoctors();
        setVisible(true); // makes the frame visible
    }

    private void fetchAndDisplayDoctors() {
        String query = "SELECT * FROM Doctor_Information";

        try {
            ResultSet rs = Database1.executeQuery(query); // executes the SQL query
            StringBuilder doctorDetails = new StringBuilder("Doctor Information:\n\n"); //StringBuilder to accumulate all the doctors details

            while (rs.next()) {
                doctorDetails.append("First Name: ").append(rs.getString("FirstName")).append("\n");
                doctorDetails.append("Surname: ").append(rs.getString("Surname")).append("\n");
                doctorDetails.append("Specialisation: ").append(rs.getString("Specialisation")).append("\n");
                doctorDetails.append("Address: ").append(rs.getString("Address")).append("\n");
                doctorDetails.append("Phone Number: ").append(rs.getString("Phone_Number")).append("\n\n");
            }

            rs.close();
            Database1.closeConnection(null); //closes the database connection
            textArea.setText(doctorDetails.toString());
        } catch (SQLException ex) {
            ex.printStackTrace(); //Prints error details in case of exception
            textArea.setText("Error fetching doctor details.");
        }
    }
}


