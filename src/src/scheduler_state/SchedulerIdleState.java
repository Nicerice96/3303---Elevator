package src.scheduler_state;

import src.SchedulerSystem;

public class SchedulerIdleState extends SchedulerState  {
    @Override
    public void handle() {
        while (SchedulerSystem.running) {
            if (SchedulerSystem.receivedData()){
                SchedulerSystem.setSchedulerState(new SchedulerProcessingFloorRequestState());
            }
        }
    }
}
