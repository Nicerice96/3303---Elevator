package src.instruction;

/**
 * Class representation of an elevator instruction that is read from a text file.
 * @author Hamza
 */
public class Instruction {
    private final int timestamp;
    private final Direction buttonDirection;
    private final int pickupFloor;
    private final int destinationFloor;

    /**
     * Constructs an Instruction.
     * An instruction is the class representation of an elevator instruction that is read from a text file.
     * @param timestamp timestamp in int (first element in an instruction)
     * @param buttonDirection button direction, can be DOWN or UP (second element in an instruction)
     * @param pickupFloor pickup floor in int (third element in an instruction)
     * @param destinationFloor destination/drop off floor (forth element in an instruction)
     */
    public Instruction(int timestamp, Direction buttonDirection, int pickupFloor, int destinationFloor) {
        this.timestamp = timestamp;
        this.buttonDirection = buttonDirection;
        this.pickupFloor = pickupFloor;
        this.destinationFloor = destinationFloor;
    }


    // Getters
    public int getTimestamp() { return timestamp; }
    public Direction getButtonDirection() { return buttonDirection; }
    public int getPickupFloor() { return pickupFloor; }
    public int getDestinationFloor() { return destinationFloor; }
}
