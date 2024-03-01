package src.instruction;

import java.time.LocalTime;

/**
 * Class representation of an elevator instruction that is read from a text file.
 * @author Hamza
 */
public class Instruction {
    private final LocalTime timestamp;
    private final Direction buttonDirection;
    private final int pickupFloor;
    private final int destinationFloor;

    /**
     * Constructs an Instruction.
     * An instruction is the class representation of an elevator instruction that is read from a text file.
     * @param timestamp timestamp in LocalTime (first element in an instruction, in format hh:mm:ss.SSS)
     * @param buttonDirection button direction, can be DOWN or UP (second element in an instruction)
     * @param pickupFloor pickup floor in int (third element in an instruction)
     * @param destinationFloor destination/drop off floor (forth element in an instruction)
     */
    public Instruction(LocalTime timestamp, Direction buttonDirection, int pickupFloor, int destinationFloor) {
        this.timestamp = timestamp;
        this.buttonDirection = buttonDirection;
        this.pickupFloor = pickupFloor;
        this.destinationFloor = destinationFloor;
    }


    // Getters
    public LocalTime getTimestamp() { return timestamp; }
    public Direction getButtonDirection() { return buttonDirection; }
    public int getPickupFloor() { return pickupFloor; }
    public int getDestinationFloor() { return destinationFloor; }

    @Override
    public String toString() {
        return String.format("Instruction - timestamp: %d, pickup: %d, direction: %s, destination: %s", timestamp, pickupFloor, buttonDirection, destinationFloor);
    }
}
