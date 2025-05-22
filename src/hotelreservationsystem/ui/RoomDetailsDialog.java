package hotelreservationsystem.ui;

import hotelreservationsystem.dao.BookingDAO;
import hotelreservationsystem.dao.CommentDAO;
import hotelreservationsystem.dao.RoomDAO;
import hotelreservationsystem.model.Booking;
import hotelreservationsystem.model.Comment;
import hotelreservationsystem.model.Room;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;

public class RoomDetailsDialog extends JDialog implements ActionListener {
    private JTable roomsTable;
    private DefaultTableModel roomsTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    private JTable commentsTable;
    private DefaultTableModel commentsTableModel;
    private JButton closeButton;
    private JButton addCommentButton;
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    private CommentDAO commentDAO;
    private JTabbedPane tabbedPane;
    private JLabel ratingLabel;
    private SimpleDateFormat dateFormat;

    public RoomDetailsDialog(JFrame parent) {
        super(parent, "All Rooms and Booking Details", true);
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
        this.commentDAO = new CommentDAO();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        initComponents();
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
        JScrollPane commentsScrollPane = new JScrollPane(commentsTable);
        
        // Add comment button
        addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(this);
        addCommentButton.setEnabled(false); // Disabled until a room is selected
        
        // Comments panel with button
        JPanel commentsPanel = new JPanel(new BorderLayout(5, 5));
        commentsPanel.add(commentsScrollPane, BorderLayout.CENTER);
        
        JPanel commentButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        commentButtonPanel.add(addCommentButton);
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
                    Room room = getRoomByNumber(roomNumber);
                    
                    loadBookingsForRoom(roomNumber);
                    loadCommentsForRoom(room.getRoomId());
                    updateRatingDisplay(room.getRoomId());
                    
                    // Enable add comment button
                    addCommentButton.setEnabled(true);
                }
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
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
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
                    booking.getCustomer().getName(),
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
        Comment[] comments = commentDAO.getCommentsByRoom(roomId);
        
        for (Comment comment : comments) {
            if (comment != null) {
                // Generate stars for the rating
                String stars = "";
                for (int i = 0; i < comment.getRating(); i++) {
                    stars += "★";
                }
                
                Object[] rowData = {
                    comment.getCustomerName(),
                    stars,
                    comment.getComment(),
                    dateFormat.format(comment.getCommentDate())
                };
                commentsTableModel.addRow(rowData);
            }
        }
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
        }
    }
    
    private void openCommentForm() {
        int selectedRow = roomsTable.getSelectedRow();
        if (selectedRow != -1) {
            int roomNumber = (int) roomsTableModel.getValueAt(selectedRow, 0);
            Room room = getRoomByNumber(roomNumber);
            
            // Get customer from parent (CustomerDashboard)
            if (getParent() instanceof CustomerDashboard) {
                CustomerDashboard dashboard = (CustomerDashboard) getParent();
                // Get the JFrame owner of this dialog
                JFrame owner = (JFrame) this.getOwner();
                CommentForm commentForm = new CommentForm(owner, dashboard.getCustomer(), room);
                commentForm.setVisible(true);
            } else {
                MessageDialog.showWarning(this, "Warning", "You must be logged in as a customer to add comments.");
            }
        }
    }
} 