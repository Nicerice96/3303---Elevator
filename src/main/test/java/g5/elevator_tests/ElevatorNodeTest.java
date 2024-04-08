package g5.elevator_tests;

import org.junit.Before;
import org.junit.Test;
import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.instruction.Direction;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static g5.elevator.defs.Defs.TIMESTAMP_FORMATTER;

public class ElevatorNodeTest {

    private ElevatorNode elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorNode();
    }

    @Test
    public void testAddingPickupInstruction() {
        Instruction instruction = new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, 2, 5);
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
    public void testTraverseToTopFloor() {
        int initialFloor = elevator.getCurrentFloor();
        for (int i = initialFloor; i < 5; i++) {
            elevator.traverse(Direction.UP);
        }
        assertEquals("Should be at the top floor", 5, elevator.getCurrentFloor());
    }

    @Test
    public void testTraverseToBottomFloor() {
        int initialFloor = elevator.getCurrentFloor();
        for (int i = initialFloor; i > 0; i--) {
            elevator.traverse(Direction.DOWN);
        }
        assertEquals("Should be at the bottom floor", 0, elevator.getCurrentFloor());
    }


    @Test
    public void testClearDestination() {
        Instruction instruction = new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, 2, 5);
        elevator.addPickup(instruction);
        assertEquals("Destination should be added", Integer.valueOf(2), elevator.getNextDestination());
        elevator.clearDestination();
        assertNull("Destinations should be cleared", elevator.getNextDestination());
    }

    @Test
    public void testUnwrapPendingInstructions() {
        elevator.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, elevator.getCurrentFloor(), 5));
        elevator.loadPassengers();
        assertTrue("Destination for current floor should be added", elevator.destinations.contains(5));
    }

    @Test
    public void testDirectionNull() {
        assertNull(elevator.getDirection());
    }
    @Test
    public void testDirectionUp() {
        elevator.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, elevator.getCurrentFloor()+2, 5));
        assertEquals(elevator.getDirection(), Direction.UP);
    }
    @Test
    public void testDirectionDown() {
        elevator.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, elevator.getCurrentFloor()-2, 5));
        assertEquals(elevator.getDirection(), Direction.DOWN);
    }
}
