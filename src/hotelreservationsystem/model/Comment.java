package hotelreservationsystem.model;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int commentId;
    private int roomId;
    private String customerId;
    private int rating; // Rating from 1-5
    private String comment;
    private Date commentDate;
    private String customerName; // To display name without loading full customer object
    
    // Constructor
    public Comment(int commentId, int roomId, String customerId, int rating, String comment, Date commentDate, String customerName) {
        this.commentId = commentId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.commentDate = commentDate;
        this.customerName = customerName;
    }
    
    // Getters and setters
    public int getCommentId() {
        return commentId;
    }
    
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getCommentDate() {
        return commentDate;
    }
    
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", roomId=" + roomId + 
               ", customer=" + customerName + ", rating=" + rating + 
               ", comment=" + comment + ", date=" + commentDate + "]";
    }
} 