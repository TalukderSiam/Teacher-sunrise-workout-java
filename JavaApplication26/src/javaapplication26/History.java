package javaapplication26;
import javax.swing.*;
import java.awt.*;

public class History extends JFrame {
    public History() {
        setTitle("History Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Heading
        JLabel headingLabel = new JLabel("History Page");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headingLabel, BorderLayout.NORTH);

        // Date Selection
        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel("Select Date: ");
        JComboBox<String> dateComboBox = new JComboBox<>(new String[]{"Select Date", "Date 1", "Date 2", "Date 3"}); // You can populate this dynamically if needed
        datePanel.add(dateLabel);
        datePanel.add(dateComboBox);
        add(datePanel, BorderLayout.CENTER);

        // Table
        String[] columnNames = {"one", "two", "three"};
        String[][] data = {
                {"Data 1", "Data 2", "Data 3"},
                {"Data 4", "Data 5", "Data 6"},
                {"Data 7", "Data 8", "Data 9"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        pack(); // Resize the frame to fit its contents
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            History history = new History();
            history.setVisible(true);
        });
    }
}
