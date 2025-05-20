package hotelreservationsystem.dao;

import hotelreservationsystem.model.Comment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDAO {
    private static final String COMMENTS_FILE = "comments.dat";
    private static ArrayList<Comment> comments = new ArrayList<>();
    private static int nextId = 1;
    
    // Initialize comments data
    static {
        loadComments();
    }
    
    // Save comments to file
    private static void saveComments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMMENTS_FILE))) {
            oos.writeObject(comments);
            oos.writeInt(nextId);
        } catch (Exception e) {
            System.err.println("Error saving comments: " + e.getMessage());
        }
    }
    
    // Load comments from file
    private static void loadComments() {
        File file = new File(COMMENTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                comments = (ArrayList<Comment>) ois.readObject();
                nextId = ois.readInt();
            } catch (Exception e) {
                System.err.println("Error loading comments: " + e.getMessage());
                comments = new ArrayList<>();
            }
        } else {
            comments = new ArrayList<>();
        }
    }
    
    // Add a new comment
    public boolean addComment(int roomId, String customerId, int rating, String comment, String customerName) {
        try {
            Comment newComment = new Comment(nextId++, roomId, customerId, rating, comment, new Date(), customerName);
            comments.add(newComment);
            saveComments();
            return true;
        } catch (Exception e) {
            System.err.println("Error adding comment: " + e.getMessage());
            return false;
        }
    }
    
    // Get all comments for a room
    public Comment[] getCommentsByRoom(int roomId) {
        List<Comment> roomComments = new ArrayList<>();
        
        for (Comment comment : comments) {
            if (comment.getRoomId() == roomId) {
                roomComments.add(comment);
            }
        }
        
        return roomComments.toArray(new Comment[0]);
    }
    
    // Get all comments by a customer
    public Comment[] getCommentsByCustomer(int customerId) {
        List<Comment> customerComments = new ArrayList<>();
        
        for (Comment comment : comments) {
            if (comment.getCustomerId().equals(customerId)) {
                customerComments.add(comment);
            }
        }
        
        return customerComments.toArray(new Comment[0]);
    }
    
    // Get average rating for a room
    public double getAverageRatingForRoom(int roomId) {
        Comment[] roomComments = getCommentsByRoom(roomId);
        
        if (roomComments.length == 0) {
            return 0;
        }
        
        double sum = 0;
        for (Comment comment : roomComments) {
            sum += comment.getRating();
        }
        
        return sum / roomComments.length;
    }
    
    // Update a comment
    public boolean updateComment(Comment comment) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getCommentId() == comment.getCommentId()) {
                comments.set(i, comment);
                saveComments();
                return true;
            }
        }
        return false;
    }
    
    // Delete a comment
    public boolean deleteComment(int commentId) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getCommentId() == commentId) {
                comments.remove(i);
                saveComments();
                return true;
            }
        }
        return false;
    }
} 