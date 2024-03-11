package g5.elevator.model.elevator.elevator_state;

import g5.elevator.defs.ElevatorDefs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.elevator.ElevatorNode;

public class ElevatorDoorClosingState extends ElevatorState {
    protected ElevatorDoorClosingState(ElevatorNode context) {
        super(context);
    }
    @Override
    public void run() {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_CLOSING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.DOOR_CLOSING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_CLOSED, context.getElevatorId()));
        context.setState(new ElevatorIdleState(context));
    }
}
