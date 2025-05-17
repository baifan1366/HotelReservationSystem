/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Customer;
import hotelreservationsystem.model.User;

public class CustomerDAO {
    
    // Find customer by userId and password
    public Customer findCustomer(String userId, String password) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Customer && 
                user.getUserId().equals(userId) && 
                user.getPassword().equals(password)) {
                return (Customer) user;
            }
        }
        return null;
    }
    
    // Find customer by userId
    public Customer findCustomerByUserId(String userId) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Customer && user.getUserId().equals(userId)) {
                return (Customer) user;
            }
        }
        return null;
    }
    
    // Register a new customer
    public boolean registerCustomer(Customer customer) {
        // Check if userId already exists
        if (findCustomerByUserId(customer.getUserId()) != null) {
            return false;
        }
        
        // Add new customer
        HotelReservationSystem.addUser(customer);
        return true;
    }
    
    // Update customer information
    public boolean updateCustomer(Customer customer) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Customer && user.getUserId().equals(customer.getUserId())) {
                users[i] = customer;
                new FileManager().saveUsers(users);
                return true;
            }
        }
        return false;
    }
}
