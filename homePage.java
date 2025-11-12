import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JDateChooser;


public class homePage extends JFrame implements ActionListener {

    static JMenuBar menuBar;
    static JMenu menuTab, patientsTab, doctorsTab, bookingsTab;
    static JMenuItem patientsTabItem1, patientsTabItem2, bookingsTabItem1, patientsTabItem3, doctorsTabItem1, doctorsTabItem2, bookingsTabItem2, bookingsTabItem3;
    static JFrame frame;
    static JLabel welcome, messages, messages2;
    static JPanel panel, panel2, panel3, panel4;
    static JButton logoutBtn;
    static String adminUser = "Admin";

    public static void main(String[] args) {
        homePage m = new homePage();

        String adminName = Database1.getAdminName(adminUser);


        frame = new JFrame("General Practitioner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(214,235,248));


        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 700, 75);
        Color panelColourb = new Color(0,0,102);
        panel.setBackground(panelColourb);

        welcome = new JLabel("Welcome, " + adminName);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcome.setBounds(8, 50, 260, 20);
        welcome.setForeground(Color.white);
        panel.add(welcome);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(165, 70, 145, 50);
        panel2.setBackground(new Color(214,235,248));

        messages = new JLabel("Your messages");
        messages.setFont(new Font("Arial", Font.PLAIN, 16));
        messages.setBounds(10, 10, 130, 30);
        panel2.add(messages);

        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(0, 120, 600, 50);
        panel3.setBackground(new Color(214,235,248));

        messages2 = new JLabel("No new messages.");
        messages2.setFont(new Font("Arial", Font.PLAIN, 14));
        messages2.setBounds(10, 5, 180, 30);
        panel3.add(messages2);

        panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBounds(350, 10, 100, 50);

        logoutBtn = new JButton("Log out");
        logoutBtn.setBounds(0, 10, 100, 30);
        logoutBtn.addActionListener(new homePage());
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        panel4.add(logoutBtn);

        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);

        menuBar = new JMenuBar();
        menuTab = new JMenu("Menu");
        patientsTab = new JMenu("Patients");
        doctorsTab = new JMenu("Doctors"); // adds doctors to the menu
        bookingsTab = new JMenu("Bookings");

        patientsTabItem1 = new JMenuItem("View Patients");
        patientsTabItem2 = new JMenuItem("Add Patients");
        patientsTabItem3 = new JMenuItem("Change Doctor");
        doctorsTabItem1 = new JMenuItem("View Doctors"); // adds view doctors to the submenu
        doctorsTabItem2 = new JMenuItem("Add Doctor"); // adds submenu add doctor to doctor
        bookingsTabItem1 = new JMenuItem("Arrange Booking");
        bookingsTabItem2 = new JMenuItem("Reschedule Booking");
        bookingsTabItem3 = new JMenuItem("View Bookings");

        patientsTabItem1.addActionListener(m);
        patientsTabItem2.addActionListener(m);
        patientsTabItem3.addActionListener(m);
        doctorsTabItem1.addActionListener(m);
        doctorsTabItem2.addActionListener(m);
        bookingsTabItem1.addActionListener(m);
        bookingsTabItem2.addActionListener(m);
        bookingsTabItem3.addActionListener(m);

        menuBar.add(menuTab);
        menuTab.add(patientsTab);
        menuTab.add(doctorsTab);
        menuTab.add(bookingsTab);
        patientsTab.add(patientsTabItem1);
        patientsTab.add(patientsTabItem2);
        patientsTab.add(patientsTabItem3);
        doctorsTab.add(doctorsTabItem1);
        doctorsTab.add(doctorsTabItem2);
        bookingsTab.add(bookingsTabItem1);
        bookingsTab.add(bookingsTabItem2);
        bookingsTab.add(bookingsTabItem3);


        frame.setJMenuBar(menuBar);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("View Patients")) {
            new PatientFrame(); // opens a new patient frame
        } else if (s.equals("Arrange Booking")) {
            new BookingFrame(); // opens a new arrange booking frame
        } else if (s.equals("View Bookings")) {
            new viewBookingsFrame(); // opens a new view bookings frame
        } else if (s.equals("Change Doctor")) {
            new ChangeDoctorFrame();
        } else if (s.equals("Add Doctor")) {
            new addDoctor();
        } else if (s.equals("Add Patients")) {
            new addPatient(); // opens a new add patient frame
        } else if (s.equals("View Doctors")) {
            new DoctorFramee(); //opens the doctorframee to view doctors
        } else if (s.equals("Reschedule Booking")) {
            new RescheduleFrame(); // opens a new reschedule frame
        } else if (s.equals("Log out")) {
            logout(); // if the yes option is clicked frame is closed and sign page frame is opened
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION); // prompts a confirmation if user wants to logout
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
            Main.main(new String[]{});
        }
    }
    public JMenuItem getViewPatientsButton() {
        return homePage.patientsTabItem1;
    }
}

