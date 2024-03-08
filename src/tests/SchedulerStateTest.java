package tests;

import org.junit.After;
import org.junit.Test;
import src.SchedulerSystem;
import src.instruction.Direction;
import src.instruction.Instruction;
import src.scheduler_state.SchedulerIdleState;

import java.time.LocalTime;

import static org.junit.Assert.*;
import static src.defs.Defs.TIMESTAMP_FORMATTER;

public class SchedulerStateTest {
    @After
    public void cleanup() {
        SchedulerSystem.stopScheduler(true);
    }

    @Test
    public void testStateTransition() {
        // Create a SchedulerSystem instance
        SchedulerSystem schedulerSystem = new SchedulerSystem();
        schedulerSystem.addInstruction(new Instruction(LocalTime.parse("00:00:01.000", TIMESTAMP_FORMATTER), Direction.UP, 1, 2));
        schedulerSystem.stopScheduler(true);

        schedulerSystem.setState(new SchedulerIdleState());

        // Initially, the state should be SchedulerIdleState
        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);

        schedulerSystem.stopScheduler(false);
        schedulerSystem.stopScheduler(true);

        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);





    }
}
