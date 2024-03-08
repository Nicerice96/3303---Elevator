package src.scheduler_state.elevator;

import src.SchedulerSystem;
import src.scheduler_state.SchedulerIdleState;
import src.scheduler_state.SchedulerState;
import src.scheduler_state.floor.ProcessingFloorAddInstructionState;
import src.scheduler_state.SchedulerProcessingRegistrationState;

public class SchedulerProcessingElevatorRequestState extends SchedulerState {
    protected final String msg;
    protected final int id;
    public SchedulerProcessingElevatorRequestState(String msg) {
        super();
        this.id = Integer.parseInt(msg.split(",")[0].replace("elevator", "").strip());
        this.msg = msg;
    }
    @Override
    public void handle() {
        String action = msg.split(",")[1];

        // mini router, find the appropriate state
        if(action.equals("register")) {
            SchedulerSystem.setState(new SchedulerProcessingRegistrationState(msg, id, SchedulerSystem.elevators));
        } else if (action.equals("addInstruction")) {
            SchedulerSystem.setState(new ProcessingFloorAddInstructionState(msg));
        } else {
            System.out.printf("SCHEDULER ERROR: Unknown action \"%s\", moving back to idle\n", action);
            SchedulerSystem.setState(new SchedulerIdleState());
        }
    }
}