//creating a class to change the patients assigned doctor
class ChangeDoctorFrame extends JFrame {
    //adding the ui components for everything
    private JComboBox<String> patientId, DoctorId;
    private JButton changeButton;
    private JPanel changingDoctorPanel;
    private JLabel selectPatient, doctorDetails, doctorIdLabel, titleForLabel, selectDoctorToAssign, successMessage;
    private Timer closeFrameTimer; // timer for closing the frame

    //adding a constructor
    public ChangeDoctorFrame() {
        //setting the title and size of the frame and everything
        setTitle("Changing Doctors");
        setSize(500, 500);

        //setting the layout as null so that I can adjust everything manually
        setLayout(null);

        //closing the winsow when clicking the x
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //setting the background color of the frame
        getContentPane().setBackground(new Color(214, 235, 248));

        //styling the panel for changing of the doctor
        changingDoctorPanel = new JPanel();
        changingDoctorPanel.setLayout(null);
        changingDoctorPanel.setBounds(0, 0, 700, 75);
        changingDoctorPanel.setBackground(new Color(0, 0, 102));
        add(changingDoctorPanel);

        //adding the subtitle  to the panel and labeling it
        titleForLabel = new JLabel("Changing Doctors");
        titleForLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleForLabel.setBounds(8, 50, 260, 20);
        titleForLabel.setForeground(Color.white);
        changingDoctorPanel.add(titleForLabel);


        //labelling the selecting part for the patients id
        selectPatient = new JLabel("The patients ID:");
        selectPatient.setBounds(0, 85, 150, 30);
        add(selectPatient);

        //adding a dropdown to select the patients id
        patientId = new JComboBox<>();
        patientId.setBounds(120, 90, 200, 20);
        add(patientId);

        //adding the assigned doctor label
        doctorDetails = new JLabel("Assigned Doctor:");
        doctorDetails.setBounds(0, 125, 150, 30);
        add(doctorDetails);

        //displaying in text the doctors id
        doctorIdLabel = new JLabel();
        doctorIdLabel.setBounds(110, 130, 200, 20);
        add(doctorIdLabel);

        //adding a section to select the new doctor with a dropdown
        selectDoctorToAssign = new JLabel("Choose new Doctor to assign:");
        selectDoctorToAssign.setBounds(0, 175, 400, 30);
        add(selectDoctorToAssign);

        //adding the dropbox for the new doctor to be selected
        DoctorId = new JComboBox<>();
        DoctorId.setBounds(180, 180, 200, 20);
        add(DoctorId);


        // adding a button to confirm the new chance
        changeButton = new JButton("Change Doctor");
        changeButton.setBounds(180, 270, 150, 30);
        add(changeButton);

        successMessage = new JLabel ("");
        successMessage.setBounds(180, 310, 200, 30);
        successMessage.setFont(new Font("Arial", Font.PLAIN, 16));
        add(successMessage);

        //loading the patients and doctors ids
        loadingPatientIDs();
        loadingDoctorsIDs();
        patientId.addActionListener(e -> updatingDoctorInformation());
        DoctorId.addActionListener(e -> updatingPatientInformation());

        changeButton.addActionListener (e -> {
            changeDoctor();
        });

        //displaying everything
        setVisible(true);

    }

    //adding a method to load the patients id from the database into the dropdown
    private void loadingPatientIDs() {
        ArrayList<String> patientIds = Database1.getPatientID();
        for (String patientsId : patientIds) {
            patientId.addItem(patientsId);
        }
    }

    //adding a method to load the doctors id from the database into the dropdown
    private void loadingDoctorsIDs() {
        ArrayList<String> doctorsIds = Database1.getDoctorID();
        for (String doctorIds : doctorsIds) {
            DoctorId.addItem(doctorIds);
        }
    }

