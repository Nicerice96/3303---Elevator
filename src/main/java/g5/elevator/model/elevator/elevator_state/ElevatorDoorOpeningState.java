package g5.elevator.model.elevator.elevator_state;

import g5.elevator.defs.ElevatorDefs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.elevator.ElevatorNode;

public class ElevatorDoorOpeningState extends ElevatorState {
    protected ElevatorDoorOpeningState(ElevatorNode context) {
        super(context);
    }

    @Override
    public void run() {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_OPENING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.DOOR_OPENING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(context.isDoorStuck()) {
            context.setState(new ElevatorDoorStuckState(context));
        } else {
            context.setState(new ElevatorDoorOpenState(context));
        }
    }
}
