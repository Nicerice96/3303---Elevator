package src.elevator.elevator_state;

import src.defs.ElevatorDefs;
import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;

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
