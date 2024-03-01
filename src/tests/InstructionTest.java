package tests;

import org.junit.Test;
import src.instruction.Direction;
import src.instruction.Instruction;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static src.defs.Defs.TIMESTAMP_FORMATTER;

/**
 * This is the test class for Instruction.java
 */
public class InstructionTest {

    @Test
    public void testGetTimestamp() {
        // set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        Instruction instruction = new Instruction(expectedTimestamp, Direction.UP, 1, 3);

        // Assert
        assertEquals(expectedTimestamp, instruction.getTimestamp());
    }

    @Test
    public void testGetButtonDirection() {
        // set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        Direction expectedDirection = Direction.DOWN;
        Instruction instruction = new Instruction(expectedTimestamp, expectedDirection, 1, 3);

        // Assert
        assertEquals(expectedDirection,instruction.getButtonDirection() );
    }

    @Test
    public void testGetPickupFloor() {
        // set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        int expectedPickupFloor = 1;
        Instruction instruction = new Instruction(expectedTimestamp, Direction.UP, expectedPickupFloor, 3);


        // Assert
        assertEquals(expectedPickupFloor, instruction.getPickupFloor());
    }

    @Test
    public void testGetDestinationFloor() {
        //set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        int expectedDestinationFloor = 3;
        Instruction instruction = new Instruction(expectedTimestamp, Direction.UP, 1, expectedDestinationFloor);

        // Assert
        assertEquals(expectedDestinationFloor, instruction.getDestinationFloor());
    }
}
