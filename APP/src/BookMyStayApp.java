import java.util.*;

// Reservation class (represents booking request)
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (read-only)
    public void viewRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        System.out.println("\nBooking Requests (FIFO Order):");
        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (without removing)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulating guest booking requests
        requestQueue.addRequest(new Reservation("Bhanu", "Single"));
        requestQueue.addRequest(new Reservation("Rahul", "Suite"));
        requestQueue.addRequest(new Reservation("Anjali", "Double"));

        // View all requests
        requestQueue.viewRequests();

        // Show next request to be processed
        System.out.println("\nNext Request to Process:");
        Reservation next = requestQueue.peekNext();

        if (next != null) {
            next.display();
        }
    }
}