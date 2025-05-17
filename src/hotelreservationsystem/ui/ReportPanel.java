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
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(buttonPanel);
        
        // Add main panel to frame
        add(mainPanel);
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
        String selectedType = (String) reportTypeComboBox.getSelectedItem();
        boolean dateFieldsEnabled = "Revenue Report".equals(selectedType);
        
        startDateField.setEnabled(dateFieldsEnabled);
        endDateField.setEnabled(dateFieldsEnabled);
    }
    
    private void generateReport() {
        String selectedType = (String) reportTypeComboBox.getSelectedItem();
        Report report = null;
        
        try {
            switch (selectedType) {
                case "Booking Summary Report":
                    report = ReportGenerator.generateBookingSummaryReport();
                    break;
                case "Room Occupancy Report":
                    report = ReportGenerator.generateRoomOccupancyReport();
                    break;
                case "Payment Method Report":
                    report = ReportGenerator.generatePaymentMethodReport();
                    break;
                case "Revenue Report":
                    Date startDate = DateUtil.parseDate(startDateField.getText());
                    Date endDate = DateUtil.parseDate(endDateField.getText());
                    
                    if (!DateUtil.isValidDateRange(startDate, endDate)) {
                        JOptionPane.showMessageDialog(this, 
                                "End date must be after start date", 
                                "Invalid Date Range", 
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    report = ReportGenerator.generateRevenueReport(startDate, endDate);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, 
                            "Please select a report type", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            displayReport(report);
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                    "Invalid date format. Please use yyyy-MM-dd", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
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
        
        for (Report.ReportItem item : report.getItems()) {
            sb.append(item.getLabel()).append(": ").append(item.getValue()).append("\n");
        }
        
        reportTextArea.setText(sb.toString());
    }
}
