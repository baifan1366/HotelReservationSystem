/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.model.Report;
import hotelreservationsystem.util.DateUtil;
import hotelreservationsystem.util.ReportGenerator;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReportPanel extends JFrame implements ActionListener {
    private JComboBox<String> reportTypeComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton generateButton;
    private JTextArea reportTextArea;
    
    public ReportPanel() {
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Hotel Reservation System - Reports");
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Report Generator");
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        // Report type combo box
        JLabel reportTypeLabel = new JLabel("Report Type:");
        reportTypeComboBox = new JComboBox<>(new String[]{
            "Booking Summary Report",
            "Room Occupancy Report",
            "Payment Method Report",
            "Revenue Report"
        });
        reportTypeComboBox.addActionListener(this);
        StyleConfig.applyStyle(reportTypeLabel);
        formPanel.add(reportTypeLabel);
        formPanel.add(reportTypeComboBox);
        
        // Start date field
        JLabel startDateLabel = new JLabel("Start Date (yyyy-MM-dd):");
        startDateField = new JTextField(DateUtil.getTodayAsString());
        startDateField.setEnabled(false);
        StyleConfig.applyStyle(startDateLabel);
        StyleConfig.applyStyle(startDateField);
        formPanel.add(startDateLabel);
        formPanel.add(startDateField);
        
        // End date field
        JLabel endDateLabel = new JLabel("End Date (yyyy-MM-dd):");
        endDateField = new JTextField(DateUtil.getTodayAsString());
        endDateField.setEnabled(false);
        StyleConfig.applyStyle(endDateLabel);
        StyleConfig.applyStyle(endDateField);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        
        // Generate report button
        generateButton = new JButton("Generate Report");
        generateButton.addActionListener(this);
        StyleConfig.applyStyle(generateButton);
        buttonPanel.add(generateButton);
        
        // Report text area
        reportTextArea = new JTextArea(15, 50);
        reportTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        
        // Create output panel to contain the scroll pane
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Change this part to add the outputPanel separately
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(outputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(buttonPanel);
        StyleConfig.applyStyle(outputPanel);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Initialize with report type selection
        handleReportTypeChange();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reportTypeComboBox) {
            handleReportTypeChange();
        } else if (e.getSource() == generateButton) {
            generateReport();
        }
    }
    
    private void handleReportTypeChange() {
        // Enable date fields for all report types
        startDateField.setEnabled(true);
        endDateField.setEnabled(true);
    }
    
    private void generateReport() {
        String selectedType = (String) reportTypeComboBox.getSelectedItem();
        Report report = null;
        
        try {
            // Parse dates for all report types
            Date startDate = DateUtil.parseDate(startDateField.getText());
            Date endDate = DateUtil.parseDate(endDateField.getText());
            
            if (!DateUtil.isValidDateRange(startDate, endDate)) {
                JOptionPane.showMessageDialog(this, 
                        "End date must be after start date", 
                        "Invalid Date Range", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            switch (selectedType) {
                case "Booking Summary Report":
                    report = ReportGenerator.generateBookingSummaryReport(startDate, endDate);
                    break;
                case "Room Occupancy Report":
                    report = ReportGenerator.generateRoomOccupancyReport(startDate, endDate);
                    break;
                case "Payment Method Report":
                    report = ReportGenerator.generatePaymentMethodReport(startDate, endDate);
                    break;
                case "Revenue Report":
                    report = ReportGenerator.generateRevenueReport(startDate, endDate);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, 
                            "Please select a report type", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            if (report != null) {
                displayReport(report);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to generate report. Report came back as null.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid date format. Please use yyyy-MM-dd", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error generating report: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void displayReport(Report report) {
        if (report == null) {
            reportTextArea.setText("Error generating report");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(report.getTitle()).append(" ===\n");
        sb.append("Generated: ").append(report.getGenerationDate()).append("\n\n");
        
        if (report.getItems().isEmpty()) {
            sb.append("No data available for this report type.\n");
            sb.append("Make sure you have added bookings, rooms, or payments to the system.");
        } else {
            for (Report.ReportItem item : report.getItems()) {
                sb.append(item.getLabel()).append(": ").append(item.getValue()).append("\n");
            }
        }
        
        reportTextArea.setText(sb.toString());
    }
}
