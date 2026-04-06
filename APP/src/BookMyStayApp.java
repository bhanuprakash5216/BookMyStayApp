import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> history;

    public SystemState(Map<String, Integer> inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public static void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded.");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return new SystemState(new HashMap<>(), new ArrayList<>());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        SystemState state = PersistenceService.load();

        if (state.inventory.isEmpty()) {
            state.inventory.put("Single", 2);
            state.inventory.put("Double", 1);

            state.history.add(new Reservation("R101", "Bhanu", "Single"));
            state.history.add(new Reservation("R102", "Rahul", "Double"));
        }

        System.out.println("\nInventory:");
        for (String type : state.inventory.keySet()) {
            System.out.println(type + " -> " + state.inventory.get(type));
        }

        System.out.println("\nBooking History:");
        for (Reservation r : state.history) {
            r.display();
        }

        PersistenceService.save(state);
    }
}