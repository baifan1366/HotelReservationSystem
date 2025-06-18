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
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReportPanel extends JFrame implements ActionListener {
    private JComboBox<String> reportTypeComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton generateButton;
    private JButton backButton;
    private JButton exportPdfButton;
    private JButton exportCsvButton;
    private JTextArea reportTextArea;
    private Report currentReport;
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/clipboard-list.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/file-plus-2.png")),
        new ImageIcon(getClass().getResource("/image/file-input.png"))
    };
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
        titleLabel.setIcon(icons[0]);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);  // text at right
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);  // center vertically
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
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
            "Revenue Report",
            "Popular Room Types Report",
            "Cancellation Analysis Report",
            "Guest Feedback Report",
            "Monthly Revenue Comparison"
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
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        
        // Generate report button
        generateButton = new JButton("Generate");
        generateButton.addActionListener(this);
        generateButton.setIcon(icons[1]);
        generateButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        generateButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        generateButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        generateButton.setIconTextGap(10);
        StyleConfig.applyStyle(generateButton);
        buttonPanel.add(generateButton);
        
        // Export PDF Button
        exportPdfButton = new JButton("Export PDF");
        exportPdfButton.addActionListener(this);
        exportPdfButton.setIcon(icons[3]);
        exportPdfButton.setHorizontalTextPosition(SwingConstants.LEFT);
        exportPdfButton.setVerticalTextPosition(SwingConstants.CENTER);
        exportPdfButton.setHorizontalAlignment(SwingConstants.CENTER);
        exportPdfButton.setIconTextGap(10);
        exportPdfButton.setEnabled(false);
        StyleConfig.applyStyle(exportPdfButton);
        buttonPanel.add(exportPdfButton);
        
        // Export CSV Button
        exportCsvButton = new JButton("Export CSV");
        exportCsvButton.addActionListener(this);
        exportCsvButton.setIcon(icons[4]);
        exportCsvButton.setHorizontalTextPosition(SwingConstants.LEFT);
        exportCsvButton.setVerticalTextPosition(SwingConstants.CENTER);
        exportCsvButton.setHorizontalAlignment(SwingConstants.CENTER);
        exportCsvButton.setIconTextGap(10);
        exportCsvButton.setEnabled(false);
        StyleConfig.applyStyle(exportCsvButton);
        buttonPanel.add(exportCsvButton);
        
        // Back button
        backButton = new JButton("Cancel");
        backButton.addActionListener(this);
        backButton.setIcon(icons[2]);
        backButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        backButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        backButton.setHorizontalAlignment(SwingConstants.CENTER); 
        backButton.setIconTextGap(10);
        StyleConfig.applyAccentStyle(backButton);
        buttonPanel.add(backButton);
        
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
        } else if (e.getSource() == backButton) {
            dispose();
        } else if (e.getSource() == exportPdfButton) {
            exportToPdf();
        } else if (e.getSource() == exportCsvButton) {
            exportToCsv();
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
                case "Popular Room Types Report":
                    report = ReportGenerator.generatePopularRoomTypesReport(startDate, endDate);
                    break;
                case "Cancellation Analysis Report":
                    report = ReportGenerator.generateCancellationAnalysisReport(startDate, endDate);
                    break;
                case "Guest Feedback Report":
                    report = ReportGenerator.generateGuestFeedbackReport(startDate, endDate);
                    break;
                case "Monthly Revenue Comparison":
                    report = ReportGenerator.generateMonthlyComparisonReport(startDate, endDate);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, 
                            "Please select a report type", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            if (report != null) {
                currentReport = report;
                displayReport(report);
                exportPdfButton.setEnabled(true);
                exportCsvButton.setEnabled(true);
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
    
    /**
     * Export the current report to a PDF file
     */
    private void exportToPdf() {
        if (currentReport == null) {
            JOptionPane.showMessageDialog(this, 
                    "Generate a report first", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        fileChooser.setSelectedFile(new File(currentReport.getTitle().replace(" ", "_") + ".pdf"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            try {
                ReportGenerator.exportToPdf(currentReport, filePath);
                JOptionPane.showMessageDialog(this, 
                        "Report exported successfully to PDF file: " + filePath, 
                        "Export Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Error exporting to PDF: " + e.getMessage(), 
                        "Export Error", 
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Export the current report to a CSV file
     */
    private void exportToCsv() {
        if (currentReport == null) {
            JOptionPane.showMessageDialog(this, 
                    "Generate a report first", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setSelectedFile(new File(currentReport.getTitle().replace(" ", "_") + ".csv"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }
            
            try {
                ReportGenerator.exportToCsv(currentReport, filePath);
                JOptionPane.showMessageDialog(this, 
                        "Report exported successfully to CSV file: " + filePath, 
                        "Export Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Error exporting to CSV: " + e.getMessage(), 
                        "Export Error", 
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * For testing export functionality
     */
    public static void main(String[] args) {
        try {
            // Set look and feel
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            
            // Create and show report panel
            ReportPanel reportPanel = new ReportPanel();
            reportPanel.setVisible(true);
            
            // Show message about PDF export
            JOptionPane.showMessageDialog(reportPanel, 
                    "Note: To use PDF export, you need to add the iText PDF library.\n" +
                    "Please see the instructions in the lib/pdf/README.txt file.",
                    "PDF Library Required",
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
