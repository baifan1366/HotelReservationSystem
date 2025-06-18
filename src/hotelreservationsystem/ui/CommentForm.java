package hotelreservationsystem.ui;

import hotelreservationsystem.dao.CommentDAO;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Room;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dialog;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class CommentForm extends JDialog implements ActionListener {
    private JTextArea commentTextArea;
    private JRadioButton[] ratingButtons;
    private JButton submitButton;
    private JButton cancelButton;
    
    private CommentDAO commentDAO;
    private Customer customer;
    private Room room;
    private Component parent;
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/smile.png")),
        new ImageIcon(getClass().getResource("/image/angry.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png"))
    };
    public CommentForm(Component parent, Customer customer, Room room) {
        super(getWindowForComponent(parent), "Add Comment and Rating", true);
        this.parent = parent;
        this.customer = customer;
        this.room = room;
        this.commentDAO = new CommentDAO();
        
        initComponents();
    }
    
    // Helper method to get the parent window
    private static Dialog getWindowForComponent(Component parent) {
        if (parent instanceof Dialog) {
            return (Dialog) parent;
        }
        if (parent instanceof JFrame) {
            return null; // This will make the JDialog use JFrame as parent
        }
        return getWindowForComponent(parent.getParent());
    }
    
    private void initComponents() {
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        
        // Title Panel
        JLabel titleLabel = new JLabel("Add Comment and Rating for Room " + room.getRoomNumber(), SwingConstants.CENTER);
        StyleConfig.applyTitleStyle(titleLabel);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Comment Panel
        JPanel commentPanel = new JPanel(new BorderLayout(5, 5));
        commentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Your Comment"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        commentTextArea = new JTextArea(5, 20);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        JScrollPane commentScrollPane = new JScrollPane(commentTextArea);
        commentPanel.add(commentScrollPane, BorderLayout.CENTER);
        
        // Rating Panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        ratingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Rate this Room (1-5 stars)"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        ButtonGroup ratingGroup = new ButtonGroup();
        ratingButtons = new JRadioButton[5];

        for (int i = 0; i < 5; i++) {
            JPanel radioWithIconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            radioWithIconPanel.setOpaque(false);

            // Add icon[0] to the left of the first radio button
            if (i == 0) {
                JLabel iconLabel = new JLabel(icons[1]);
                radioWithIconPanel.add(iconLabel);
            }

            ratingButtons[i] = new JRadioButton((i + 1) + " Star" + (i > 0 ? "s" : ""));
            ratingGroup.add(ratingButtons[i]);
            radioWithIconPanel.add(ratingButtons[i]);

            // Add icon[1] to the right of the last radio button
            if (i == 4) {
                JLabel iconLabel = new JLabel(icons[0]);
                radioWithIconPanel.add(iconLabel);
            }

            ratingPanel.add(radioWithIconPanel);
        }

        // Default to 5 stars
        ratingButtons[4].setSelected(true);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        submitButton = new JButton("Submit");
        submitButton.setIcon(icons[2]);
        submitButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        submitButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        submitButton.setIconTextGap(10);
        submitButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.setIcon(icons[3]);
        cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        cancelButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        cancelButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        cancelButton.setIconTextGap(10);
        cancelButton.addActionListener(this);
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(commentPanel, BorderLayout.CENTER);
        mainPanel.add(ratingPanel, BorderLayout.NORTH);
        
        // Add panels to dialog
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Apply styling
        StyleConfig.applyStyle(mainPanel);
        StyleConfig.applyStyle(commentPanel);
        StyleConfig.applyStyle(ratingPanel);
        StyleConfig.applyStyle(buttonPanel);
        StyleConfig.applyStyle(submitButton);
        StyleConfig.applyAccentStyle(cancelButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            saveComment();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
    
    private void saveComment() {
        // Get rating
        int rating = 5; // Default
        for (int i = 0; i < ratingButtons.length; i++) {
            if (ratingButtons[i].isSelected()) {
                rating = i + 1;
                break;
            }
        }
        
        // Get comment text
        String commentText = commentTextArea.getText().trim();
        
        // Save comment
        boolean success = commentDAO.addComment(
            room.getRoomId(),
            customer.getUserId(),
            rating,
            commentText,
            customer.getFullName()
        );
        
        if (success) {
            MessageDialog.showInformation(this, "Success", "Your comment has been submitted successfully.");
            dispose();
            
            // Refresh room details if parent is RoomDetailsDialog
            if (parent instanceof RoomDetailsDialog) {
                ((RoomDetailsDialog) parent).refreshComments();
            }
        } else {
            MessageDialog.showError(this, "Error", "Failed to submit your comment. Please try again.");
        }
    }
} 