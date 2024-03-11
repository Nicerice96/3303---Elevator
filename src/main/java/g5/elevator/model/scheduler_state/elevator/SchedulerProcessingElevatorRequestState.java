package g5.elevator.model.scheduler_state.elevator;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.scheduler_state.SchedulerState;
import g5.elevator.model.scheduler_state.floor.ProcessingFloorAddInstructionState;
import g5.elevator.model.scheduler_state.SchedulerProcessingRegistrationState;

public class SchedulerProcessingElevatorRequestState extends SchedulerState {
    protected final String msg;
    protected final int id;
    public SchedulerProcessingElevatorRequestState(String msg) {
        super();
        this.id = Integer.parseInt(msg.split(",")[0].replace("g5", "").strip());
        this.msg = msg;
    }
    @Override
    public void handle() {
        String action = msg.split(",")[1].strip();

        // mini router, find the appropriate state
        if(action.equals("register")) {
            SchedulerSystem.setState(new SchedulerProcessingRegistrationState(msg, id, SchedulerSystem.elevators));
        } else if (action.equals("addInstruction")) {
            SchedulerSystem.setState(new ProcessingFloorAddInstructionState(msg));
        } else if (action.equals("event")) {
            SchedulerSystem.setState(new ProcessingForwardEventState(msg));
        } else {
            System.out.printf("SCHEDULER ERROR: Unknown action \"%s\", moving back to idle\n", action);
            SchedulerSystem.setState(new SchedulerIdleState());
        }
    }
}

