/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;
import java.io.Serializable;
import hotelreservationsystem.util.UUIDUtil;
/**
 *
 * @author user
 */

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String fullName;
    private String username;
    private String password;
    private String email;
    
    // Constructor
    public User(String fullName, String username, String password, String email) {
        this.userId = "user-" + UUIDUtil.generateShortUUID();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Abstract method
    public abstract boolean login(String userId, String password);
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", fullName=" + fullName + ", username=" + username + ", email=" + email + "]";
    }
}
