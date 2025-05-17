/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.model.*;
import java.io.*;

public class FileManager {
    private static final String ROOMS_FILE = "rooms.dat";
    private static final String USERS_FILE = "users.dat";
    private static final String BOOKINGS_FILE = "bookings.dat";
    private static final String PAYMENTS_FILE = "payments.dat";
    
    // Save rooms to binary file
    public void saveRooms(Room[] rooms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }
    
    // Load rooms from binary file
    public Room[] loadRooms() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ROOMS_FILE))) {
            return (Room[]) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Rooms file not found. Creating new file on save.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
        return null;
    }
    
    // Save users to binary file
    public void saveUsers(User[] users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    // Load users from binary file
    public User[] loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(USERS_FILE))) {
            return (User[]) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Creating new file on save.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return null;
    }
    
    // Save bookings to binary file
    public void saveBookings(Booking[] bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }
    
    // Load bookings from binary file
    public Booking[] loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(BOOKINGS_FILE))) {
            return (Booking[]) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Bookings file not found. Creating new file on save.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
        return null;
    }
    
    // Save payments to binary file
    public void savePayments(Payment[] payments) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PAYMENTS_FILE))) {
            oos.writeObject(payments);
        } catch (IOException e) {
            System.err.println("Error saving payments: " + e.getMessage());
        }
    }
    
    // Load payments from binary file
    public Payment[] loadPayments() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(PAYMENTS_FILE))) {
            return (Payment[]) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Payments file not found. Creating new file on save.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading payments: " + e.getMessage());
        }
        return null;
    }
}
