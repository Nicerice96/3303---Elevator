package src.scheduler_state.floor;

import src.SchedulerSystem;
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
        String action = msg.split(",")[1];

        // mini router, find the appropriate state
        if(action.equals("register")) {
            SchedulerSystem.setState(new ProcessingFloorRegistrationState(msg));
        } else {
            System.out.printf("SCHEDULER ERROR: Uknown action \"%s\", moving back to idle\n", action);
        }
    }
}
