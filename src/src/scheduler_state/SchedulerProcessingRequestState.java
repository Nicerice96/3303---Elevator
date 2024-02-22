package src.scheduler_state;

import src.SchedulerSystem;
import src.elevator.elevator_state.ElevatorMovingState;
import src.events.Event;
import src.events.EventType;

public class SchedulerProcessingRequestState extends SchedulerState  {
    @Override
    public void handle() {

        while (SchedulerSystem.running) {

            while (SchedulerSystem.receievedData()) {
                SchedulerSystem.pollElevators();
            }

            SchedulerSystem.addEvent(new Event(EventType.SCHEDULER_MOVE_TO_IDLE));
            SchedulerSystem.setSchedulerState(new SchedulerIdleState());


        }
    }
}
