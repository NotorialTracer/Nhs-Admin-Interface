import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    private static JPanel panel, panel2, panel3, panel4;
    private static JFrame frame;

    private static JLabel LoginLabel;
    private static JLabel UserLabel;

    private static JTextField userNameText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton LoginBtn;
    private static JLabel successfulLogin;

    public static void main(String[] args) {

        panel = new JPanel();
        panel.setBounds(0, 0, 700, 75);
        Color colourb = new Color(0,0,102);
        panel.setBackground(colourb);
        frame = new JFrame();
        frame.setTitle("GP Admin Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // for now leave like this
        frame.setSize(600,480);
        frame.getContentPane().setBackground(new Color(214,235,248));
        frame.add(panel);
        panel.setLayout(null);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(0, 160, 450,30);
        panel2.setBackground(new Color(214,235,248));
        frame.add(panel2);

        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(0,190,450,30);
        panel3.setBackground(new Color(214,235,248));
        frame.add(panel3);

        panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBounds(0,230, 450, 50);
        panel4.setBackground(new Color(214,235,248));
        frame.add(panel4);



        LoginLabel = new JLabel("Login");
        frame.add(LoginLabel);
        LoginLabel.setBounds(5,50,80,25);
        LoginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        LoginLabel.setForeground(Color.white);
        panel.add(LoginLabel);

        UserLabel = new JLabel("Username");
        UserLabel.setBounds(145,5,90, 25);
        panel2.add(UserLabel);

        userNameText = new JTextField();
        userNameText.setBounds(235, 2, 160, 25);
        panel2.add(userNameText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(145,-5,160, 40);
        panel3.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(235, 2, 160, 25);
        panel3.add(passwordText);

        LoginBtn = new JButton("Sign in");
        LoginBtn.setBounds(235, 0, 160, 25);
        LoginBtn.addActionListener(new Main());
        panel4.add(LoginBtn);

        successfulLogin = new JLabel("");
        successfulLogin.setBounds(10, 30, 200, 25);
        panel4.add(successfulLogin);

        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userNameText.getText();
        String password = new String (passwordText.getPassword());

        if (AuthenticatingUser.authenticateUser(username, password)) {
            successfulLogin.setText("You have successfully logged in!");
            frame.dispose();
            homePage.main(new String[]{});
        } else {
            successfulLogin.setText("Invalid Credentials! Try again.");
        }
    }
    //adding a getter method for the testing
    public JButton getLoginButton(){

        return LoginBtn;
    }
}