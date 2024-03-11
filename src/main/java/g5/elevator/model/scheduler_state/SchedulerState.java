package g5.elevator.model.scheduler_state;

import g5.elevator.model.SchedulerSystem;

public abstract class SchedulerState extends Thread {
    protected final SchedulerSystem context;
    public SchedulerState(SchedulerSystem context) {
        this.context = context;
    }
}
