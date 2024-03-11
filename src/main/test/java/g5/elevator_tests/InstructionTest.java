package g5.elevator_tests;

import org.junit.Test;
import g5.elevator.model.instruction.Direction;
import g5.elevator.model.instruction.Instruction;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static g5.elevator.defs.Defs.TIMESTAMP_FORMATTER;

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

    @Test
    public void testParser1() {
        //set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        Direction direction = Direction.UP;
        int pickup = 1, destination = 4;
        Instruction instruction = Instruction.parse(new Instruction(expectedTimestamp, direction, pickup, destination).toString());

        // Assert
        assertEquals(expectedTimestamp, instruction.getTimestamp());
        assertEquals(direction, instruction.getButtonDirection());
        assertEquals(pickup, instruction.getPickupFloor());
        assertEquals(destination, instruction.getDestinationFloor());
    }
    @Test
    public void testParser2() {
        //set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        Direction direction = Direction.DOWN;
        int pickup = 10, destination = -10;
        Instruction instruction = Instruction.parse(new Instruction(expectedTimestamp, direction, pickup, destination).toString());

        // Assert
        assertEquals(expectedTimestamp, instruction.getTimestamp());
        assertEquals(direction, instruction.getButtonDirection());
        assertEquals(pickup, instruction.getPickupFloor());
        assertEquals(destination, instruction.getDestinationFloor());
    }
    @Test
    public void testParserWhitespace() {
        //set up
        LocalTime expectedTimestamp = LocalTime.parse("00:00:05.000", TIMESTAMP_FORMATTER);
        Direction direction = Direction.DOWN;
        int pickup = 10, destination = -10;
        Instruction instruction = Instruction.parse(" " + new Instruction(expectedTimestamp, direction, pickup, destination).toString() + " ");

        // Assert
        assertEquals(expectedTimestamp, instruction.getTimestamp());
        assertEquals(direction, instruction.getButtonDirection());
        assertEquals(pickup, instruction.getPickupFloor());
        assertEquals(destination, instruction.getDestinationFloor());
    }
}
