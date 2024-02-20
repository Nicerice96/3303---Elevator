package tests;

import org.junit.After;
import org.junit.Test;
import src.SchedulerSystem;
import src.elevator.ElevatorNode;
import src.instruction.Direction;
import src.instruction.Instruction;
import src.scheduler_state.SchedulerIdleState;
import src.scheduler_state.SchedulerProcessingRequestState;
import src.scheduler_state.SchedulerState;

import static org.junit.Assert.*;

public class SchedulerStateTest {

    @After
    public void cleanup() {
        SchedulerSystem.stopScheduler(true);
    }

    @Test
    public void testStateTransition() {
        // Create a SchedulerSystem instance
        SchedulerSystem schedulerSystem = new SchedulerSystem();
        schedulerSystem.addPayload(new Instruction(1, Direction.UP, 1, 2));
        schedulerSystem.stopScheduler(true);

        schedulerSystem.setSchedulerState(new SchedulerIdleState());

        // Initially, the state should be SchedulerIdleState
        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);

        schedulerSystem.stopScheduler(false);
        schedulerSystem.stopScheduler(true);

        assertTrue(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);





    }
}
