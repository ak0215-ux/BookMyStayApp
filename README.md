# UC – Centralized Room Inventory Using HashMap

## Goal

Introduce centralized inventory management by replacing scattered availability variables with a single data structure using `HashMap`. This demonstrates how a collection can manage system state more efficiently and consistently.

## Actor

RoomInventory – responsible for managing and exposing room availability across the system.

## Flow

1. The system initializes the inventory component.
2. Room types are registered with their available counts.
3. Availability is stored and retrieved from a centralized `HashMap`.
4. Updates to availability are performed through controlled methods.
5. The current inventory state is displayed when requested.

## Key Concepts

**Problem of Scattered State**
In earlier implementations, availability was stored in separate variables. This approach becomes difficult to maintain as the system grows, leading to inconsistent updates and duplication.

**HashMap**
`HashMap<String, Integer>` maps room types to the number of available rooms. It allows efficient storage and retrieval of inventory data using a logical key.

**O(1) Lookup**
HashMap provides average constant-time performance for `get()` and `put()` operations, making it suitable for frequent availability checks.

**Single Source of Truth**
All room availability information is stored in one centralized structure. This ensures consistent data across the application.

**Encapsulation of Inventory Logic**
Inventory operations such as adding, retrieving, and updating room availability are contained within the `RoomInventory` class. Other parts of the system interact with the inventory only through defined methods.

**Separation of Concerns**
The inventory component manages room availability, while room attributes such as price, size, and type remain part of the room domain model.

**Scalability**
New room types can be added simply by inserting new entries into the `HashMap`, without modifying existing application logic.

## Data Structure

HashMap<String, Integer>

## Key Features

* Centralized management of room availability
* Efficient lookup and updates using HashMap
* Encapsulated inventory management methods
* Improved scalability and maintainability
