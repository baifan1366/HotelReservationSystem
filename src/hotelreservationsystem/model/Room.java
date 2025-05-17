/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */

import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int roomId;
    private int roomNumber;
    private String type;  // Single, Double, Suite
    private double price;
    private boolean status;  // true = available, false = booked
    
    // Constructor
    public Room(int roomId, int roomNumber, String type, double price, boolean status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    
    // Method to check if room is available
    public boolean isAvailable() {
        return status;
    }
    
    // Method to set room availability
    public void setAvailable(boolean available) {
        this.status = available;
    }
    
    // Getters and setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public double getPricePerNight() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Room [roomId=" + roomId + ", roomNumber=" + roomNumber + ", type=" + type +
               ", price=" + price + ", status=" + (status ? "Available" : "Booked") + "]";
    }
}
