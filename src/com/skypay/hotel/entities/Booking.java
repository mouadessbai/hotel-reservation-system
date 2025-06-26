package com.skypay.hotel.entities;

import java.util.Date;
import java.util.Calendar;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    private long creationTimestamp;
    private RoomType roomType;
    private int pricePerNight;
    private int userBalanceAtBooking;
    private static int nextBookingId = 1;

    public Booking(User user, Room room, Date checkIn, Date checkOut, int totalCost) {
        this.bookingId = nextBookingId++;
        this.userId = user.getUserId();
        this.roomNumber = room.getRoomNumber();
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalCost = totalCost;
        this.creationTimestamp = System.currentTimeMillis();
        this.roomType = room.getRoomType();
        this.pricePerNight = room.getPricePerNight();
        this.userBalanceAtBooking = user.getBalance();
    }

    public boolean overlapsWithPeriod(Date otherCheckIn, Date otherCheckOut) {
        Date thisCheckIn = normalizeDate(this.checkIn);
        Date thisCheckOut = normalizeDate(this.checkOut);
        Date otherCheckInNorm = normalizeDate(otherCheckIn);
        Date otherCheckOutNorm = normalizeDate(otherCheckOut);
        return !(thisCheckOut.before(otherCheckInNorm) ||
                thisCheckIn.after(otherCheckOutNorm) ||
                thisCheckOut.equals(otherCheckInNorm) ||
                thisCheckIn.equals(otherCheckOutNorm));
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

    public int getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public int getRoomNumber() { return roomNumber; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public int getTotalCost() { return totalCost; }
    public long getCreationTimestamp() { return creationTimestamp; }
    public RoomType getRoomType() { return roomType; }
    public int getPricePerNight() { return pricePerNight; }
    public int getUserBalanceAtBooking() { return userBalanceAtBooking; }
}