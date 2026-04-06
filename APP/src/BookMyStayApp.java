import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
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

    public void incrementRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingHistory {
    private Map<String, Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new HashMap<>();
    }

    public void addBooking(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String id) {
        return confirmedBookings.get(id);
    }

    public void removeBooking(String id) {
        confirmedBookings.remove(id);
    }

    public boolean exists(String id) {
        return confirmedBookings.containsKey(id);
    }
}

class CancellationService {

    private InventoryService inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(InventoryService inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        Reservation r = history.getBooking(reservationId);

        String releasedRoomId = r.getRoomType() + "-" + reservationId;
        rollbackStack.push(releasedRoomId);

        inventory.incrementRoom(r.getRoomType());
        history.removeBooking(reservationId);

        System.out.println("Booking Cancelled: " + reservationId);
        System.out.println("Released Room ID: " + releasedRoomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);

        BookingHistory history = new BookingHistory();
        history.addBooking(new Reservation("R101", "Single"));
        history.addBooking(new Reservation("R102", "Single"));

        CancellationService service = new CancellationService(inventory, history);

        service.cancelBooking("R101");
        service.cancelBooking("R999");

        service.showRollbackStack();

        System.out.println("\nUpdated Inventory (Single): " +
                inventory.getAvailability("Single"));
    }
}