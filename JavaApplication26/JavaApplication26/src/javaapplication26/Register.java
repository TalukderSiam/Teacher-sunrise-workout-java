
package javaapplication26;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Register extends JFrame {
    private JLabel usernameLabel, emailLabel, passwordLabel, ageLabel, weightLabel, heightLabel, header;
    private JTextField usernameField, emailField, ageField, weightField, heightField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private Container container;
    private Font font1, font2;

   public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 100, 1000, 650);
        setTitle("Register Form");
        font1 = new Font("Arial", Font.BOLD, 18);
        font2 = new Font("Arial", Font.BOLD, 24);

        container = getContentPane();
        container.setLayout(null);
        container.setBackground(Color.blue);

        header = new JLabel("Register Form");
        header.setBounds(350, 50, 200, 100);
        header.setFont(font2);
        header.setForeground(Color.white);
        container.add(header);

        usernameLabel = new JLabel("USERNAME ");
        usernameLabel.setBounds(290, 150, 200, 100);
        usernameLabel.setFont(font1);
        usernameLabel.setForeground(Color.white);
        container.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(450, 180, 170, 30);
        usernameField.setFont(font1);
        container.add(usernameField);

        emailLabel = new JLabel("EMAIL ");
        emailLabel.setBounds(290, 190, 200, 100);
        emailLabel.setFont(font1);
        emailLabel.setForeground(Color.white);
        container.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(450, 220, 170, 30);
        emailField.setFont(font1);
        container.add(emailField);

        passwordLabel = new JLabel("PASSWORD ");
        passwordLabel.setBounds(290, 220, 200, 100);
        passwordLabel.setFont(font1);
        passwordLabel.setForeground(Color.white);
        container.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(450, 260, 170, 30);
        passwordField.setFont(font1);
        container.add(passwordField);

        ageLabel = new JLabel("AGE ");
        ageLabel.setBounds(290, 260, 200, 100);
        ageLabel.setFont(font1);
        ageLabel.setForeground(Color.white);
        container.add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(450, 300, 170, 30);
        ageField.setFont(font1);
        container.add(ageField);

        heightLabel = new JLabel("HEIGHT ");
        heightLabel.setBounds(290, 300, 200, 100);
        heightLabel.setFont(font1);
        heightLabel.setForeground(Color.white);
        container.add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(450, 340, 170, 30);
        heightField.setFont(font1);
        container.add(heightField);

        weightLabel = new JLabel("WEIGHT ");
        weightLabel.setBounds(290, 340, 200, 100);
        weightLabel.setFont(font1);
        weightLabel.setForeground(Color.white);
        container.add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(450, 380, 170, 30);
        weightField.setFont(font1);
        container.add(weightField);

        registerButton = new JButton("REGISTER ");
        registerButton.setBounds(350, 430, 150, 50);
        registerButton.setFont(font1);
        registerButton.setForeground(Color.black);
        container.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String age = ageField.getText();
        String height = heightField.getText();
        String weight = weightField.getText();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !age.isEmpty() && !height.isEmpty() && !weight.isEmpty()) {
            
             try {
                FileInputStream file = new FileInputStream(new File("E:\\officiak_work_for_java\\JavaApplication26\\src\\register.xlsx"));
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                
                
                // Check if this is the first entry, if so, add column headers
                if (sheet.getPhysicalNumberOfRows() == 0) {
                    Row headerRow = sheet.createRow(0);
                    headerRow.createCell(0).setCellValue("Name");
                    headerRow.createCell(1).setCellValue("Email   Adress");
                    headerRow.createCell(2).setCellValue("password");
                    headerRow.createCell(3).setCellValue("Age");
                    headerRow.createCell(4).setCellValue("Height");
                    headerRow.createCell(5).setCellValue("Weight");
                }
                
                
                int lastRowNum = sheet.getLastRowNum();
                Row row = sheet.createRow(++lastRowNum);
                row.createCell(0).setCellValue(username);
                row.createCell(1).setCellValue(email);
                row.createCell(2).setCellValue(password);
                row.createCell(3).setCellValue(age);
                row.createCell(4).setCellValue(height);
                row.createCell(5).setCellValue(weight);
                
                
                
                FileOutputStream outFile = new FileOutputStream(new File("E:\\officiak_work_for_java\\JavaApplication26\\src\\register.xlsx"));
                workbook.write(outFile);
                outFile.close();
                workbook.close();
                
                
                JOptionPane.showMessageDialog(this, "Registration successful!");
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                ageField.setText("");
                heightField.setText("");
                weightField.setText("");
                
                LoginFrame login=new LoginFrame();
                login.setVisible(true);
                dispose();
                
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: Unable to register!");
            }
        }
                
                
                
                
           else {
            JOptionPane.showMessageDialog(this, "All fields are required!");
        }
    }

    public static void main(String[] args) {
        new Register().setVisible(true);
    }
}
