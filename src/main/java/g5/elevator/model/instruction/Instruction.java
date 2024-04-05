package g5.elevator.model.instruction;

import g5.elevator.defs.Defs;

import java.time.Duration;
import java.time.Instant;
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
    private final Instant scheduleTick = Instant.now();
    private Instant pickupTick;
    private Instant dropoffTick;

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

    /**
     * Creates an instruction from the toString() method
     * @return parsed Instruction
     */
    public static Instruction parse(String instruction) {
        String clean = instruction.strip().replace("Instruction - ", "");
        String[] seg = clean.split("\\|");
        LocalTime timestamp = LocalTime.parse(seg[0], Defs.TIMESTAMP_FORMATTER);
        int pickupFloor, destinationFloor;
        Direction direction = seg[2].equals("DOWN") ? Direction.DOWN : Direction.UP;
        try {
            pickupFloor = Integer.parseInt(seg[1].trim());
            destinationFloor = Integer.parseInt(seg[3].trim());
        } catch(NumberFormatException e) {
            return null;
        }
        return new Instruction(timestamp, direction, pickupFloor, destinationFloor);
    }


    // Getters
    public LocalTime getTimestamp() { return timestamp; }
    public Direction getButtonDirection() { return buttonDirection; }
    public int getPickupFloor() { return pickupFloor; }
    public int getDestinationFloor() { return destinationFloor; }
    public void setPickupTick() { pickupTick = Instant.now(); }
    public void setDropoffTick() { dropoffTick = Instant.now(); }

    public long getTotalTime() {
        if(dropoffTick == null) return 0;
        return Duration.between(scheduleTick, dropoffTick).toMillis();
    }
    public long getPickupTime() {
        if(pickupTick == null) return 0;
        return Duration.between(scheduleTick, pickupTick).toMillis();
    }
    public long getDropoffTime() {
        if(dropoffTick == null) return 0;
        return Duration.between(pickupTick, dropoffTick).toMillis();
    }

    @Override
    public String toString() {
        return String.format("Instruction - %s|%d|%s|%d", timestamp.format(Defs.TIMESTAMP_FORMATTER), pickupFloor, buttonDirection, destinationFloor);
    }
}
