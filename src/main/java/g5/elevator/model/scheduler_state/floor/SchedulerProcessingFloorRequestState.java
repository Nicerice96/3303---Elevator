package g5.elevator.model.scheduler_state.floor;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.scheduler_state.SchedulerProcessingRegistrationState;
import g5.elevator.model.scheduler_state.SchedulerState;

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
