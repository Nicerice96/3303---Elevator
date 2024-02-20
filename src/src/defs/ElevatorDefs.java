package src.defs;

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

    /**
     * Time taken for loading passengers into the elevator in milliseconds.
     */
    public static final long LOADING_TIME = 4714; // ms

    /**
     * Time taken for unloading passengers from the elevator in milliseconds.
     */
    public static final long UNLOADING_TIME = 4713; // ms

    /**
     * Acceleration rate of the elevator in meters per second squared (m/s^2).
     */
    public static final float ACCELERATION = 1.0F; // m/s^2

    /**
     * Deceleration rate of the elevator in meters per second squared (m/s^2).
     */
    public static final float DECELERATION = 1.0F; // m/s^2

    /**
     * Maximum speed of the elevator in meters per second (m/s).
     */
    public static final float MAX_SPEED = 5.0F; // m/s
}
