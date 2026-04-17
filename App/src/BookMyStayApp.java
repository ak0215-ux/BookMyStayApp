import java.io.*;
import java.util.*;

/* 1. Reservation Class */
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String guestName;
    private String roomType;
    private String assignedRoomId;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void setAssignedRoomId(String id) {
        this.assignedRoomId = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getAssignedRoomId() {
        return assignedRoomId;
    }
}


/* 2. Thread-Safe Booking Queue */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getNextRequest() {

        if (queue.isEmpty())
            return null;

        return queue.poll();
    }
}


/* 3. Inventory Service (Thread-Safe + Serializable) */
class InventoryService implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> availableCounts = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public InventoryService() {

        availableCounts.put("Single", 5);
        availableCounts.put("Double", 3);
        availableCounts.put("Suite", 2);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public synchronized String allocateRoom(String roomType) {

        if (!availableCounts.containsKey(roomType))
            return null;

        if (availableCounts.get(roomType) <= 0)
            return null;

        String roomId =
                roomType.substring(0, 1).toUpperCase()
                        + (100 + allocatedRooms.get(roomType).size() + 1);

        allocatedRooms.get(roomType).add(roomId);

        availableCounts.put(
                roomType,
                availableCounts.get(roomType) - 1
        );

        return roomId;
    }

    public Map<String, Integer> getAvailableCounts() {
        return availableCounts;
    }

    public Map<String, Set<String>> getAllocatedRooms() {
        return allocatedRooms;
    }
}


/* 4. Application State Snapshot */
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    InventoryService inventory;
    List<Reservation> bookingHistory;

    SystemState(InventoryService inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.bookingHistory = history;
    }
}


/* 5. Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("💾 System state saved successfully.");

        } catch (IOException e) {

            System.out.println("⚠ Failed to save state.");
            e.printStackTrace();
        }
    }

    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();

            System.out.println("📂 Persisted system state restored.");

            return state;

        } catch (FileNotFoundException e) {

            System.out.println("ℹ No previous state found. Starting fresh.");
            return null;

        } catch (Exception e) {

            System.out.println("⚠ Corrupted persistence file. Starting fresh.");
            return null;
        }

        return total;
    }
}


/* 6. Concurrent Booking Processor */
class ConcurrentBookingProcessor extends Thread {

    private BookingRequestQueue queue;
    private InventoryService inventory;
    private List<Reservation> bookingHistory;

    public ConcurrentBookingProcessor(
            BookingRequestQueue queue,
            InventoryService inventory,
            List<Reservation> history,
            String threadName
    ) {
        super(threadName);
        this.queue = queue;
        this.inventory = inventory;
        this.bookingHistory = history;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request = queue.getNextRequest();

            if (request == null)
                break;

            String roomId =
                    inventory.allocateRoom(request.getRoomType());

            if (roomId != null) {

                request.setAssignedRoomId(roomId);

                synchronized (bookingHistory) {
                    bookingHistory.add(request);
                }

                System.out.println(
                        Thread.currentThread().getName()
                                + " ✅ Confirmed: "
                                + request.getGuestName()
                                + " → Room "
                                + roomId
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName()
                                + " ❌ Failed: No room available for "
                                + request.getGuestName()
                );
            }
        }
    }
}


/* 7. Main Application */
public class BookMyStayApp {

    public static void main(String[] args) {
    }
}
