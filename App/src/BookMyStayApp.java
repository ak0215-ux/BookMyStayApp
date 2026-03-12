import java.util.Scanner;

/**
 * Demonstrates abstraction, inheritance, and polymorphism
 * with user-modifiable room details.
 *
 * @author ABHI
 * @version 1.0
 */

// Abstract Room class
abstract class Room {

    String type;
    int beds;
    double size;
    double price;

    Room(String type, int beds, double size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    SingleRoom(int beds, double size, double price) {
        super("Single Room", beds, size, price);
    }
}

// Double Room
class DoubleRoom extends Room {
    DoubleRoom(int beds, double size, double price) {
        super("Double Room", beds, size, price);
    }
}

// Suite Room
class SuiteRoom extends Room {
    SuiteRoom(int beds, double size, double price) {
        super("Suite Room", beds, size, price);
    }
}

// Application class
class HotelRoomApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // User input for Single Room
        System.out.println("Enter details for Single Room");
        System.out.print("Beds: ");
        int sBeds = sc.nextInt();
        System.out.print("Size (sq.ft): ");
        double sSize = sc.nextDouble();
        System.out.print("Price per night: ");
        double sPrice = sc.nextDouble();
        System.out.print("Available rooms: ");
        int singleAvailable = sc.nextInt();

        // User input for Double Room
        System.out.println("\nEnter details for Double Room");
        System.out.print("Beds: ");
        int dBeds = sc.nextInt();
        System.out.print("Size (sq.ft): ");
        double dSize = sc.nextDouble();
        System.out.print("Price per night: ");
        double dPrice = sc.nextDouble();
        System.out.print("Available rooms: ");
        int doubleAvailable = sc.nextInt();

        // User input for Suite Room
        System.out.println("\nEnter details for Suite Room");
        System.out.print("Beds: ");
        int suBeds = sc.nextInt();
        System.out.print("Size (sq.ft): ");
        double suSize = sc.nextDouble();
        System.out.print("Price per night: ");
        double suPrice = sc.nextDouble();
        System.out.print("Available rooms: ");
        int suiteAvailable = sc.nextInt();

        // Create objects (Polymorphism)
        Room single = new SingleRoom(sBeds, sSize, sPrice);
        Room doubleRoom = new DoubleRoom(dBeds, dSize, dPrice);
        Room suite = new SuiteRoom(suBeds, suSize, suPrice);

        System.out.println("\nHotel Room Availability\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);

        sc.close();
    }
}