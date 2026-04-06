import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
        return inventory.getOrDefault(type, -1);
    }

    public void decrementRoom(String type) throws InvalidBookingException {
        int count = getAvailability(type);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
        inventory.put(type, count - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

class BookingValidator {

    public static void validate(Reservation r, InventoryService inventory) throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation r) {
        try {
            BookingValidator.validate(r, inventory);
            inventory.decrementRoom(r.getRoomType());

            System.out.println("Booking Successful for " + r.getGuestName() +
                    " | Room: " + r.getRoomType());

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        BookingService service = new BookingService(inventory);

        service.confirmBooking(new Reservation("Bhanu", "Single"));
        service.confirmBooking(new Reservation("", "Single"));
        service.confirmBooking(new Reservation("Rahul", "Suite"));
        service.confirmBooking(new Reservation("Anjali", "Double"));
    }
}