    //adding a method to update the label showing the assigned doctor
    private void updatingDoctorInformation() {
        String selectedPatientID = (String) patientId.getSelectedItem();
        if (selectedPatientID != null) {
            String doctorInformation = Database1.getAssignedDoctor(selectedPatientID);
            doctorIdLabel.setText(doctorInformation);
        }
    }

    //adding a method to update the patients label
    private void updatingPatientInformation() {
        String selectedDoctorID = (String) DoctorId.getSelectedItem();
        if (selectedDoctorID != null) {
            System.out.println("New doctor selected: " + selectedDoctorID);

        }
    }

    private void changeDoctor(){

        String selectedPatientID = (String) patientId.getSelectedItem();
        String selectedComboDoctor = (String) DoctorId.getSelectedItem();
        //so when getting the doctor id I wanna split it so that because in our dropdown we have the doctors name and id
        //so I get the doctor id only
        String selectDoctorID = selectedComboDoctor.split(" - ")[0].trim();

        if (selectedPatientID != null && selectDoctorID != null) {
            boolean success = Database1.updateDoctorAssignment(selectedPatientID, selectDoctorID);

            if (success) {
                successMessage.setText("Doctor successfully assigned!");
                updatingDoctorInformation(); // updates the doctors label
                //sets a timer to close the frame after the button has been pressed
                closeFrameTimer = new Timer(5000, e -> dispose());
                closeFrameTimer.setRepeats(false);
                closeFrameTimer.start();
            } else {
                successMessage.setText("Doctor could not be assigned!");
            }
        } else {
            successMessage.setText("Select both a patient and a doctor.");
        }

    }
}


class BookingFrame extends JFrame {
    private JComboBox<String> patientIdCombo;
    private JPanel bookingPanel;
    private JLabel arrangeBooking, selectPatient, doctorIdLabel, doctorDetails, selectDateLbl, selectTimeLbl;

    private JDateChooser date;
    private JSpinner timeFormat;
    private JButton submitBooking;


    public BookingFrame() {
        setTitle("Arrange Booking"); // the title of the frame
        setSize(500, 500); // the size of the frame
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // once exited it closes frame
        getContentPane().setBackground(new Color(214,235,248)); // background colour



        bookingPanel = new JPanel(); // new booking panel
        bookingPanel.setLayout(null);
        bookingPanel.setBounds(0, 0, 700, 75);
        Color colourPanelBg = new Color(0,0,102); // background colour for the panel
        bookingPanel.setBackground(colourPanelBg);
        add(bookingPanel); // adds booking panel to the frame


        arrangeBooking = new JLabel("Arrange Booking"); // new label to show your in the arrange booking tab
        arrangeBooking.setFont(new Font("Arial", Font.BOLD, 18));
        arrangeBooking.setBounds(8, 50, 260, 20);
        arrangeBooking.setForeground(Color.white); // setting text colour white
        bookingPanel.add(arrangeBooking); // adding arrange Booking label to Booking panel


        selectPatient = new JLabel("Select a Patient ID:"); // new label for selecting patient ID
        selectPatient.setBounds(5,85,150,30);
        add(selectPatient); // adds select patient label to the frame

        patientIdCombo = new JComboBox<>(); // new dropdown box for patient IDs
        patientIdCombo.setBounds(120,90,200,20);
        add(patientIdCombo); // adds the patient dropdown box to the frame
        loadPatientIDs(); // loads patient Ids into the dropdown box
        patientIdCombo.addActionListener(e -> updDoctorInfo()); // doctor info is updated when a patient is selected


        doctorDetails = new JLabel("Assigned Doctor:"); // label for assigned doctor
        doctorDetails.setBounds(5, 130, 150, 30);
        add(doctorDetails); // adds doctors Details label to the frame

        doctorIdLabel = new JLabel(); // label for the doctor ID
        doctorIdLabel.setBounds(110, 135, 200, 20);
        add(doctorIdLabel); // adds the doctor Id label to the frame

        selectDateLbl = new JLabel("Select a Date:"); // label for the date
        selectDateLbl.setBounds(5,180,150,30);
        add(selectDateLbl); // adds the select date label to the frame

        date = new JDateChooser(); // date picker for selecting a booking date
        date.setFont(new Font("Arial", Font.PLAIN, 16));
        date.setBounds(90, 185, 150, 20);
        date.setDateFormatString("dd-MM-yyyy"); // setting a date format
        add(date); // adds date picker to the frame

        selectTimeLbl = new JLabel("Select a Time:"); // label for the time
        selectTimeLbl.setBounds(5, 230, 150, 30);
        add(selectTimeLbl); // adds the select time label to frame

        Calendar startOfCal = Calendar.getInstance(); // a calendar instance for the default time
        startOfCal.set(Calendar.HOUR_OF_DAY, 8); // sets the starting hour to 8am/ 08
        startOfCal.set(Calendar.MINUTE,0); // sets the starting minute to 0/ 00
        Date startingTime = startOfCal.getTime(); /// gets the starting time

        SpinnerDateModel startingtimeFormat = new SpinnerDateModel(startingTime,null,null,Calendar.MINUTE); // spinner for time selection
        timeFormat = new JSpinner(startingtimeFormat); // time picker

        JSpinner.DateEditor customTime = new JSpinner.DateEditor(timeFormat, "HH:mm"); // setting time to HH:mm
        timeFormat.setEditor(customTime); // makes the custom time format active

        customTime.getTextField().setEditable(false); // prevents manual text, allows you to only use arrows

        timeFormat.setBounds(90,235,150,20);
        add(timeFormat); // adds time picker to frame

        submitBooking = new JButton("Submit Booking"); // button for submitting the booking
        submitBooking.setBounds(5, 275, 150, 30);
        submitBooking.setFocusPainted(false);
        add(submitBooking); // adds the submit booking button to the frame

        submitBooking.addActionListener(e -> submitBooking()); // action listener for the submit button

        setVisible(true); // makes frame visible
    }

