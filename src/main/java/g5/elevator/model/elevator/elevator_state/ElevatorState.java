package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;

public abstract class ElevatorState {
    public abstract void handle(ElevatorNode context);
}
