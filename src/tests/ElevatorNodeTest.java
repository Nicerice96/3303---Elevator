package tests;

import org.junit.Before;
import org.junit.Test;
import src.elevator.ElevatorNode;
import src.instruction.Instruction;
import src.instruction.Direction;

import static org.junit.Assert.*;

public class ElevatorNodeTest {

    private ElevatorNode elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorNode();
    }

    @Test
    public void testAddingPickupInstruction() {
        Instruction instruction = new Instruction(1, Direction.UP, 2, 5);
        elevator.addPickup(instruction);
        assertFalse("Destinations should not be empty after adding a pickup", elevator.destinationsEmpty());
        assertEquals("The first destination should be the pickup floor", Integer.valueOf(2), elevator.getNextDestination());

    }

    @Test
    public void testUpdateAltitude() {
        float initialAltitude = elevator.getAltitude();
        elevator.updateAltitude(5.0f);
        assertEquals("Altitude should increase by 5.0", initialAltitude + 5.0f, elevator.getAltitude(), 0.01);
    }

    @Test
    public void testTraverseUp() {
        int initialFloor = elevator.getCurrentFloor();
        elevator.traverse(Direction.UP);
        assertEquals("Should move up one floor", initialFloor + 1, elevator.getCurrentFloor());
    }

    @Test
    public void testTraverseDown() {
        elevator.traverse(Direction.UP); // initial floor is 0
        int initialFloor = elevator.getCurrentFloor();
        elevator.traverse(Direction.DOWN);
        assertEquals("Should move down one floor", initialFloor - 1, elevator.getCurrentFloor());
    }

    @Test
    public void testClearDestination() {
        Instruction instruction = new Instruction(1, Direction.UP, 2, 5);
        elevator.addPickup(instruction);
        assertEquals("Destination should be added", Integer.valueOf(2), elevator.getNextDestination());
        elevator.clearDestination();
        assertNull("Destinations should be cleared", elevator.getNextDestination());
    }

    @Test
    public void testUnwrapPendingInstructions() {
        elevator.addPickup(new Instruction(1, Direction.UP, elevator.getCurrentFloor(), 5));
        elevator.unwrapPendingInstructions();
        assertTrue("Destination for current floor should be added", elevator.destinations.contains(5));
    }
}
