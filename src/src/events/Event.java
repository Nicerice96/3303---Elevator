package src.events;


/**
 * Encapsulates events, will be used for displaying events using a java UI framework
 */
public class Event {
    private EventType eventType;
    private final int elevatorId;

    /**
     * Creates a non-elevator event instance
     * @param eventType the event type
     */
    public Event(EventType eventType) {
        this(eventType, -1);
    }
    /**
     * Creates an elevator event instance
     * @param eventType the elevator event type
     * @param elevatorId the elevator ID
     */
    public Event(EventType eventType, int elevatorId) {
        this.eventType = eventType;
        this.elevatorId = elevatorId;
    }

    // Getters
    public EventType getEventType() { return eventType; }
    public int getElevatorId() { return elevatorId; }

    @Override
    public String toString() {
        return
            eventType == EventType.ELEVATOR_ARRIVED ?                       String.format("Elevator %d arrived.", elevatorId)
                    : eventType == EventType.ELEVATOR_DEPARTED ?            String.format("Elevator %d departed.", elevatorId)
                    : eventType == EventType.ELEVATOR_LOADING ?             String.format("Elevator %d loading passengers", elevatorId)
                    : eventType == EventType.ELEVATOR_UNLOADING ?           String.format("Elevator %d unloading passengers", elevatorId)
                    : eventType == EventType.ELEVATOR_DOOR_OPENING ?        String.format("Elevator %d opening its doors", elevatorId)
                    : eventType == EventType.ELEVATOR_DOOR_OPEN ?           String.format("Elevator %d opened its doors", elevatorId)
                    : eventType == EventType.ELEVATOR_DOOR_CLOSING ?        String.format("Elevator %d closing its doors", elevatorId)
                    : eventType == EventType.ELEVATOR_DOOR_CLOSED ?         String.format("Elevator %d closed its doors", elevatorId)
                    : eventType == EventType.ELEVATOR_RECEIVED_REQUEST ?    String.format("Elevator %d received a request", elevatorId)
                    : eventType == EventType.SCHEDULER_RECEIVED_REQUEST ?   "Scheduler received a pickup request"
                    :                                                       String.format("Unknown event: %s. Please update Event.toString()", eventType);
    }

}
