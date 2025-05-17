/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Utility class for styling UI components
 */
public class StyleConfig {
    // Colors
    public static final Color PRIMARY_COLOR = new Color(0, 102, 204);   // Blue
    public static final Color SECONDARY_COLOR = new Color(70, 130, 180); // Steel Blue
    public static final Color ACCENT_COLOR = new Color(255, 102, 0);     // Orange
    public static final Color BG_COLOR = new Color(240, 240, 240);       // Light Gray
    public static final Color TEXT_COLOR = new Color(50, 50, 50);        // Dark Gray
    
    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);
    
    // Borders
    public static final Border PADDING_BORDER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    public static final Border LINE_BORDER = BorderFactory.createLineBorder(SECONDARY_COLOR, 1);
    
    /**
     * Apply style to a JFrame
     */
    public static void applyStyle(JFrame frame) {
        frame.getContentPane().setBackground(BG_COLOR);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
    }
    
    /**
     * Apply style to a JPanel
     */
    public static void applyStyle(JPanel panel) {
        panel.setBackground(BG_COLOR);
        panel.setBorder(PADDING_BORDER);
    }
    
    /**
     * Apply style to a JLabel
     */
    public static void applyStyle(JLabel label) {
        label.setFont(NORMAL_FONT);
        label.setForeground(TEXT_COLOR);
    }
    
    /**
     * Apply style to a JTextField
     */
    public static void applyStyle(JTextField textField) {
        textField.setFont(NORMAL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                LINE_BORDER, 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
    
    /**
     * Apply style to a JButton
     */
    public static void applyStyle(JButton button) {
        button.setFont(NORMAL_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createRaisedBevelBorder()));
    }
    
    /**
     * Apply style to any component
     */
    public static void applyStyle(Component component) {
        if (component instanceof JFrame) {
            applyStyle((JFrame) component);
        } else if (component instanceof JPanel) {
            applyStyle((JPanel) component);
        } else if (component instanceof JLabel) {
            applyStyle((JLabel) component);
        } else if (component instanceof JTextField) {
            applyStyle((JTextField) component);
        } else if (component instanceof JButton) {
            applyStyle((JButton) component);
        }
    }
    
    /**
     * Apply heading style to a label
     */
    public static void applyHeadingStyle(JLabel label) {
        label.setFont(HEADING_FONT);
        label.setForeground(PRIMARY_COLOR);
    }
    
    /**
     * Apply title style to a label
     */
    public static void applyTitleStyle(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(PRIMARY_COLOR);
        label.setHorizontalAlignment(JLabel.CENTER);
    }
    
    /**
     * Apply accent style to a button
     */
    public static void applyAccentStyle(JButton button) {
        applyStyle(button);
        button.setBackground(ACCENT_COLOR);
    }
}
