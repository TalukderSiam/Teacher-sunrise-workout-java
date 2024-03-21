/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication26;

/**
 *
 * @author siamt
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoginFrame extends JFrame {
    private JLabel titleLabel, emailLabel, passwordLabel,registermessage;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton,registerButton;
    private ImageIcon backgroundImage;
     private Font font1, font2;

    public LoginFrame() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        font1 = new Font("Arial", Font.BOLD, 18);

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("Morning-Jog.jpg"));

        // Set frame size to match image size
        setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        setContentPane(new JLabel(backgroundImage));

        // Set layout to null to manually position components
        setLayout(null);

        // Create components
        JLabel titleLabel = new JLabel("Teacher Sunrise Workout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.white);
        titleLabel.setBounds((backgroundImage.getIconWidth() - 250)/ 2 -100, 20, 450, 30);
        add(titleLabel);

        JLabel loginLabel = new JLabel("Login Form");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(Color.white);
        loginLabel.setBounds((backgroundImage.getIconWidth() - 150) / 2, 200, 150, 30);
        add(loginLabel);

        emailLabel = new JLabel("Username:");
        emailLabel.setForeground(Color.white);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 18));
        emailLabel.setBounds((backgroundImage.getIconWidth() - 80) / 2 - 150, (backgroundImage.getIconHeight() - 25) / 2 - 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds((backgroundImage.getIconWidth() - 200) / 2, (backgroundImage.getIconHeight() - 25) / 2 - 20, 200, 25);
        add(emailField);
        emailField.setFont(font1);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passwordLabel.setBounds((backgroundImage.getIconWidth() - 80) / 2 - 150, (backgroundImage.getIconHeight() - 25) / 2 + 20, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds((backgroundImage.getIconWidth() - 200) / 2, (backgroundImage.getIconHeight() - 25) / 2 + 20, 200, 25);
        add(passwordField);
        emailField.setFont(font1);

        loginButton = new JButton("Login");
        loginButton.setBounds((backgroundImage.getIconWidth() - 100) / 2, (backgroundImage.getIconHeight() - 30) / 2 + 60, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login authentication here
                
                 String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                // Add your authentication logic here
                if (authenticate(email, password)) {
                    
                    navigateToHomePage(email);
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid email or password. Please try again.");
                }
                
                
            }
        });
        add(loginButton);
        
        registermessage = new JLabel("Not Register Yet?");
        registermessage.setForeground(Color.white);
        registermessage.setFont(new Font("Arial", Font.BOLD, 16));
        registermessage.setBounds((backgroundImage.getIconWidth() - 80) / 2 - 120, (backgroundImage.getIconHeight() - 25) / 2 + 100, 150, 25);
        add(registermessage);
        
       JButton registerButton = new JButton("Register");
        registerButton.setBounds((backgroundImage.getIconWidth() - 150) / 2+60, (backgroundImage.getIconHeight() - 30) / 2 + 105, 100, 20);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the register page here
                // For now, display a message
              Register rego = new Register();
              rego.setVisible(true);
             
               dispose();
               
            }
        });
        add(registerButton);

        setVisible(true);
    }
    
    
     private boolean authenticate(String email, String password) {
        try {
            FileInputStream file = new FileInputStream(new File("E:\\officiak_work_for_java\\JavaApplication26\\src\\register.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String storedEmail = row.getCell(0).getStringCellValue();
                String storedPassword = row.getCell(2).getStringCellValue();
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    workbook.close();
                    file.close();
                    return true;
                }
            }
            workbook.close();
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
     
     private void navigateToHomePage(String email) {
        // Navigate to the homepage
         Homepage homepage = new Homepage(email);
         homepage.setVisible(true);
        dispose(); // Close the login frame
        
        
        
    }
     
     

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
    }
}
