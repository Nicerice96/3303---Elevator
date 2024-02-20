package src.scheduler_state;

import src.SchedulerSystem;
import src.elevator.elevator_state.ElevatorMovingState;

public class SchedulerProcessingRequestState extends SchedulerState  {
    @Override
    public void handle() {

        SchedulerSystem.pollElevators();

        if (!SchedulerSystem.receievedData()) {
            SchedulerSystem.setState(new SchedulerIdleState());
        }

    }
}
