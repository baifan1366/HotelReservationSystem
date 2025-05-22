/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import hotelreservationsystem.dao.PaymentDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.CardPayment;
import hotelreservationsystem.model.CashPayment;
import hotelreservationsystem.model.Payment;
import hotelreservationsystem.util.ValidationUtil;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PaymentForm extends JFrame implements ActionListener {
    private Booking booking;
    private CustomerDashboard dashboard;
    
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel cashPanel;
    private JPanel creditCardPanel;
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JComboBox<Integer> expiryMonthComboBox;
    private JComboBox<Integer> expiryYearComboBox;
    private JTextField cvvField;
    private JButton payButton;
    private JButton cancelButton;
    
    private PaymentDAO paymentDAO;
    
    public PaymentForm(Booking booking, CustomerDashboard dashboard) {
        this.booking = booking;
        this.dashboard = dashboard;
        this.paymentDAO = new PaymentDAO();
        initComponents();
    }
    
    private void initComponents() {
        // Set frame properties
        setTitle("Payment");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Payment");
        StyleConfig.applyTitleStyle(titleLabel);
        titlePanel.add(titleLabel);
        StyleConfig.applyStyle(titlePanel);
        
        // Create booking info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JLabel roomLabel = new JLabel("Room:");
        JLabel roomValueLabel = new JLabel(booking.getRoom().getRoomNumber() + " - " + booking.getRoom().getType());
        StyleConfig.applyStyle(roomLabel);
        StyleConfig.applyStyle(roomValueLabel);
        infoPanel.add(roomLabel);
        infoPanel.add(roomValueLabel);
        
        JLabel dateLabel = new JLabel("Dates:");
        JLabel dateValueLabel = new JLabel(booking.getCheckInDate() + " to " + booking.getCheckOutDate());
        StyleConfig.applyStyle(dateLabel);
        StyleConfig.applyStyle(dateValueLabel);
        infoPanel.add(dateLabel);
        infoPanel.add(dateValueLabel);
        
        JLabel amountLabel = new JLabel("Total Amount:");
        JLabel amountValueLabel = new JLabel(String.format("$%.2f", booking.getTotalAmount()));
        StyleConfig.applyStyle(amountLabel);
        StyleConfig.applyHeadingStyle(amountValueLabel);
        infoPanel.add(amountLabel);
        infoPanel.add(amountValueLabel);
        
        // Create payment method panel
        JPanel methodPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel radioPanel = new JPanel(new GridLayout(1, 2));
        
        // Radio buttons for payment method
        cashRadioButton = new JRadioButton("Cash Payment");
        cardRadioButton = new JRadioButton("Credit Card");
        
        // Group radio buttons
        ButtonGroup methodGroup = new ButtonGroup();
        methodGroup.add(cashRadioButton);
        methodGroup.add(cardRadioButton);
        
        // Add action listeners
        cashRadioButton.addActionListener(this);
        cardRadioButton.addActionListener(this);
        
        // Select cash by default
        cashRadioButton.setSelected(true);
        
        radioPanel.add(cashRadioButton);
        radioPanel.add(cardRadioButton);
        
        // Create card layout panel for payment details
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Create cash panel
        cashPanel = new JPanel();
        cashPanel.add(new JLabel("Pay at Hotel Reception upon Check-in"));
        
        // Create credit card panel
        creditCardPanel = createCreditCardPanel();
        
        // Add panels to card panel
        cardPanel.add(cashPanel, "cash");
        cardPanel.add(creditCardPanel, "card");
        
        // Show cash panel by default
        cardLayout.show(cardPanel, "cash");
        
        methodPanel.add(radioPanel, BorderLayout.NORTH);
        methodPanel.add(cardPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Pay button
        payButton = new JButton("Pay Now");
        payButton.addActionListener(this);
        StyleConfig.applyStyle(payButton);
        
        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        StyleConfig.applyAccentStyle(cancelButton);
        
        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);
        
        // Create a south panel to hold both method and button panels
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.add(methodPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(radioPanel);
        StyleConfig.applyStyle(infoPanel);
        StyleConfig.applyStyle(cashPanel);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createCreditCardPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        // Card number field
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        StyleConfig.applyStyle(cardNumberLabel);
        StyleConfig.applyStyle(cardNumberField);
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        
        // Card holder field
        JLabel cardHolderLabel = new JLabel("Card Holder:");
        cardHolderField = new JTextField();
        StyleConfig.applyStyle(cardHolderLabel);
        StyleConfig.applyStyle(cardHolderField);
        panel.add(cardHolderLabel);
        panel.add(cardHolderField);
        
        // Expiry date
        JLabel expiryLabel = new JLabel("Expiry Date (MM/YYYY):");
        
        JPanel expiryPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        
        // Month combo box
        expiryMonthComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            expiryMonthComboBox.addItem(i);
        }
        
        // Year combo box
        expiryYearComboBox = new JComboBox<>();
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            expiryYearComboBox.addItem(i);
        }
        
        expiryPanel.add(expiryMonthComboBox);
        expiryPanel.add(expiryYearComboBox);
        
        StyleConfig.applyStyle(expiryLabel);
        panel.add(expiryLabel);
        panel.add(expiryPanel);
        
        // CVV field
        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JTextField();
        StyleConfig.applyStyle(cvvLabel);
        StyleConfig.applyStyle(cvvField);
        panel.add(cvvLabel);
        panel.add(cvvField);
        
        return panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashRadioButton) {
            cardLayout.show(cardPanel, "cash");
        } else if (e.getSource() == cardRadioButton) {
            cardLayout.show(cardPanel, "card");
        } else if (e.getSource() == payButton) {
            processPayment();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
    
    private void processPayment() {
        Payment payment = null;
        
        try {
            if (cashRadioButton.isSelected()) {
                // Create cash payment
                payment = new CashPayment(
                        booking,
                        booking.getTotalAmount(),
                        new Date()
                );
                payment.setStatus("Pending Payment"); // Set cash payment status
            } else if (cardRadioButton.isSelected()) {
                // Validate card details
                String cardNumber = cardNumberField.getText();
                String cardHolder = cardHolderField.getText();
                int expiryMonth = (int) expiryMonthComboBox.getSelectedItem();
                int expiryYear = (int) expiryYearComboBox.getSelectedItem();
                String cvv = cvvField.getText();
                
                if (cardNumber.isEmpty() || cardHolder.isEmpty() || cvv.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill all card details",
                            "Payment Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!ValidationUtil.isValidCreditCardNumber(cardNumber)) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid card number",
                            "Payment Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!ValidationUtil.isValidExpiryDate(expiryMonth, expiryYear)) {
                    JOptionPane.showMessageDialog(this,
                            "Card is expired",
                            "Payment Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create card payment
                payment = new CardPayment(
                        booking,
                        booking.getTotalAmount(),
                        new Date(),
                        cardNumber,
                        cardHolder,
                        expiryMonth,
                        expiryYear,
                        cvv
                );
                payment.setStatus("Paid"); // Set card payment status
            }
            
            if (payment != null) {
                boolean success = paymentDAO.createPayment(payment);
                
                if (success) {
                    String successMessage = cashRadioButton.isSelected() ? 
                            "Cash payment registered. Status: Pending Payment" : 
                            "Card payment processed successfully. Status: Paid";
                    
                    JOptionPane.showMessageDialog(this,
                            successMessage,
                            "Payment Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh dashboard
                    dashboard.refreshDashboard();
                    
                    // Close payment form
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error processing payment",
                            "Payment Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
