package src.elevator.elevator_state;

import src.elevator.ElevatorNode;
import src.defs.ElevatorDefs;

public class ElevatorDoorClosingState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {
        try {
            Thread.sleep(ElevatorDefs.DOOR_CLOSING_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ElevatorState s = new ElevatorDoorClosedState();
        context.setState(s);
        s.handle(context);
    }
}
