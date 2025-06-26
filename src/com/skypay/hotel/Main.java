package com.skypay.hotel;

import com.skypay.hotel.entities.RoomType;
import com.skypay.hotel.services.ReservationService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            ReservationService service = new ReservationService();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("===== HOTEL RESERVATION SYSTEM TEST =====\n");

            System.out.println("--- Creating Rooms ---");
            service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
            service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
            service.setRoom(3, RoomType.MASTER_SUITE, 3000);

            System.out.println("\n--- Creating Users ---");
            service.setUser(1, 5000);
            service.setUser(2, 10000);

            System.out.println("\n--- Test 1: User 1 booking Room 2 (7 nights) ---");
            Date date1_in = sdf.parse("30/06/2026");
            Date date1_out = sdf.parse("07/07/2026");
            service.bookRoom(1, 2, date1_in, date1_out);

            System.out.println("\n--- Test 2: User 1 booking Room 2 (invalid dates) ---");
            Date date2_in = sdf.parse("07/07/2026");
            Date date2_out = sdf.parse("30/06/2026");
            service.bookRoom(1, 2, date2_in, date2_out);

            System.out.println("\n--- Test 3: User 1 booking Room 1 (1 night) ---");
            Date date3_in = sdf.parse("07/07/2026");
            Date date3_out = sdf.parse("08/07/2026");
            service.bookRoom(1, 1, date3_in, date3_out);

            System.out.println("\n--- Test 4: User 2 booking Room 1 (2 nights) ---");
            Date date4_in = sdf.parse("07/07/2026");
            Date date4_out = sdf.parse("09/07/2026");
            service.bookRoom(2, 1, date4_in, date4_out);

            System.out.println("\n--- Test 5: User 2 booking Room 3 (1 night) ---");
            Date date5_in = sdf.parse("07/07/2026");
            Date date5_out = sdf.parse("08/07/2026");
            service.bookRoom(2, 3, date5_in, date5_out);

            System.out.println("\n--- Updating Room 1 ---");
            service.setRoom(1, RoomType.MASTER_SUITE, 10000);

            System.out.println("\n===== FINAL RESULTS =====");
            service.printAll();
            service.printAllUsers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}