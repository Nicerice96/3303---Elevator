package src.scheduler_state.floor;

import src.SchedulerSystem;
import src.scheduler_state.SchedulerIdleState;
import src.scheduler_state.SchedulerProcessingRegistrationState;
import src.scheduler_state.SchedulerState;

public class SchedulerProcessingFloorRequestState extends SchedulerState {
    protected final String msg;
    protected final int floor;
    public SchedulerProcessingFloorRequestState(String msg) {
        super();
        this.msg = msg;
        this.floor = Integer.parseInt(msg.split(",")[0].replace("floor", "").strip());
    }
    @Override
    public void handle() {
        String action = msg.split(",")[1].strip();

        // mini router, find the appropriate state
        if(action.equals("register")) {
            SchedulerSystem.setState(new SchedulerProcessingRegistrationState(msg, floor, SchedulerSystem.floors));
        } else if (action.equals("addInstruction")) {
            SchedulerSystem.setState(new ProcessingFloorAddInstructionState(msg));
        } else {
            System.out.printf("SCHEDULER ERROR: Unknown action \"%s\", moving back to idle\n", action);
            SchedulerSystem.setState(new SchedulerIdleState());
        }
    }
}
