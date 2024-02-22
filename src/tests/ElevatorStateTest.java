package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.elevator.ElevatorNode;
import src.instruction.Direction;
import src.instruction.Instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * The ElevatorStateTest class is responsible for conducting unit tests on the ElevatorState class,
 * ElevatorNode class, and the different Elevator classes.
 *
 * @author Nabeel Azard
 * @version 1.0
 */

public class ElevatorStateTest {
    private ElevatorNode elevatorNode;

    @Before
    public void setUp() {
        elevatorNode = new ElevatorNode();
    }

    @Test
    public void testGetNextDestination_Null() {
        assertNull(elevatorNode.getNextDestination());
    }

    @Test
    public void testGetNextDestination_Null2() {
        elevatorNode.addPickup(new Instruction(1, Direction.DOWN, 0, 0));
        assertNull(elevatorNode.getNextDestination());
    }

    @Test
    public void testGetNextDestination_1() {
        elevatorNode.addPickup(new Instruction(1, Direction.UP, 1, 3));
        assertEquals(Integer.valueOf(1), elevatorNode.getNextDestination());
    }

    @Test
    public void testGetNextDestination_Negative1() {
        elevatorNode.addPickup(new Instruction(1, Direction.UP, -1, 3));
        assertEquals(Integer.valueOf(-1), elevatorNode.getNextDestination());
    }
//    private ElevatorNode elevator;
//
//    @Before
//    public void setUp() {
//        elevator = new ElevatorNode();
//    }
//
//    @Test
//    public void test1() {
//        Instruction instruction = new Instruction(1, Direction.DOWN, 0, 0);
//        elevator.addPickup(instruction);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        //assertNull(elevator.getNextDestination());
//        assertEquals(0, elevator.getNextDestination());
//    }
}
