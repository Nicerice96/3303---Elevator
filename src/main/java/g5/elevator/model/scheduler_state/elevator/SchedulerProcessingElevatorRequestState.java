package g5.elevator.model.scheduler_state.elevator;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.scheduler_state.SchedulerState;
import g5.elevator.model.scheduler_state.floor.ProcessingFloorAddInstructionState;
import g5.elevator.model.scheduler_state.SchedulerProcessingRegistrationState;

public class SchedulerProcessingElevatorRequestState extends SchedulerState {
    protected final String msg;
    protected final int id;
    public SchedulerProcessingElevatorRequestState(SchedulerSystem context, String msg) {
        super(context);
        this.id = Integer.parseInt(msg.split(",")[0].replace("elevator", "").strip());
        this.msg = msg;
    }
    @Override
    public void run() {
        String action = msg.split(",")[1].strip();

        // mini router, find the appropriate state
        switch (action) {
            case "register" ->
                    context.setState(new SchedulerProcessingRegistrationState(context, msg, id, context.elevators));
            case "addInstruction" -> context.setState(new ProcessingFloorAddInstructionState(context, msg));
            case "event" -> context.setState(new ProcessingForwardEventState(context, msg));
            default -> {
                System.out.printf("SCHEDULER ERROR: Unknown action \"%s\", moving back to idle\n", action);
                context.setState(new SchedulerIdleState(context));
            }
        }
    }
}

