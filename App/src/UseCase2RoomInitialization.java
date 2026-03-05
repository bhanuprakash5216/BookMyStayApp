/**
 * Book My Stay App
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, and simple availability tracking.
 *
 * @author Lalitya
 * @version 2.1
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 1800);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 3000);
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("      Welcome to Book My Stay");
        System.out.println("   Hotel Booking Management App");
        System.out.println("           Version 2.1");
        System.out.println("===================================\n");

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability Variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display Details
        System.out.println("Room Details & Availability\n");

        single.displayRoomDetails();
        System.out.println("Available : " + singleAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailable + "\n");

        suite.displayRoomDetails();
        System.out.println("Available : " + suiteAvailable + "\n");

    }
}