import java.util.*;

class Reservation {
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
        System.out.println("ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void showAllBookings(List<Reservation> history) {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }

    public void generateSummary(List<Reservation> history) {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            countMap.put(r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nBooking Summary (Room Type Count):");
        for (String type : countMap.keySet()) {
            System.out.println(type + " -> " + countMap.get(type));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("R101", "Bhanu", "Single"));
        history.addReservation(new Reservation("R102", "Rahul", "Double"));
        history.addReservation(new Reservation("R103", "Anjali", "Single"));
        history.addReservation(new Reservation("R104", "Kiran", "Suite"));

        reportService.showAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());
    }
}