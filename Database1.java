import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Database1 {
    private static final String URL = "jdbc:mysql://localhost:3306/gp_information_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    public static String getAdminName(String username) {

        String fullName = "User";

        try {
            Connection connection = getConnection();
            String query = "SELECT FirstName, Surname FROM admininformation WHERE Admin_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String surname = resultSet.getString("Surname");
                fullName = firstName + " " + surname;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullName;
    }

    public static ArrayList<String> getPatientID() {
        ArrayList<String> patientIDs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Patient_ID FROM patientinformation")) {

                while (resultSet.next()) {
                    patientIDs.add(resultSet.getString("Patient_ID"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return patientIDs;
        }
    /**
     * Getting the assigned doctor´s details with a patient´s id
     *
     * This method joins  the Doctor_Information and PatientInformation tables
     * using the Assigned_Doctor foreign key
     *
     * @param patientID the patient´s id whichs doctor we need to get
     * @return a string containing the assigned doctor's ID and full name and we otherwise we print "N/A" if not found
     */
    //changing this to get assigned doctor so that it works with our new database as we removed the assign_patient variable
    public static String getAssignedDoctor(String patientID) {
        String doctorInfo = "N/A";
        //writing an sql query to join the PatientInformation and Doctorinformation tables so that we can get the assigned
        String query = "SELECT d.Doctor_ID, d.FirstName, d.Surname " +
                "FROM Doctor_Information d " +
                "JOIN PatientInformation p ON d.Doctor_ID = p.Assigned_Doctor " +
                "WHERE p.Patient_ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, patientID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                doctorInfo = rs.getString("Doctor_ID") + " - " +
                        rs.getString("FirstName") + " " +
                        rs.getString("Surname");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorInfo;
    }



    /**
     * Getting the list of all the doctor ids and their first names from the database
     *
     * This method connects to the database, where it executes a sql query that I wrote below to get the doctors id and first name
     *
     * @return an arraylist of all the strings containing doctor_Id and their first names
     *
     */
    //making a method to get the doctor Ids and name to display it on the dropdown in main
    public static ArrayList<String> getDoctorID() {
        //creating an arraylist to store the doctors names and ids
        ArrayList<String> doctorsIds = new ArrayList<>();

        //connecting to the database
        try (Connection connection2 = DriverManager.getConnection(URL, USER, PASSWORD);
             //making a query and running it to get the doctors id and name from the databse
             Statement statement = connection2.createStatement();
             ResultSet doctorsData = statement.executeQuery("SELECT Doctor_ID, FirstName FROM Doctor_Information")){

            //iterating over the doctors name and id like the data and getting the id and name
            while (doctorsData.next()) {
                String doctorsId = doctorsData.getString("Doctor_ID");
                String doctorsName = doctorsData.getString("FirstName");

                //printing the doctors id and name and  for the dropdown
                doctorsIds.add(doctorsId + " - " + doctorsName);
            }
        } catch (SQLException e) {
            //printing an sql exception
            e.printStackTrace();
        }
        return doctorsIds;
    }

    public static String fetchBookings(String selectedPatient, String selectedDoctor, Date selectedDate) {
        StringBuilder query = new StringBuilder("SELECT * FROM bookinginformation WHERE 1=1");

        if (selectedPatient != null && !selectedPatient.equals("Select Patient")) {
            query.append(" AND Patient_ID='").append(selectedPatient).append("'");
        }
        if (selectedDoctor != null && !selectedDoctor.equals("Select Doctor")) {
            query.append(" AND Assigned_Doctor='").append(selectedDoctor).append("'");
        }
        if (selectedDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            int selectedMonth = cal.get(Calendar.MONTH) + 1;
            int selectedYear = cal.get(Calendar.YEAR);
            query.append(" AND MONTH(Date_of_Booking)=").append(selectedMonth);
            query.append(" AND YEAR(Date_of_Booking)=").append(selectedYear);
        }

        StringBuilder bookingDetails = new StringBuilder("Bookings:\n\n");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query.toString())) {

            if (!resultSet.isBeforeFirst()) {  // No results
                return "No bookings found.";
            }

            while (resultSet.next()) {
                bookingDetails.append("Patient ID: ").append(resultSet.getString("Patient_ID")).append("\n");
                bookingDetails.append("Doctor ID: ").append(resultSet.getString("Assigned_Doctor")).append("\n");
                bookingDetails.append("Date: ").append(resultSet.getString("Date_of_Booking")).append("\n");
                Time timeOfBooking = resultSet.getTime("Time_of_Booking");
                String formattedTime = timeFormat.format(timeOfBooking);
                bookingDetails.append("Time: ").append(formattedTime).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching bookings.";
        }
        return bookingDetails.toString();
    }




    public static boolean updateDoctorAssignment(String patientID, String doctorID) {
        String query = "UPDATE PatientInformation SET Assigned_Doctor = ? WHERE Patient_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorID);
            stmt.setString(2, patientID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getDoctorNames() {
        ArrayList<String> doctorNames = new ArrayList<>();

        String query = "SELECT Doctor_ID, FirstName, Surname FROM doctor_information";


        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String doctorInfo = rs.getString("Doctor_ID") + " - " + rs.getString("FirstName") + " " + rs.getString("Surname");
                doctorNames.add(doctorInfo);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return doctorNames;
    }

    public static boolean insertBooking(String patientID, String doctorID, String dateFormatted, java.sql.Time sqlTime) {
        String query = "INSERT INTO bookinginformation (Patient_ID, Assigned_Doctor, Date_of_Booking, Time_of_Booking) VALUES (?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, patientID);
            statement.setString(2,doctorID);
            statement.setString(3, dateFormatted);
            statement.setTime(4,sqlTime);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkDoctorAvailability(String doctorID, Date date, Time time) {
        String query = "SELECT * FROM BookingInformation WHERE Doctor_ID = ? AND Date_of_Booking = ? AND Time_of_Booking = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
/*
            stmt.setString(1, Assigned_Doctor);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setTime(3, time);
*/
            ResultSet rs = stmt.executeQuery();

            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean updateBooking(String patientID, String Assigned_Doctor, Date date, Time time) {
        String query = "UPDATE BookingInformation SET Doctor_ID = ?, Date_of_Booking = ?, Time_of_Booking = ? WHERE Patient_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

//            stmt.setString(1, Assigned_Doctor);
//            stmt.setDate(2, new java.sql.Date(date.getTime()));
//            stmt.setTime(3, time);
            stmt.setString(4, patientID);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean removeBooking(String patientID, String Assigned_Doctor, java.sql.Date date, java.sql.Time time) {
        String query = "DELETE FROM BookingInformation WHERE Patient_ID = ? AND Assigned_Doctor = ? AND Date_of_Booking = ? AND Time_of_Booking = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientID);
            stmt.setString(2, Assigned_Doctor);
            stmt.setDate(3, date);
            stmt.setTime(4, time);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}








