package g5.elevator_tests;

import org.junit.Before;
import org.junit.Test;
import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.instruction.Direction;
import g5.elevator.model.instruction.Instruction;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static g5.elevator.defs.Defs.TIMESTAMP_FORMATTER;

public class ElevatorPickupTest {
    private static final LocalTime ts = java.time.LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER);

    private ElevatorNode elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorNode();
    }

    @Test
    public void testIncrementingUp() {
        // Scenario 1
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 1, 5),
                new Instruction(ts, Direction.UP, 2, 5),
                new Instruction(ts, Direction.UP, 3, 5),
                new Instruction(ts, Direction.UP, 4, 5)
        };
        int[] expected = {0, 1, 2, 3, 4};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);
        }
    }

    @Test
    public void testSameDirectionUp() {
        // Scenario 2
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 2, 4),
                new Instruction(ts, Direction.UP, 1, 4),
        };
        int[] expected = {0, 0};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);
        }
    }

    @Test
    public void testOppositeDirectionUp() {
        // Scenario 3
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 2, 4),
                new Instruction(ts, Direction.DOWN, 1, 0),
        };
        int[] expected = {0, 1};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);
        }
    }

    @Test
    public void testMultipleSameDirectionUp() {
        // Scenario 4
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 4, 5),
                new Instruction(ts, Direction.UP, 3, 5),
                new Instruction(ts, Direction.UP, 2, 5),
                new Instruction(ts, Direction.UP, 1, 5),
        };
        int[] expected = {0, 0, 0, 0};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);

        }
    }
    @Test
    public void testMultipleOppositeDirections() {
        // Scenario 5
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        /**
         * 1. |0,4,UP,5| = [] -> [4*]                   = 0
         * 2. |0,2,UP,5| = [4] -> [2*, 4]               = 0
         * 3. |0,1,DN,5| = [2, 4] -> [2, 4, 1*]         = 2
         * 4. |0,3,UP,5| = [2, 4, 1] -> [2, 3*, 4, 1]   = 1
         */
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 4, 5),
                new Instruction(ts, Direction.UP, 2, 5),
                new Instruction(ts, Direction.DOWN, 1, 0),
                new Instruction(ts, Direction.UP, 3, 5),
        };
        int[] expected = {0, 0, 2, 1};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);
        }
    }
    @Test
    public void testNegativePickups() {
        // Scenario 6
        // sanity check
        assertEquals(elevator.getCurrentFloor(), 0);
        /**
         * 1. |0,2, UP,5| = [] -> [2*]                      = 0
         * 2. |0,-2,UP,5| = [2] -> [2, -2*]                 = 1
         * 3. |0,1, DN,0| = [2, -2] -> [2, 1*, -2]          = 1
         * 4. |0,-1,UP,5| = [2, 1, -2] -> [2, 1, -2, -1*]   = 3
         */
        Instruction[] instructions = {
                new Instruction(ts, Direction.UP, 2, 5),
                new Instruction(ts, Direction.UP, -2, 5),
                new Instruction(ts, Direction.DOWN, 1, 0),
                new Instruction(ts, Direction.UP, -1, 5),
        };
        int[] expected = {0, 1, 1, 3};

        // actual test
        for (int i = 0; i < instructions.length; i++) {
            Instruction instruction = instructions[i];
            int e = expected[i];
            System.out.printf("%d. %s, %d, %d\n", i, instruction, e, elevator.getPickupIndex(instruction));
            assertEquals(e, elevator.getPickupIndex(instruction));
            elevator.addPickup(instruction);
        }
    }
}
