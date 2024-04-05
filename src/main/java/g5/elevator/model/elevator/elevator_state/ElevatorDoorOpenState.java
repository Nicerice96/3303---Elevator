package g5.elevator.model.elevator.elevator_state;

import g5.elevator.defs.ElevatorDefs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.elevator.ElevatorNode;

public class ElevatorDoorOpenState extends ElevatorState {
    protected ElevatorDoorOpenState(ElevatorNode context) {
        super(context);
    }
    @Override
    public void run() {
        context.addEvent(new Event(EventType.ELEVATOR_DOOR_OPEN, context.getElevatorId()));
        context.unloadPassengers();
        try {
            Thread.sleep(ElevatorDefs.UNLOADING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.loadPassengers();
        try {
            Thread.sleep(ElevatorDefs.LOADING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.setState(new ElevatorDoorClosingState(context));
    }
}
