/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.dao;

import hotelreservationsystem.HotelReservationSystem;
import hotelreservationsystem.model.Room;

/**
 *
 * @author user
 */
public class RoomDAO {
    
    // Get all available rooms
    public Room[] getAvailableRooms() {
        Room[] allRooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        // Count available rooms
        int availableCount = 0;
        for (int i = 0; i < roomCount; i++) {
            if (allRooms[i].isAvailable()) {
                availableCount++;
            }
        }
        
        // Create array of available rooms
        Room[] availableRooms = new Room[availableCount];
        int index = 0;
        for (int i = 0; i < roomCount; i++) {
            if (allRooms[i].isAvailable()) {
                availableRooms[index++] = allRooms[i];
            }
        }
        
        return availableRooms;
    }
    
    // Find room by room number
    public Room findRoomByNumber(int roomNumber) {
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].getRoomNumber() == roomNumber) {
                return rooms[i];
            }
        }
        return null;
    }
    
    // Add a new room
    public boolean addRoom(Room room) {
        // Check if room number already exists
        if (findRoomByNumber(room.getRoomNumber()) != null) {
            return false;
        }
        
        HotelReservationSystem.addRoom(room);
        return true;
    }
    
    // Update room information
    public boolean updateRoom(Room room) {
        Room[] rooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].getRoomNumber() == room.getRoomNumber()) {
                rooms[i] = room;
                new FileManager().saveRooms(rooms);
                return true;
            }
        }
        return false;
    }
    
    // Get rooms by type
    public Room[] getRoomsByType(String type) {
        Room[] allRooms = HotelReservationSystem.getRooms();
        int roomCount = HotelReservationSystem.getRoomCount();
        
        // Count rooms of specified type
        int typeCount = 0;
        for (int i = 0; i < roomCount; i++) {
            if (allRooms[i].getType().equals(type)) {
                typeCount++;
            }
        }
        
        // Create array of rooms with specified type
        Room[] typeRooms = new Room[typeCount];
        int index = 0;
        for (int i = 0; i < roomCount; i++) {
            if (allRooms[i].getType().equals(type)) {
                typeRooms[index++] = allRooms[i];
            }
        }
        
        return typeRooms;
    }
}
