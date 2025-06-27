# Hotel Reservation System
# Hotel Reservation System

A Java implementation of a simplified hotel reservation system that manages rooms, users, and bookings according to specific business rules.

## Features

- Room management with different types (Standard, Junior, Master Suite)
- User accounts with balance tracking
- Booking system with date validation and availability checks
- Historical data preservation for financial integrity
- Chronological reporting of rooms, users, and bookings

## Design Questions Answers

### 1. Suppose we put all the functions inside the same service. Is this the recommended approach ? Please explain
**Not recommended** for production systems. While acceptable for small-scale projects, it violates:
- **Single Responsibility Principle**: Service handles users, rooms, bookings
- **Testability**: Difficult to isolate concerns
- **Scalability**: Tight coupling impedes future extensions

Better approach:
- Separate services: `UserService`, `RoomService`, `BookingService`
- Use dependency injection
- Implement repository layer for data access

### 2. In this design, we chose to have a function setRoom(..) that should not impact the previous bookings. What is another way ? What is your recommendation ? Please explain and justify
**Current approach is correct**: 
- Bookings store room details (type/price) at booking time
- Room updates don't alter historical records
- Preserves data integrity for financial reporting

for a simple system, snapshot approach is perfectly fine it's transparent and low-effort. In larger, data-intensive systems where you need rich auditing or reporting across historical price changes,we go with versioned entities to keep domain data normalized and avoid duplicating fields in every booking.
