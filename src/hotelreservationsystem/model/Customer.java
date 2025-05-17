/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */

public class Customer extends User {
    private static final long serialVersionUID = 1L;
    
    private String phone;
    private String address;
    
    // Constructor
    public Customer(String userId, String name, String password, String email, String phone, String address) {
        super(userId, name, password, email);
        this.phone = phone;
        this.address = address;
    }
    
    // Method to register a new customer
    public boolean register() {
        // Registration logic will be implemented in CustomerDAO
        return true;
    }
    
    @Override
    public boolean login(String userId, String password) {
        // Login logic will be implemented in CustomerDAO
        return this.getUserId().equals(userId) && this.getPassword().equals(password);
    }
    
    // Getters and setters
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Customer [phone=" + phone + ", address=" + address + "]";
    }
}
