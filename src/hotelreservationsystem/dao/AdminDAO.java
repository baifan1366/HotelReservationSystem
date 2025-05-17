/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Admin;
import hotelreservationsystem.model.User;

/**
 *
 * @author user
 */
public class AdminDAO {
    
    // Find admin by userId and password
    public Admin findAdmin(String userId, String password) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Admin && 
                user.getUserId().equals(userId) && 
                user.getPassword().equals(password)) {
                return (Admin) user;
            }
        }
        return null;
    }
    
    // Find admin by userId
    public Admin findAdminByUserId(String userId) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Admin && user.getUserId().equals(userId)) {
                return (Admin) user;
            }
        }
        return null;
    }
    
    // Update admin information
    public boolean updateAdmin(Admin admin) {
        User[] users = HotelReservationSystem.getUsers();
        int userCount = HotelReservationSystem.getUserCount();
        
        for (int i = 0; i < userCount; i++) {
            User user = users[i];
            if (user instanceof Admin && user.getUserId().equals(admin.getUserId())) {
                users[i] = admin;
                new FileManager().saveUsers(users);
                return true;
            }
        }
        return false;
    }
} 