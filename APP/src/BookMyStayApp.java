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

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingTask implements Runnable {

    private Reservation reservation;
    private InventoryService inventory;

    public BookingTask(Reservation reservation, InventoryService inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        boolean success = inventory.allocateRoom(reservation.getRoomType());

        if (success) {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking SUCCESS for " + reservation.getGuestName());
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking FAILED for " + reservation.getGuestName());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);

        List<Thread> threads = new ArrayList<>();

        threads.add(new Thread(new BookingTask(new Reservation("Bhanu", "Single"), inventory)));
        threads.add(new Thread(new BookingTask(new Reservation("Rahul", "Single"), inventory)));
        threads.add(new Thread(new BookingTask(new Reservation("Anjali", "Single"), inventory)));
        threads.add(new Thread(new BookingTask(new Reservation("Kiran", "Single"), inventory)));

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\nFinal Availability: " +
                inventory.getAvailability("Single"));
    }
}