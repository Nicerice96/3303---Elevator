package src.elevator.elevator_state;

import src.defs.ElevatorDefs;
import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;

public class ElevatorDoorOpenState extends ElevatorState {

    @Override
    public void handle(ElevatorNode context) {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_OPEN, context.getElevatorId()));
        context.unwrapPendingInstructions();
        context.addEvent(new Event(EventType.ELEVATOR_UNLOADING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.UNLOADING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.addEvent(new Event(EventType.ELEVATOR_LOADING, context.getElevatorId()));
        try {
            Thread.sleep(ElevatorDefs.LOADING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.setState(new ElevatorDoorClosingState());
    }
}
