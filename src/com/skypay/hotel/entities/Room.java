package com.skypay.hotel.entities;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int pricePerNight;
    private long creationTimestamp;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.creationTimestamp = System.currentTimeMillis();
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public RoomType getRoomType() {
        return roomType;
    }
    public int getPricePerNight() {
        return pricePerNight;
    }
    public long getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType.getDisplayName() +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}