/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */

public class Admin extends User {
    private static final long serialVersionUID = 1L;
    
    private String adminId;
    private String role;
    
    // Constructor
    public Admin(String userId, String name, String password, String email, String adminId, String role) {
        super(userId, name, password, email);
        this.adminId = adminId;
        this.role = role;
    }
    
    // Method to generate reports
    public Report generateReport(String reportType) {
        // Report generation logic to be implemented
        return new Report(reportType);
    }
    
    @Override
    public boolean login(String userId, String password) {
        // Admin login logic
        return this.getUserId().equals(userId) && this.getPassword().equals(password);
    }
    
    // Getters and setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Admin [adminId=" + adminId + ", role=" + role + "]";
    }
}