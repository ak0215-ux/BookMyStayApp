import java.util.*;

// 1. Updated Reservation to include a Room ID after processing
class Reservation {
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

// 2. Booking Request Queue (FIFO processing)
class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addRequest(Reservation r) {
        requestQueue.add(r);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

// 3. Inventory Service: Manages state and uniqueness
class InventoryService {

    private Map<String, Integer> availableCounts = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public InventoryService() {

        availableCounts.put("Single", 10);
        availableCounts.put("Double", 5);
        availableCounts.put("Suite", 2);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public boolean isAvailable(String roomType) {
        return availableCounts.getOrDefault(roomType, 0) > 0;
    }

    public String allocateRoom(String roomType) {

        if (!isAvailable(roomType))
            return null;

        String roomId = roomType.substring(0,1).toUpperCase() +
                (100 + allocatedRooms.get(roomType).size() + 1);

        allocatedRooms.get(roomType).add(roomId);

        availableCounts.put(roomType,
                availableCounts.get(roomType) - 1);

        return roomId;
    }
}

// 4. Booking Service: Processes booking queue
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processQueue(BookingRequestQueue queue) {

        while(queue.hasPendingRequests()) {

            Reservation request = queue.getNextRequest();

            if(inventory.isAvailable(request.getRoomType())) {

                String roomId = inventory.allocateRoom(request.getRoomType());
                request.setAssignedRoomId(roomId);

                System.out.println("✅ Confirmed: "
                        + request.getGuestName()
                        + " assigned to Room "
                        + roomId);

            } else {

                System.out.println("❌ Failed: No availability for "
                        + request.getRoomType()
                        + " (Guest: "
                        + request.getGuestName()
                        + ")");
            }
        }
    }
}

// 5. Add-On Service (Optional Feature)
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// 6. Add-On Service Manager
class AddOnServiceManager {

    // ReservationID → List of Services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if(services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Room " + reservationId + ":");

        for(Service s : services) {
            System.out.println("- " + s.getServiceName()
                    + " : ₹" + s.getCost());
        }
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if(services != null) {

            for(Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// 7. Main Application
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService service = new BookingService(inventory);

        AddOnServiceManager addOnManager = new AddOnServiceManager();

        // Booking Requests
        Reservation r1 = new Reservation("Abhi", "Suite");
        Reservation r2 = new Reservation("Subha", "Suite");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process Bookings
        service.processQueue(bookingQueue);

        // Add-On Services for confirmed reservations
        if(r1.getAssignedRoomId() != null) {

            addOnManager.addService(r1.getAssignedRoomId(),
                    new Service("Breakfast", 500));

            addOnManager.addService(r1.getAssignedRoomId(),
                    new Service("Airport Pickup", 1000));
        }

        if(r2.getAssignedRoomId() != null) {

            addOnManager.addService(r2.getAssignedRoomId(),
                    new Service("Spa Access", 1500));
        }

        System.out.println("\n------ Add-On Service Summary ------");

        if(r1.getAssignedRoomId() != null) {
            addOnManager.displayServices(r1.getAssignedRoomId());
            System.out.println("Total Cost: ₹"
                    + addOnManager.calculateTotalCost(r1.getAssignedRoomId()));
        }

        if(r2.getAssignedRoomId() != null) {
            addOnManager.displayServices(r2.getAssignedRoomId());
            System.out.println("Total Cost: ₹"
                    + addOnManager.calculateTotalCost(r2.getAssignedRoomId()));
        }
    }
}
