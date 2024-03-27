package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;

import static g5.elevator.defs.ElevatorDefs.DOOR_STUCK_TIME;

public class ElevatorDoorStuckState extends ElevatorState {
    protected ElevatorDoorStuckState(ElevatorNode context) {
        super(context);
    }

    @Override
    public void run() {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_STUCK, context.getElevatorId()));
        try {
            Thread.sleep(DOOR_STUCK_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.resetDoorStuck();
        context.setState(new ElevatorDoorOpeningState(context));
    }
}
