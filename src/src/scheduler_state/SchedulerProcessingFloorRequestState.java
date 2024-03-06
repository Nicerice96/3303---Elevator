package src.scheduler_state;

import src.SchedulerSystem;
import src.events.Event;
import src.events.EventType;

public class SchedulerProcessingFloorRequestState extends SchedulerState  {
    @Override
    public void handle() {

        while (SchedulerSystem.running) {

            while (SchedulerSystem.receivedData()) {
                SchedulerSystem.pollElevators();
            }

            SchedulerSystem.addEvent(new Event(EventType.SCHEDULER_MOVE_TO_IDLE));
            SchedulerSystem.setSchedulerState(new SchedulerIdleState());


        }
    }
}
