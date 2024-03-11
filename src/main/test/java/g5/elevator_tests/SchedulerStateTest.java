package g5.elevator_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.instruction.Direction;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.scheduler_state.SchedulerIdleState;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static g5.elevator.defs.Defs.TIMESTAMP_FORMATTER;

public class SchedulerStateTest {
    private SchedulerSystem schedulerSystem;

    @Before
    public void setUp() {
        schedulerSystem = new SchedulerSystem();
    }
    @After
    public void cleanup() {
        schedulerSystem.stopScheduler(true);
    }

    @Test
    public void testStateTransition() {
        // Create a SchedulerSystem instance
        schedulerSystem.addInstruction(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, 1, 2));
        schedulerSystem.stopScheduler(true);

        schedulerSystem.setState(new SchedulerIdleState(schedulerSystem));

        // Initially, the state should be SchedulerIdleState
        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);

        schedulerSystem.stopScheduler(false);
        schedulerSystem.stopScheduler(true);

        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);
    }
}
