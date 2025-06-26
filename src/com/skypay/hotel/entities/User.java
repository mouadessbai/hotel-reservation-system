package com.skypay.hotel.entities;

public class User {
    private int userId;
    private int balance;
    private long creationTimestamp;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
        this.creationTimestamp = System.currentTimeMillis();
    }

    public boolean deductBalance(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean hasSufficientBalance(int amount) {
        return balance >= amount;
    }

    public int getUserId() {
        return userId;
    }
    public int getBalance() {
        return balance;
    }
    public long getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}