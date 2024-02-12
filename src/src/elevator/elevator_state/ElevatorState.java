package src.elevator.elevator_state;

import src.elevator.ElevatorNode;

public abstract class ElevatorState {
    public abstract ElevatorState handle(ElevatorNode context);
}