    private void loadPatientIDs() {
        patientIdCombo.addItem("Select Patient");
        ArrayList<String> patientIDs = Database1.getPatientID();
        for (String ids : patientIDs) {
            patientIdCombo.addItem(ids);
        }
    }

    private void updDoctorInfo() {
        String selectedPID = (String) patientIdCombo.getSelectedItem();
        if (selectedPID != null) {
            String doctorInformation = Database1.getAssignedDoctor(selectedPID);
            doctorIdLabel.setText(doctorInformation);
        }
    }

    private void submitBooking() {

        String patientID = (String) patientIdCombo.getSelectedItem();
        if (patientID == "Select Patient") {
            JOptionPane.showMessageDialog(this, "Please select a Patient ID.");
            return;
        }

        String doctorID = doctorIdLabel.getText();
        if (doctorID.equals("N/A")) {
            JOptionPane.showMessageDialog(this, "The Selected Patient does not have an assigned Doctor.");
            return;
        }

        Calendar irlDate = Calendar.getInstance();
        irlDate.set(Calendar.HOUR_OF_DAY,0);
        irlDate.set(Calendar.MINUTE,0);


        Date selectedDate = date.getDate();
        if (selectedDate == null || selectedDate.before(irlDate.getTime())) {
            JOptionPane.showMessageDialog(this, "Please select a valid date");
            return;
        }

        Date selectedTime = (Date) timeFormat.getValue();
        Calendar chosenTime = Calendar.getInstance();
        chosenTime.setTime(selectedTime);

        int selectedHour = chosenTime.get(Calendar.HOUR_OF_DAY);
        int selectedMinute = chosenTime.get(Calendar.MINUTE);

        int startingHour = 8;
        int startingMinute = 0;
        int closingHour = 18;
        int closingMinute = 30;

        if (selectedHour < startingHour || selectedHour == startingHour && selectedMinute < startingMinute
                || selectedHour > closingHour || selectedHour == closingHour && selectedMinute > closingMinute) {
            JOptionPane.showMessageDialog(this, "The GP is only open between the times 08:00 and 18:30. Please enter a valid time.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = dateFormat.format(selectedDate);

        java.sql.Time sqlTime = new java.sql.Time(selectedTime.getTime());

        if (Database1.insertBooking(patientID, doctorID, dateFormatted, sqlTime)) {
            JOptionPane.showMessageDialog(this, "Booking successfully created. \nBoth the patient and doctor have been notified about their appointment.");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error saving booking.");
        }
    }
}

class viewBookingsFrame extends JFrame {
    private JComboBox<String> patientIdCombo, doctorIdCombo;
    private JDateChooser dateChooser;
    private JTextArea bookingsTextArea;
    private JButton fetchBookingsBtn;

    public viewBookingsFrame() {

        setTitle("View Bookings");
        setSize(600, 500);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(214, 235, 248));

        JLabel selectPatientLbl = new JLabel("Select Patient ID:");
        selectPatientLbl.setBounds(10, 20, 150, 30);
        add(selectPatientLbl);

        patientIdCombo = new JComboBox<>();
        patientIdCombo.setBounds(120, 25, 150, 25);
        add(patientIdCombo);
        loadPatientIDs();

        JLabel selectDoctorLbl = new JLabel("Select Doctor ID:");
        selectDoctorLbl.setBounds(10, 60, 150, 30);
        add(selectDoctorLbl);

        doctorIdCombo = new JComboBox<>();
        doctorIdCombo.setBounds(120, 65, 150, 25);
        add(doctorIdCombo);
        loadDoctorIDs();

        JLabel selectDateLbl = new JLabel("Select Month");
        selectDateLbl.setBounds(10, 100, 150, 30);
        add(selectDateLbl);

        JLabel selectDateLbl2 = new JLabel("and Year:");
        selectDateLbl2.setBounds(20,115,150,30);
        add(selectDateLbl2);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(120, 105, 150, 25);
        dateChooser.setDateFormatString("MM-yyyy");
        add(dateChooser);

        fetchBookingsBtn = new JButton("Fetch Bookings");
        fetchBookingsBtn.setBounds(120, 140, 150, 30);
        add(fetchBookingsBtn);

        bookingsTextArea = new JTextArea();
        bookingsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingsTextArea);
        scrollPane.setBounds(10, 180, 550, 250);
        add(scrollPane);

        fetchBookingsBtn.addActionListener(e -> fetchBookings());

        setVisible(true);
    }

