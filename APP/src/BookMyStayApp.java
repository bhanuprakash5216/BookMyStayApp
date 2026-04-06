import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrementRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingService {

    private Queue<Reservation> queue;
    private InventoryService inventory;
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    private String generateRoomId(String roomType) {
        return roomType + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    public void processBookings() {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();
            String type = r.getRoomType();

            System.out.println("\nProcessing: " + r.getGuestName());

            if (inventory.getAvailability(type) > 0) {

                allocatedRooms.putIfAbsent(type, new HashSet<>());

                String roomId;

                do {
                    roomId = generateRoomId(type);
                } while (allocatedRooms.get(type).contains(roomId));

                allocatedRooms.get(type).add(roomId);
                inventory.decrementRoom(type);

                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed (No rooms available)");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Bhanu", "Single"));
        queue.offer(new Reservation("Rahul", "Single"));
        queue.offer(new Reservation("Anjali", "Single"));
        queue.offer(new Reservation("Kiran", "Double"));

        BookingService service = new BookingService(queue, inventory);
        service.processBookings();
    }
}