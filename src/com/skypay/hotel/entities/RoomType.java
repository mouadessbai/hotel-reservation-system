package com.skypay.hotel.entities;

public enum RoomType {
    STANDARD_SUITE("standard"),
    JUNIOR_SUITE("junior"),
    MASTER_SUITE("master");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}