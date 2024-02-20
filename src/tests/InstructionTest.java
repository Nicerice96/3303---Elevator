package tests;

import org.junit.Assert;
import org.junit.Test;
import src.instruction.Direction;
import src.instruction.Instruction;

import static org.junit.Assert.assertEquals;


public class InstructionTest {

    @Test
    public void testGetTimestamp() {
        // set up
        int expectedTimestamp = 5;
        Instruction instruction = new Instruction(expectedTimestamp, Direction.UP, 1, 3);

        // Assert
        assertEquals(expectedTimestamp, instruction.getTimestamp());

    }

    @Test
    public void testGetButtonDirection() {
        // set up
        Direction expectedDirection = Direction.DOWN;
        Instruction instruction = new Instruction(5, expectedDirection, 1, 3);

        // Assert
        assertEquals(expectedDirection,instruction.getButtonDirection() );
    }

    @Test
    public void testGetPickupFloor() {
        // set up
        int expectedPickupFloor = 1;
        Instruction instruction = new Instruction(5, Direction.UP, expectedPickupFloor, 3);


        // Assert
        assertEquals(expectedPickupFloor, instruction.getPickupFloor());
    }

    @Test
    public void testGetDestinationFloor() {
        //set up
        int expectedDestinationFloor = 3;
        Instruction instruction = new Instruction(5, Direction.UP, 1, expectedDestinationFloor);

        // Assert
        assertEquals(expectedDestinationFloor, instruction.getDestinationFloor());
    }
}
