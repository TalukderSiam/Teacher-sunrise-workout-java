package javaapplication26;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExercisePage extends JFrame implements ActionListener {

    private JLabel exerciseLabel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton finishButton;
    private Timer timer;
    private int secondsPassed;
    private int durationInSeconds; // Duration in seconds
    String exerciseName;

    public ExercisePage(String exerciseName, String duration) {
        this.exerciseName=exerciseName;
        setTitle("Exercise Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load background image
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("exercise.jpg"));
        int imageWidth = backgroundImage.getIconWidth();
        int imageHeight = backgroundImage.getIconHeight();

        // Set frame size to match image size
        setSize(imageWidth, imageHeight);

        setContentPane(new JLabel(backgroundImage));

        // Set layout to null to manually position components
        setLayout(null);

        exerciseLabel = new JLabel("Exercise Name: " + exerciseName);
        exerciseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        exerciseLabel.setFont(new Font("Arial", Font.BOLD, 30));
        exerciseLabel.setForeground(Color.WHITE); // Set text color to white
        exerciseLabel.setBounds(0, 0, getWidth(), 50); // Set the width to match the frame width
        add(exerciseLabel);

        durationInSeconds = Integer.parseInt(duration) * 60;

        timerLabel = new JLabel("00:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        timerLabel.setBounds(350, 200, 300, 100);
        timerLabel.setOpaque(true); // Enable background color
        timerLabel.setBackground(Color.LIGHT_GRAY); // Set background color
        timerLabel.setForeground(Color.BLACK); // Set text color to black
        add(timerLabel);

        startButton = new JButton("Start Time");
        startButton.setBounds(350, 500, 150, 30);
        startButton.addActionListener(this);
        startButton.setBackground(Color.GREEN); // Set background color
        startButton.setForeground(Color.BLACK); // Set text color to black
        startButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font to bold
        add(startButton);

        finishButton = new JButton("Finish Exercise");
        finishButton.setBounds(530, 500, 150, 30);
        finishButton.setBackground(Color.RED); // Set background color
        finishButton.setForeground(Color.BLACK); // Set text color to black
        finishButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font to bold
        add(finishButton);

        timer = new Timer(1000, this);
        secondsPassed = 0;

        setLocationRelativeTo(null); // Center the frame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            timer.start();
        } else if (e.getSource() == timer) {
            secondsPassed++;
            int remainingSeconds = durationInSeconds - secondsPassed;
            if (remainingSeconds <= 0) {
                timer.stop();
                timerLabel.setText("Duration Reached");
                
                modifyExcelData(exerciseName);
            } else {
                int hours = remainingSeconds / 3600;
                int minutes = (remainingSeconds % 3600) / 60;
                int seconds = remainingSeconds % 60;
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerLabel.setText(timeString);
            }
        }
        
        
    }
    
    private void modifyExcelData(String exerciseName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("E:\\officiak_work_for_java\\JavaApplication26\\src\\history.xlsx"));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming exercise name is in the first column
                if (cell != null && cell.getStringCellValue().equalsIgnoreCase(exerciseName)) {
                    Cell statusCell = row.getCell(2);
                    if (statusCell != null && statusCell.getStringCellValue().equalsIgnoreCase("NO")) {
                        statusCell.setCellValue("YES"); // Modify the status to "YES"

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = dateFormat.format(currentDate);

                        Cell dateCell = row.createCell(4);
                        dateCell.setCellValue(formattedDate);
                    }
                }
            }

            fileInputStream.close();

            FileOutputStream outputStream = new FileOutputStream(new File("E:\\officiak_work_for_java\\JavaApplication26\\src\\history.xlsx"));
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExercisePage exercisePage = new ExercisePage("Running", "30");
            exercisePage.setVisible(true);
        });
    }
}