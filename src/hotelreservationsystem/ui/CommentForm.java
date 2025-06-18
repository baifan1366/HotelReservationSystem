package hotelreservationsystem.ui;

import hotelreservationsystem.dao.CommentDAO;
import hotelreservationsystem.model.Comment;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Room;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class CommentForm extends JDialog implements ActionListener {
    private Customer customer;
    private Room room;
    private Comment existingComment;
    private boolean isEditMode = false;
    
    private JRadioButton[] ratingButtons;
    private JTextArea commentTextArea;
    private JButton submitButton;
    private JButton cancelButton;
    private CommentDAO commentDAO;
    
    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/smile.png")),
        new ImageIcon(getClass().getResource("/image/angry.png")),
        new ImageIcon(getClass().getResource("/image/circle-check.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png"))
    };
    
    public CommentForm(JFrame parent, Customer customer, Room room) {
        super(parent, "Add Room Comment", true);
        this.customer = customer;
        this.room = room;
        this.commentDAO = new CommentDAO();
        initComponents();
    }
    
    // Constructor for editing existing comment
    public CommentForm(JFrame parent, Customer customer, Room room, Comment comment) {
        super(parent, "Edit Room Comment", true);
        this.customer = customer;
        this.room = room;
        this.existingComment = comment;
        this.isEditMode = true;
        this.commentDAO = new CommentDAO();
        initComponents();
        // Load existing comment data
        loadExistingComment();
    }
    
    private void initComponents() {
        setSize(400, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel
        JLabel titleLabel = new JLabel(isEditMode ? "Edit Comment" : "Add Comment for Room " + room.getRoomNumber());
        StyleConfig.applyTitleStyle(titleLabel);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        
        // Rating panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ratingLabel = new JLabel("Rating: ");
        StyleConfig.applyStyle(ratingLabel);
        ratingPanel.add(ratingLabel);
        
        // Rating buttons
        ButtonGroup ratingGroup = new ButtonGroup();
        ratingButtons = new JRadioButton[5];
        
        for (int i = 0; i < 5; i++) {
            ratingButtons[i] = new JRadioButton((i + 1) + " ★");
            StyleConfig.applyStyle(ratingButtons[i]);
            ratingGroup.add(ratingButtons[i]);
            ratingPanel.add(ratingButtons[i]);
        }
        
        // Default select 5 stars
        ratingButtons[4].setSelected(true);
        
        // Comment panel
        JPanel commentPanel = new JPanel(new BorderLayout(5, 5));
        JLabel commentLabel = new JLabel("Comment: ");
        StyleConfig.applyStyle(commentLabel);
        commentTextArea = new JTextArea(5, 20);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(commentTextArea);
        
        commentPanel.add(commentLabel, BorderLayout.NORTH);
        commentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add to form panel
        formPanel.add(ratingPanel);
        formPanel.add(commentPanel);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        submitButton = new JButton(isEditMode ? "Update Comment" : "Submit Comment");
        StyleConfig.applyStyle(submitButton);
        submitButton.addActionListener(this);
        
        cancelButton = new JButton("Cancel");
        StyleConfig.applyAccentStyle(cancelButton);
        cancelButton.addActionListener(this);
        
        buttonsPanel.add(submitButton);
        buttonsPanel.add(cancelButton);
        
        // Add to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Add to dialog
        add(mainPanel);
        
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    // Load existing comment data for edit mode
    private void loadExistingComment() {
        if (existingComment != null) {
            // Set rating
            int rating = existingComment.getRating();
            if (rating >= 1 && rating <= 5) {
                ratingButtons[rating - 1].setSelected(true);
            }
            
            // Set comment text
            commentTextArea.setText(existingComment.getComment());
        }
    }
    
    // Get the selected rating
    private int getSelectedRating() {
        for (int i = 0; i < ratingButtons.length; i++) {
            if (ratingButtons[i].isSelected()) {
                return i + 1;
            }
        }
        return 5; // Default to 5 stars
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Get comment text
            String commentText = commentTextArea.getText().trim();
            
            // Validate comment
            if (commentText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Please enter a comment.", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get rating
            int rating = getSelectedRating();
            
            boolean success;
            if (isEditMode) {
                // Update existing comment
                existingComment.setRating(rating);
                existingComment.setComment(commentText);
                existingComment.setCommentDate(new Date()); // Update date to now
                success = commentDAO.updateComment(existingComment);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                            "Comment updated successfully.", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Failed to update comment.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Add new comment
                success = commentDAO.addComment(
                        room.getRoomId(), 
                        customer.getUserId(), 
                        rating, 
                        commentText,
                        customer.getFullName());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                            "Comment added successfully.", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Failed to add comment.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
            // Refresh comments in parent dialog
            if (success && getParent() instanceof RoomDetailsDialog) {
                ((RoomDetailsDialog) getParent()).refreshComments();
            }
            
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
} 