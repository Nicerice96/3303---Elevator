package g5.elevator.model.elevator.elevator_state;

import g5.elevator.defs.ElevatorDefs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.elevator.ElevatorNode;

public class ElevatorDoorOpeningState extends ElevatorState {

    @Override
    public void handle(ElevatorNode context) {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_OPENING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.DOOR_OPENING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.setState(new ElevatorDoorOpenState());
    }
}
