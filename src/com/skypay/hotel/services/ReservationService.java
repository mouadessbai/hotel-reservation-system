package com.skypay.hotel.services;

import com.skypay.hotel.entities.Booking;
import com.skypay.hotel.entities.Room;
import com.skypay.hotel.entities.RoomType;
import com.skypay.hotel.entities.User;

import java.util.*;
import java.text.SimpleDateFormat;

public class ReservationService {
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public ReservationService() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            Room existingRoom = findRoomByNumber(roomNumber);

            if (existingRoom != null) {
                existingRoom.setRoomType(roomType);
                existingRoom.setPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully");
            } else {
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully");
            }
        } catch (Exception e) {
            System.err.println("Error in setRoom: " + e.getMessage());
        }
    }

    public void setUser(int userId, int balance) {
        try {
            User existingUser = findUserById(userId);

            if (existingUser != null) {
                existingUser.setBalance(balance);
                System.out.println("User " + userId + " updated successfully");
            } else {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created successfully");
            }
        } catch (Exception e) {
            System.err.println("Error in setUser: " + e.getMessage());
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            if (checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dates cannot be null");
            }

            checkIn = normalizeDate(checkIn);
            checkOut = normalizeDate(checkOut);

            if (!checkOut.after(checkIn)) {
                throw new IllegalArgumentException("Check-out must be after check-in");
            }

            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                throw new IllegalArgumentException("User " + userId + " not found");
            }
            if (room == null) {
                throw new IllegalArgumentException("Room " + roomNumber + " not found");
            }

            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                throw new IllegalStateException("Room " + roomNumber + " not available for selected dates");
            }

            long diffInMillies = checkOut.getTime() - checkIn.getTime();
            int nights = (int) (diffInMillies / (1000 * 60 * 60 * 24));
            int totalCost = nights * room.getPricePerNight();

            if (!user.hasSufficientBalance(totalCost)) {
                throw new IllegalStateException("Insufficient balance. Need: " + totalCost + ", Have: " + user.getBalance());
            }

            Booking booking = new Booking(user, room, checkIn, checkOut, totalCost);
            bookings.add(booking);
            user.deductBalance(totalCost);

            System.out.println("Booking successful! ID: " + booking.getBookingId() +
                    ", Total: " + totalCost + ", New balance: " + user.getBalance());

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    public void printAll() {
        System.out.println("\n========== ALL ROOMS ==========");
        List<Room> sortedRooms = new ArrayList<>(rooms);
        sortedRooms.sort((r1, r2) -> Long.compare(r2.getCreationTimestamp(), r1.getCreationTimestamp()));

        for (Room room : sortedRooms) {
            System.out.println(room);
        }

        System.out.println("\n========== ALL BOOKINGS ==========");
        List<Booking> sortedBookings = new ArrayList<>(bookings);
        sortedBookings.sort((b1, b2) -> Long.compare(b2.getCreationTimestamp(), b1.getCreationTimestamp()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Booking booking : sortedBookings) {
            System.out.println("\nBooking ID: " + booking.getBookingId());
            System.out.println("  User: " + booking.getUserId() + " (Balance at booking: " + booking.getUserBalanceAtBooking() + ")");
            System.out.println("  Room: " + booking.getRoomNumber() + " (" + booking.getRoomType().getDisplayName() +
                    ", " + booking.getPricePerNight() + "/night)");
            System.out.println("  Period: " + sdf.format(booking.getCheckIn()) + " to " + sdf.format(booking.getCheckOut()));
            System.out.println("  Total Cost: " + booking.getTotalCost());
        }
    }

    public void printAllUsers() {
        System.out.println("\n========== ALL USERS ==========");
        List<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort((u1, u2) -> Long.compare(u2.getCreationTimestamp(), u1.getCreationTimestamp()));

        for (User user : sortedUsers) {
            System.out.println(user);
        }
    }

    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (booking.overlapsWithPeriod(checkIn, checkOut)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Date normalizeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}