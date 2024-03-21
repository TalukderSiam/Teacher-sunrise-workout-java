package javaapplication26;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import org.apache.poi.ss.usermodel.*;

public class Homepage extends JFrame {
    private JLabel welcomeLabel;
    private Font font1, font2;
    private JLabel imageLabel;
    private JLabel newImageLabel;
    private JLabel exerciseLabel;
    private JComboBox<String> exerciseComboBox;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private String username; // Remove 'static'

    public Homepage(String username) {
        this.username = username;

        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLayout(null);

        font2 = new Font("Arial", Font.BOLD, 36);
        font1 = new Font("Arial", Font.BOLD, 13);

        welcomeLabel = new JLabel("============ " + username + "============");
        welcomeLabel.setBounds(300, 210, 1000, 30);
        welcomeLabel.setFont(font2);
        add(welcomeLabel);

        exerciseLabel = new JLabel("Exercise :");
        exerciseLabel.setFont(font2);
        exerciseLabel.setForeground(Color.BLACK);
        exerciseLabel.setBounds(400, 530, 250, 40);
        add(exerciseLabel);

        exerciseComboBox = new JComboBox<>();
        exerciseComboBox.setBounds(600, 535, 180, 30);
        exerciseComboBox.setForeground(Color.BLACK);
        add(exerciseComboBox);

        button1 = new JButton("Manage Exercise");
        button1.setBounds(900, 10, 150, 30);
        button1.setBackground(Color.BLUE);
        button1.setFont(font1);
        button1.setForeground(Color.WHITE);
        add(button1);

        button2 = new JButton("History & Track");
        button2.setBounds(1070, 10, 150, 30);
        button2.setBackground(Color.BLUE);
        button2.setFont(font1);
        button2.setForeground(Color.WHITE);
        add(button2);

        button3 = new JButton("Logout");
        button3.setBounds(1250, 10, 100, 30);
        button3.setBackground(Color.BLUE);
        button3.setFont(font1);
        button3.setForeground(Color.WHITE);
        add(button3);

        loadExerciseNames();
        
       exerciseComboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String selectedExercise = (String) exerciseComboBox.getSelectedItem();
        if (selectedExercise != null) {
            try {
                String duration = exerciseduration(username);
                ExercisePage exercisePage = new ExercisePage(selectedExercise, duration);
                exercisePage.setVisible(true);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});
        
        ImageIcon imageIcon = new ImageIcon("E:\\officiak_work_for_java\\JavaApplication26\\src\\javaapplication26\\pngimg.png");
        imageLabel = new JLabel(imageIcon);
        int x = (getWidth() - imageIcon.getIconWidth()) / 2;
        imageLabel.setBounds(x, 45, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(imageLabel);

        ImageIcon newImageIcon = new ImageIcon("E:\\officiak_work_for_java\\JavaApplication26\\src\\javaapplication26\\option2.jpg");
        newImageLabel = new JLabel(newImageIcon);
        int newX = (getWidth() - newImageIcon.getIconWidth()) / 2;
        int newY = imageLabel.getY() + imageLabel.getHeight() + 20;
        newImageLabel.setBounds(newX, newY - 50, newImageIcon.getIconWidth(), newImageIcon.getIconHeight());
        add(newImageLabel);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageExercise manageexercise = new ManageExercise(username);
                manageexercise.setVisible(true);
                setVisible(false);
            }
        });
    }
    
   public String exerciseduration(String username) throws IOException {
    String cellValue = null;
    FileInputStream file = null;
    try {
        file = new FileInputStream("E:\\officiak_work_for_java\\JavaApplication26\\src\\ExerciseData.xlsx");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(1);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    cellValue = cell.getStringCellValue();
                    if (cellValue.equals(username)) {
                        break;
                    }
                }
            }
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } finally {
        if (file != null) {
            file.close();
        }
    }
    return cellValue;
}

    public void loadExerciseNames() {
        try {
            FileInputStream file = new FileInputStream("E:\\officiak_work_for_java\\JavaApplication26\\src\\ExerciseData.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(2);
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if (cellValue.equals(username)) {
                            String exerciseName = row.getCell(0).getStringCellValue();
                            exerciseComboBox.addItem(exerciseName);
                        }
                    }
                }
            }

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addExercise(String exerciseName) {
        exerciseComboBox.addItem(exerciseName);
    }
    public String getUsername() {
        return username;
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // In your login page, you'll have a variable 'username' containing the username
                String username = "YourUsernameHere"; // Provide the username
                Homepage homepage = new Homepage(username);
                homepage.setVisible(true);
            }
        });
    }    
}
