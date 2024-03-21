package javaapplication26;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.table.JTableHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManageExercise extends JFrame {

    private DefaultTableModel tableModel;
    private JTextField exerciseNameField;
    private JTextField durationField;

    public ManageExercise(String username ) {
        setTitle("Exercise Tracker");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel exerciseLabel = new JLabel("Exercise Name:");
        exerciseLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        exerciseNameField = new JTextField(20);
        exerciseNameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        JLabel durationLabel = new JLabel("Duration (minutes):");
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        durationField = new JTextField(10);
        durationField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        JButton homepageButton = new JButton("Homepage");
        homepageButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size

        // Add components to input panel
        inputPanel.add(exerciseLabel);
        inputPanel.add(exerciseNameField);
        inputPanel.add(durationLabel);
        inputPanel.add(durationField);
        inputPanel.add(addButton);
        inputPanel.add(homepageButton);

        // Create table
        String[] columns = {"Exercise Name", "Duration (minutes)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 17));

        // Increase row height
        table.setRowHeight(25);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));

        // Add components to main panel
        panel.add(inputPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        //String username = Homepage.getUsername();
        displayUserData(username);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String exerciseName = exerciseNameField.getText();
        String duration = durationField.getText();
        if (!exerciseName.isEmpty() && !duration.isEmpty()) {
            Object[] row = {exerciseName, duration};
            tableModel.addRow(row);
            exerciseNameField.setText("");
            durationField.setText("");
            //String username = homepage.getUsername();

            try {
                String filePath = "E:\\officiak_work_for_java\\JavaApplication26\\src\\ExerciseData.xlsx";
                String historyFilePath = "E:\\officiak_work_for_java\\JavaApplication26\\src\\history.xlsx";
                Workbook workbook = null;
                Sheet sheet = null;
                Row excelRow = null;
                Cell cell = null;

                // Writing to ExerciseData.xlsx
                File file = new File(filePath);
                if (!file.exists()) {
                    workbook = new XSSFWorkbook();
                    sheet = workbook.createSheet("Exercise Data");
                    excelRow = sheet.createRow(0);
                    cell = excelRow.createCell(0);
                    cell.setCellValue("Exercise Name");
                    cell = excelRow.createCell(1);
                    cell.setCellValue("Duration");
                    cell = excelRow.createCell(2);
                    cell.setCellValue("Username");
                } else {
                    FileInputStream fis = new FileInputStream(file);
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheetAt(0);
                }

                excelRow = sheet.createRow(sheet.getLastRowNum() + 1);
                cell = excelRow.createCell(0);
                cell.setCellValue(exerciseName);
                cell = excelRow.createCell(1);
                cell.setCellValue(duration);
                cell = excelRow.createCell(2);
                cell.setCellValue(username);

                FileOutputStream fos = new FileOutputStream(filePath);
                workbook.write(fos);
                workbook.close();
                fos.close();

                // Writing to history.xlsx
                File historyFile = new File(historyFilePath);
                if (!historyFile.exists()) {
                    workbook = new XSSFWorkbook();
                    sheet = workbook.createSheet("History");
                    excelRow = sheet.createRow(0);
                    cell = excelRow.createCell(0);
                    cell.setCellValue("Exercise Name");
                    cell = excelRow.createCell(1);
                    cell.setCellValue("Duration");
                    cell = excelRow.createCell(2);
                    cell.setCellValue("NO");
                    cell = excelRow.createCell(3);
                    cell.setCellValue("Date");
                    
                } else {
                    FileInputStream fis = new FileInputStream(historyFile);
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheetAt(0);
                }

                excelRow = sheet.createRow(sheet.getLastRowNum() + 1);
                cell = excelRow.createCell(0);
                cell.setCellValue(exerciseName);
                cell = excelRow.createCell(1);
                cell.setCellValue(duration);
                cell = excelRow.createCell(2);
                cell.setCellValue("NO");
                cell = excelRow.createCell(3);
                cell.setCellValue(username);

                FileOutputStream fos1 = new FileOutputStream(historyFilePath);
                workbook.write(fos1);
                workbook.close();
                fos1.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while writing to Excel.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter exercise name and duration.");
        }
    }
});

        homepageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle going to homepage
                //String username = Homepage.getUsername();
                Homepage homepage = new Homepage(username);
                homepage.setVisible(true);
                dispose();
                // You can uncomment and modify this section according to your requirements
            }
        });

        // Add main panel to frame
        add(panel);
        setVisible(true);
    }

    private void displayUserData(String username) {
    try {
        String filePath = "E:\\officiak_work_for_java\\JavaApplication26\\src\\ExerciseData.xlsx"; // Change this to your desired file path
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        tableModel.setRowCount(0); // Clear previous data from the table

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip the header row
            Cell usernameCell = row.getCell(2); // Assuming username is in the third column (index 2)
            if (usernameCell != null && usernameCell.getStringCellValue().equals(username)) {
                String exerciseName = row.getCell(0).getStringCellValue();
                String duration;
                Cell durationCell = row.getCell(1);
                if (durationCell.getCellType() == CellType.NUMERIC) {
                    // If the cell contains a numeric value, convert it to string
                    duration = String.valueOf((int) durationCell.getNumericCellValue());
                } else {
                    // If the cell contains a string, directly retrieve the string value
                    duration = durationCell.getStringCellValue();
                }
                Object[] rowData = {exerciseName, duration};
                tableModel.addRow(rowData);
            }
        }

        workbook.close();
        fis.close();
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "An error occurred while reading data from Excel.");
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               
            }
        });
    }
}
