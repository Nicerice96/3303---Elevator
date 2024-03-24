package g5.elevator.defs;

/**
 * Constants definition class for Elevator subsystem.
 * This class defines various constants used in the Elevator subsystem.
 */
public class ElevatorDefs {
    /**
     * Time taken to move between adjacent floors in milliseconds.
     */
    public static final long ADJACENT_FLOOR_TIME = 8628; // ms

    /**
     * Time taken for the elevator door to open in milliseconds.
     */
    public static final long DOOR_OPENING_TIME = 3364; // ms

    /**
     * Time taken for the elevator door to close in milliseconds.
     */
    public static final long DOOR_CLOSING_TIME = 3324; // ms
    public static final long DOOR_STUCK_TIME = 3000; // ms

    /**
     * Time taken for loading passengers into the elevator in milliseconds.
     */
    public static final long LOADING_TIME = 4714; // ms

    /**
     * Time taken for unloading passengers from the elevator in milliseconds.
     */
    public static final long UNLOADING_TIME = 4713; // ms

    /**
     * The acceleration time in milliseconds.
     */
    public static final long ACCELERATION_TIME = 262; // ms
    /**
     * The elevator max speed in (m/s)
     */
    public static final float MAX_SPEED = 1.225f; // (m/s)

    /**
     * Tick rate (used for ElevatorMovingState), in milliseconds.
     */
    public static final long TICK_RATE = 5; // ms
}
