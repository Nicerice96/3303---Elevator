package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.elevator.ElevatorNode;
import src.instruction.Direction;
import src.instruction.Instruction;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static src.defs.Defs.TIMESTAMP_FORMATTER;

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
        elevatorNode.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.DOWN, 0, 0));
        assertNotNull(elevatorNode.getNextDestination());
    }

    @Test
    public void testGetNextDestination_1() {
        elevatorNode.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, 1, 3));
        assertEquals(Integer.valueOf(1), elevatorNode.getNextDestination());
    }

    @Test
    public void testGetNextDestination_Negative1() {
        elevatorNode.addPickup(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, -1, 3));
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
