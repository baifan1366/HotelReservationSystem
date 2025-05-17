/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;
import java.io.Serializable;
/**
 *
 * @author user
 */

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String name;
    private String password;
    private String email;
    
    // Constructor
    public User(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "User [userId=" + userId + ", name=" + name + ", email=" + email + "]";
    }
}
