package src.elevator.elevator_state;

import src.elevator.ElevatorNode;
import src.defs.ElevatorDefs;
import src.events.Event;
import src.events.EventType;

public class ElevatorDoorClosingState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_CLOSING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.DOOR_CLOSING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_CLOSED, context.getElevatorId()));
        context.setState(new ElevatorIdleState());
    }
}