    private void loadPatientIDs() {
        patientIdCombo.addItem("Select Patient");
        ArrayList<String> patientIDs = Database1.getPatientID();
        for (String id : patientIDs) {
            patientIdCombo.addItem(id);
        }
    }

    private void loadDoctorIDs() {
        doctorIdCombo.addItem("Select Doctor");
        ArrayList<String> doctorNames = Database1.getDoctorNames();
        for (String doctor : doctorNames) {
            doctorIdCombo.addItem(doctor);
        }
    }

    private void fetchBookings() {
        String selectedPatient = (String) patientIdCombo.getSelectedItem();
        String selectedDoctor = (String) doctorIdCombo.getSelectedItem();
        Date selectedDate = dateChooser.getDate();

        String bookingInfo = Database1.fetchBookings(selectedPatient, selectedDoctor, (java.sql.Date) selectedDate); //required sql date instead of util date, had to cast sql.date
        bookingsTextArea.setText(bookingInfo);
    }
}



class PatientFrame extends JFrame {
    JTextArea textArea;

    public PatientFrame() {
        setTitle("Patient Information");
        setSize(500, 500);
        setLayout(new BorderLayout());
        setResizable(false);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        fetchAndDisplayPatients();

        setVisible(true);
    }
    // written by ab2617
    private void fetchAndDisplayPatients() {
        String query = "SELECT * FROM patientinformation";

        try {
            ResultSet rs = Database1.executeQuery(query);
            StringBuilder patientDetails = new StringBuilder("Patient Information:\n\n");

            while (rs.next()) {
                patientDetails.append("First Name: ").append(rs.getString("FirstName")).append("\n");
                patientDetails.append("Surname: ").append(rs.getString("Surname")).append("\n");
                patientDetails.append("Date of Birth: ").append(rs.getString("DOB")).append("\n");
                patientDetails.append("Occupation: ").append(rs.getString("Occupation")).append("\n");
                patientDetails.append("Address: ").append(rs.getString("Address")).append("\n");
                patientDetails.append("Emergency_Contact: ").append(rs.getString("Emergency_Contact")).append("\n");
                patientDetails.append("Assigned Doctor: ").append(rs.getString("Assigned_Doctor")).append("\n");
                patientDetails.append("PatientID: ").append(rs.getString("Patient_ID")).append("\n");
                patientDetails.append("Allergies: ").append(rs.getString("Allergies")).append("\n");
                patientDetails.append("Past Surgeries: ").append(rs.getString("Past_Surgeries")).append("\n");
                patientDetails.append("Conditions: ").append(rs.getString("Conditions")).append("\n");
                patientDetails.append("Phone Number: ").append(rs.getString("Phone_Number")).append("\n\n");


            }

            rs.close();
            Database1.closeConnection(null);
            textArea.setText(patientDetails.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            textArea.setText("Error fetching patient details.");
        }
    }
}
