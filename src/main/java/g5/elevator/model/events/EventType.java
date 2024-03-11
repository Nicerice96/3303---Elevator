package g5.elevator.model.events;

public enum EventType {
    ELEVATOR_IDLE_TOO_LONG,
    ELEVATOR_TRAVERSED_FLOOR,
    ELEVATOR_ARRIVED,
    ELEVATOR_DEPARTED,
    ELEVATOR_LOADING,
    ELEVATOR_UNLOADING,
    ELEVATOR_DOOR_OPENING,
    ELEVATOR_DOOR_OPEN,
    ELEVATOR_DOOR_CLOSING,
    ELEVATOR_DOOR_CLOSED,
    ELEVATOR_RECEIVED_REQUEST,
    SCHEDULER_RECEIVED_FLOOR_REQUEST,
    SCHEDULER_RECEIVED_ELEVATOR_REQUEST,
    SCHEDULER_MOVE_TO_IDLE,
    RECEIVED,
    SENT,
    FORWARDED,
}