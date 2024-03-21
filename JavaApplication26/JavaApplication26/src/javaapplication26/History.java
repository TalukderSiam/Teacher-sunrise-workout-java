package javaapplication26;

import com.toedter.calendar.JCalendar;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class History extends JFrame {
    private JLabel dateLabel;
    String username;

    public History(String username) {
        this.username = username;
        setTitle("History Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background Image
        ImageIcon backgroundImage = new ImageIcon("E:\\officiak_work_for_java\\JavaApplication26\\src\\javaapplication26\\history.jpg"); // Path to your image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        setContentPane(backgroundLabel);

        // Heading
        JLabel headingLabel = new JLabel("History Page");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundLabel.add(headingLabel, BorderLayout.NORTH);

        // Date Selection using JCalendar
        JPanel datePanel = new JPanel();
        datePanel.setOpaque(false);
        JLabel selectLabel = new JLabel("Select Date: ");
        JButton selectDateButton = new JButton("Select Date");
        dateLabel = new JLabel();
        JCalendar jCalendar = new JCalendar();
        datePanel.add(selectLabel);
        datePanel.add(selectDateButton);
        datePanel.add(dateLabel);
        backgroundLabel.add(datePanel, BorderLayout.CENTER);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "Selected Date: " + dateFormat.format(new Date());
        dateLabel.setText(currentDate);

        selectDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(History.this, "Select Date", true);
                dialog.setLayout(new BorderLayout());
                dialog.add(jCalendar, BorderLayout.CENTER);
                JButton selectButton = new JButton("Select");
                selectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Date selectedDate = jCalendar.getDate();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dateLabel.setText("Selected Date: " + dateFormat.format(selectedDate));
                        dialog.dispose();
                    }
                });
                dialog.add(selectButton, BorderLayout.SOUTH);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        // Read data from Excel file and filter based on username
        String excelFilePath = "E:\\officiak_work_for_java\\JavaApplication26\\src\\history.xlsx";
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);
            model.addColumn("Exercise Name");
            model.addColumn("Duration");
            model.addColumn("Complete");

            for (int r = 0; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    String cellValue = row.getCell(3).getStringCellValue(); // Assuming username is in the first column
                    if (cellValue.equals(username)) {
                        Object[] rowData = new Object[cols];
                        for (int c = 0; c < cols; c++) {
                            rowData[c] = row.getCell(c).toString(); // Assuming data starts from the second column
                        }
                        model.addRow(rowData);
                    }
                }
            }

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            backgroundLabel.add(scrollPane, BorderLayout.SOUTH);
        } catch (IOException | EncryptedDocumentException  ex) {
            ex.printStackTrace();
        }

        setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = ""; // Provide username here
            History history = new History(username);
            history.setVisible(true);
        });
    }
}
