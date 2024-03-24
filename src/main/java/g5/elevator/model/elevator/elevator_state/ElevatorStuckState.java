package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;

public class ElevatorStuckState extends ElevatorState {
    protected ElevatorStuckState(ElevatorNode context) { super(context); }

    @Override
    public void run() {
        context.addEvent(new Event(EventType.ELEVATOR_STUCK, context.getElevatorId()));
        context.velocity = 0;
        context.close();
        context.addEvent(new Event(EventType.ELEVATOR_SHUTDOWN, context.getElevatorId()));
    }
}
