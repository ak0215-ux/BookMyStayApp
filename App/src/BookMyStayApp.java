import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Demonstrates centralized room inventory management
 * using a HashMap to store and manage room availability.
 *
 * @author ABHI
 * @version 1.0
 */

// Inventory class responsible for managing room availability
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with available count
    void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Retrieve availability for a specific room type
    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    // Display full inventory
    void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

// Application class
class HotelInventoryApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types
        System.out.print("Enter available Single Rooms: ");
        inventory.addRoomType("Single Room", sc.nextInt());

        System.out.print("Enter available Double Rooms: ");
        inventory.addRoomType("Double Room", sc.nextInt());

        System.out.print("Enter available Suite Rooms: ");
        inventory.addRoomType("Suite Room", sc.nextInt());

        // Display inventory
        inventory.displayInventory();

        // Update availability
        sc.nextLine(); // clear buffer
        System.out.print("\nEnter room type to update: ");
        String roomType = sc.nextLine();

        System.out.print("Enter new availability count: ");
        int newCount = sc.nextInt();

        inventory.updateAvailability(roomType, newCount);

        // Display updated inventory
        inventory.displayInventory();

        sc.close();
    }
}