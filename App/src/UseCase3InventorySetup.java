import java.util.HashMap;

/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates centralized inventory management using HashMap.
 *
 * @author Lalitya
 * @version 3.1
 */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application");
        System.out.println("        Version 3.1");
        System.out.println("=================================\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println("\nUpdating availability for Suite Room...");
        inventory.updateAvailability("Suite Room", 4);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}