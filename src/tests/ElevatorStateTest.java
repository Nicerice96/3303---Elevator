package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.elevator.ElevatorNode;
import src.instruction.Direction;
import src.instruction.Instruction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ElevatorStateTest {
    private ElevatorNode elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorNode();
    }

    @Test
    public void test1() {
        Instruction instruction = new Instruction(1, Direction.DOWN, 0, 0);
        elevator.addPickup(instruction);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertNull(elevator.getNextDestination());
    }
}
