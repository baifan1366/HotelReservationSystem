package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.dao.CommentDAO;
import hotelreservationsystem.dao.RoomDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Comment;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.Room;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class RoomDetailsDialog extends JDialog implements ActionListener {
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    private JTable commentsTable;
    private DefaultTableModel commentsTableModel;
    private JButton closeButton;
    private JButton addCommentButton;
    private JButton editCommentButton;
    private JButton deleteCommentButton;
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    private CommentDAO commentDAO;
    private JTabbedPane tabbedPane;
    private JLabel ratingLabel;
    private SimpleDateFormat dateFormat;
    private int lastSelectedRoomNumber = -1;
    private Map<Integer, Comment> commentRowMap = new HashMap<>();

    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/image/message-circle.png")),
        new ImageIcon(getClass().getResource("/image/circle-x.png")),
        new ImageIcon(getClass().getResource("/image/settings.png")), // Edit icon
        new ImageIcon(getClass().getResource("/image/file-x-2.png"))  // Delete icon
    };
    
    public RoomDetailsDialog(JFrame parent) {
        super(parent, "All Rooms and Booking Details", true);
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
        this.commentDAO = new CommentDAO();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        initComponents();
        
        // Add window listener to refresh data when dialog is shown
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                // Refresh data when dialog becomes visible
                refreshAllData();
                
                // If a room was previously selected, refresh its data
                if (lastSelectedRoomNumber != -1) {
                    updateSelectedRoomData();
                }
            }
        });
    }

    private void initComponents() {
        setSize(900, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        setResizable(true);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Rooms table
        String[] roomColumns = {"Room Number", "Type", "Price", "Status", "Rating"};
        roomsTableModel = new DefaultTableModel(roomColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomsTable = new JTable(roomsTableModel);
        roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomsTable.setPreferredScrollableViewportSize(new Dimension(350, 400));
        JScrollPane roomsScrollPane = new JScrollPane(roomsTable);
        
        // Tabbed pane for bookings and comments
        tabbedPane = new JTabbedPane();
        
        // Bookings table
        String[] bookingColumns = {"Booking ID", "Customer", "Check-in", "Check-out", "Status"};
        bookingsTableModel = new DefaultTableModel(bookingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(bookingsTableModel);
        bookingsTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        JScrollPane bookingsScrollPane = new JScrollPane(bookingsTable);
        
        // Comments table
        String[] commentColumns = {"Customer", "Rating", "Comment", "Date"};
        commentsTableModel = new DefaultTableModel(commentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        commentsTable = new JTable(commentsTableModel);
        commentsTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        commentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane commentsScrollPane = new JScrollPane(commentsTable);
        
        // Comment management buttons
        addCommentButton = new JButton("Add Comment");
        addCommentButton.setIcon(icons[0]);
        addCommentButton.setHorizontalTextPosition(SwingConstants.LEFT);
        addCommentButton.setVerticalTextPosition(SwingConstants.CENTER);
        addCommentButton.setHorizontalAlignment(SwingConstants.CENTER);
        addCommentButton.setIconTextGap(10);
        addCommentButton.addActionListener(this);
        StyleConfig.applyStyle(addCommentButton);
        addCommentButton.setEnabled(false); // Disabled until a room is selected
        
        editCommentButton = new JButton("Edit Comment");
        editCommentButton.setIcon(icons[2]);
        editCommentButton.setHorizontalTextPosition(SwingConstants.LEFT);
        editCommentButton.setVerticalTextPosition(SwingConstants.CENTER);
        editCommentButton.setHorizontalAlignment(SwingConstants.CENTER);
        editCommentButton.setIconTextGap(10);
        editCommentButton.addActionListener(this);
        StyleConfig.applyStyle(editCommentButton);
        editCommentButton.setEnabled(false); // Disabled until a comment is selected
        
        deleteCommentButton = new JButton("Delete Comment");
        deleteCommentButton.setIcon(icons[3]);
        deleteCommentButton.setHorizontalTextPosition(SwingConstants.LEFT);
        deleteCommentButton.setVerticalTextPosition(SwingConstants.CENTER);
        deleteCommentButton.setHorizontalAlignment(SwingConstants.CENTER);
        deleteCommentButton.setIconTextGap(10);
        deleteCommentButton.addActionListener(this);
        StyleConfig.applyStyle(deleteCommentButton);
        deleteCommentButton.setEnabled(false); // Disabled until a comment is selected
        
        // Comments panel with buttons
        JPanel commentsPanel = new JPanel(new BorderLayout(5, 5));
        commentsPanel.add(commentsScrollPane, BorderLayout.CENTER);
        
        JPanel commentButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        commentButtonPanel.add(addCommentButton);
        commentButtonPanel.add(editCommentButton);
        commentButtonPanel.add(deleteCommentButton);
        commentsPanel.add(commentButtonPanel, BorderLayout.SOUTH);
        
        // Rating label
        ratingLabel = new JLabel("Select a room to see ratings and comments");
        ratingLabel.setHorizontalAlignment(JLabel.CENTER);
        StyleConfig.applyStyle(ratingLabel);
        
        // Add tabs
        tabbedPane.addTab("Bookings", bookingsScrollPane);
        tabbedPane.addTab("Comments", commentsPanel);

        // Add listeners
        roomsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = roomsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
                    lastSelectedRoomNumber = roomNumber;
                    Room room = getRoomByNumber(roomNumber);
                    
                    loadBookingsForRoom(roomNumber);
                    loadCommentsForRoom(room.getRoomId());
                    updateRatingDisplay(room.getRoomId());
                    
                    // Check if customer has booked this room before enabling the comment button
                    updateCommentButtonState(roomNumber);
                    
                    // Reset comment management buttons
                    editCommentButton.setEnabled(false);
                    deleteCommentButton.setEnabled(false);
                }
            }
        });
        
        // Add listener for comments table selection
        commentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCommentManagementButtons();
            }
        });

        // Left panel with rooms and rating
        JPanel leftPanel = new JPanel(new BorderLayout(5, 10));
        leftPanel.add(roomsScrollPane, BorderLayout.CENTER);
        leftPanel.add(ratingLabel, BorderLayout.SOUTH);
        
        // Split panel layout
        JPanel splitPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        splitPanel.add(leftPanel);
        splitPanel.add(tabbedPane);
        
        mainPanel.add(splitPanel, BorderLayout.CENTER);

        // Close button
        closeButton = new JButton("Cancel");
        closeButton.addActionListener(this);
        closeButton.setIcon(icons[1]);
        closeButton.setHorizontalTextPosition(SwingConstants.LEFT);  // text at left
        closeButton.setVerticalTextPosition(SwingConstants.CENTER);  // center vertically
        closeButton.setHorizontalAlignment(SwingConstants.CENTER);   // overall alignment
        closeButton.setIconTextGap(10);
        StyleConfig.applyAccentStyle(closeButton);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        // Title
        JLabel titleLabel = new JLabel("All Rooms and Booking Details", JLabel.CENTER);
        StyleConfig.applyTitleStyle(titleLabel);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        StyleConfig.applyStyle(titlePanel);

        // Add to dialog
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Load rooms data
        loadRoomsData();
    }
    
    // Update comment management buttons based on selection
    private void updateCommentManagementButtons() {
        int selectedRow = commentsTable.getSelectedRow();
        if (selectedRow != -1) {
            // Check if the comment belongs to the current customer
            String customerName = (String) commentsTableModel.getValueAt(selectedRow, 0);
            Comment comment = commentRowMap.get(selectedRow);
            
            if (comment != null && getParent() instanceof CustomerDashboard) {
                CustomerDashboard dashboard = (CustomerDashboard) getParent();
                Customer customer = dashboard.getCustomer();
                
                // Enable edit/delete only if this is the customer's comment
                boolean isCustomerComment = comment.getCustomerId().equals(customer.getUserId());
                editCommentButton.setEnabled(isCustomerComment);
                deleteCommentButton.setEnabled(isCustomerComment);
            } else {
                editCommentButton.setEnabled(false);
                deleteCommentButton.setEnabled(false);
            }
        } else {
            editCommentButton.setEnabled(false);
            deleteCommentButton.setEnabled(false);
        }
    }
    
    // Method to refresh all data in the dialog
    public void refreshAllData() {
        loadRoomsData();
    }
    
    // Update the data for the selected room
    private void updateSelectedRoomData() {
        int roomNumber = lastSelectedRoomNumber;
        if (roomNumber != -1) {
            Room room = getRoomByNumber(roomNumber);
            if (room != null) {
                loadBookingsForRoom(roomNumber);
                loadCommentsForRoom(room.getRoomId());
                updateRatingDisplay(room.getRoomId());
                updateCommentButtonState(roomNumber);
            }
        }
    }
    
    // Update the comment button state based on booking status
    private void updateCommentButtonState(int roomNumber) {
        Customer customer = null;
        if (getParent() instanceof CustomerDashboard) {
            CustomerDashboard dashboard = (CustomerDashboard) getParent();
            customer = dashboard.getCustomer();
            
            // Only enable the button if customer has booked this room
            addCommentButton.setEnabled(hasCustomerBookedRoom(customer, roomNumber));
        } else {
            addCommentButton.setEnabled(false);
        }
    }

    private void loadRoomsData() {
        roomsTableModel.setRowCount(0);
        Room[] allRooms = hotelreservationsystem.HotelReservationSystem.getRooms();
        int roomCount = hotelreservationsystem.HotelReservationSystem.getRoomCount();
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        for (int i = 0; i < roomCount; i++) {
            Room room = allRooms[i];
            if (room != null) {
                double avgRating = commentDAO.getAverageRatingForRoom(room.getRoomId());
                String ratingStr = avgRating > 0 ? df.format(avgRating) + " ★" : "No ratings";
                
                Object[] rowData = {
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPricePerNight(),
                    room.isAvailable() ? "Available" : "Booked",
                    ratingStr
                };
                roomsTableModel.addRow(rowData);
            }
        }
    }

    private void loadBookingsForRoom(int roomNumber) {
        bookingsTableModel.setRowCount(0);
        Booking[] bookings = bookingDAO.getBookingsByRoomNumber(roomNumber);
        for (Booking booking : bookings) {
            if (booking != null) {
                Object[] rowData = {
                    booking.getBookingId(),
                    booking.getCustomer().getFullName(),
                    dateFormat.format(booking.getCheckInDate()),
                    dateFormat.format(booking.getCheckOutDate()),
                    booking.isCancelled() ? "Cancelled" : (booking.isPaid() ? "Paid" : "Pending Payment")
                };
                bookingsTableModel.addRow(rowData);
            }
        }
    }
    
    private void loadCommentsForRoom(int roomId) {
        commentsTableModel.setRowCount(0);
        commentRowMap.clear(); // Clear the mapping
        Comment[] comments = commentDAO.getCommentsByRoom(roomId);
        
        for (int i = 0; i < comments.length; i++) {
            Comment comment = comments[i];
            if (comment != null) {
                // Generate stars for the rating
                String stars = "";
                for (int j = 0; j < comment.getRating(); j++) {
                    stars += "★";
                }
                
                Object[] rowData = {
                    comment.getCustomerName(),
                    stars,
                    comment.getComment(),
                    dateFormat.format(comment.getCommentDate())
                };
                commentsTableModel.addRow(rowData);
                
                // Store the comment in our map
                commentRowMap.put(commentsTableModel.getRowCount() - 1, comment);
            }
        }
        
        // Reset comment management buttons
        editCommentButton.setEnabled(false);
        deleteCommentButton.setEnabled(false);
    }
    
    private void updateRatingDisplay(int roomId) {
        double avgRating = commentDAO.getAverageRatingForRoom(roomId);
        DecimalFormat df = new DecimalFormat("#.##");
        
        if (avgRating > 0) {
            // Generate stars for visual display
            String stars = "";
            for (int i = 0; i < Math.round(avgRating); i++) {
                stars += "★";
            }
            
            ratingLabel.setText("Average Rating: " + df.format(avgRating) + " " + stars);
        } else {
            ratingLabel.setText("No ratings yet for this room");
        }
    }
    
    private Room getRoomByNumber(int roomNumber) {
        Room[] allRooms = hotelreservationsystem.HotelReservationSystem.getRooms();
        int roomCount = hotelreservationsystem.HotelReservationSystem.getRoomCount();
        
        for (int i = 0; i < roomCount; i++) {
            if (allRooms[i] != null && allRooms[i].getRoomNumber() == roomNumber) {
                return allRooms[i];
            }
        }
        
        return null;
    }
    
    // Check if a customer has booked a specific room
    private boolean hasCustomerBookedRoom(Customer customer, int roomNumber) {
        if (customer == null) {
            return false;
        }
        
        Booking[] bookings = bookingDAO.getBookingsByCustomer(customer);
        for (Booking booking : bookings) {
            if (booking != null && booking.getRoom().getRoomNumber() == roomNumber) {
                return true;
            }
        }
        return false;
    }
    
    // Method to refresh comments (called from CommentForm)
    public void refreshComments() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow != -1) {
            int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
            Room room = getRoomByNumber(roomNumber);
            
            loadCommentsForRoom(room.getRoomId());
            updateRatingDisplay(room.getRoomId());
            loadRoomsData(); // Refresh average ratings in the room table
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            dispose();
        } else if (e.getSource() == addCommentButton) {
            openCommentForm();
        } else if (e.getSource() == editCommentButton) {
            editSelectedComment();
        } else if (e.getSource() == deleteCommentButton) {
            deleteSelectedComment();
        }
    }
    
    // Method to edit the selected comment
    private void editSelectedComment() {
        int selectedRow = commentsTable.getSelectedRow();
        if (selectedRow != -1) {
            Comment selectedComment = commentRowMap.get(selectedRow);
            if (selectedComment != null) {
                int roomNumber = lastSelectedRoomNumber;
                Room room = getRoomByNumber(roomNumber);
                
                if (getParent() instanceof CustomerDashboard) {
                    CustomerDashboard dashboard = (CustomerDashboard) getParent();
                    Customer customer = dashboard.getCustomer();
                    
                    // Check if this is the customer's comment
                    if (selectedComment.getCustomerId().equals(customer.getUserId())) {
                        // Open edit form
                        JFrame owner = (JFrame) this.getOwner();
                        CommentForm commentForm = new CommentForm(owner, customer, room, selectedComment);
                        commentForm.setVisible(true);
                        
                        // After form closes, refresh comments
                        refreshComments();
                    } else {
                        MessageDialog.showWarning(this, "Cannot Edit Comment", "You can only edit your own comments.");
                    }
                }
            }
        }
    }
    
    // Method to delete the selected comment
    private void deleteSelectedComment() {
        int selectedRow = commentsTable.getSelectedRow();
        if (selectedRow != -1) {
            Comment selectedComment = commentRowMap.get(selectedRow);
            if (selectedComment != null) {
                if (getParent() instanceof CustomerDashboard) {
                    CustomerDashboard dashboard = (CustomerDashboard) getParent();
                    Customer customer = dashboard.getCustomer();
                    
                    // Check if this is the customer's comment
                    if (selectedComment.getCustomerId().equals(customer.getUserId())) {
                        // Confirm deletion
                        int response = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this comment?",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                        );
                        
                        if (response == JOptionPane.YES_OPTION) {
                            // Delete the comment
                            boolean success = commentDAO.deleteComment(selectedComment.getCommentId());
                            if (success) {
                                // Refresh comments
                                refreshComments();
                                MessageDialog.showInformation(this, "Success", "Comment deleted successfully.");
                            } else {
                                MessageDialog.showError(this, "Error", "Failed to delete comment.");
                            }
                        }
                    } else {
                        MessageDialog.showWarning(this, "Cannot Delete Comment", "You can only delete your own comments.");
                    }
                }
            }
        }
    }
    
    // This method is used to refresh the dialog when it's made visible again
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            // Refresh data each time dialog is shown
            refreshAllData();
            
            // If a room was previously selected, refresh its data
            if (lastSelectedRoomNumber != -1) {
                updateSelectedRoomData();
            }
        }
        super.setVisible(visible);
    }
    
    private void openCommentForm() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow != -1) {
            int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
            Room room = getRoomByNumber(roomNumber);
            
            // Get customer from parent (CustomerDashboard)
            if (getParent() instanceof CustomerDashboard) {
                CustomerDashboard dashboard = (CustomerDashboard) getParent();
                Customer customer = dashboard.getCustomer();
                
                // Check if customer has booked this room
                if (hasCustomerBookedRoom(customer, roomNumber)) {
                    // Get the JFrame owner of this dialog
                    JFrame owner = (JFrame) this.getOwner();
                    CommentForm commentForm = new CommentForm(owner, customer, room);
                    commentForm.setVisible(true);
                } else {
                    MessageDialog.showWarning(this, "Cannot Add Comment", "You can only comment on rooms you have booked.");
                }
            } else {
                MessageDialog.showWarning(this, "Warning", "You must be logged in as a customer to add comments.");
            }
        }
    }
} 