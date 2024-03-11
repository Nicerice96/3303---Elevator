package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;

public abstract class ElevatorState extends Thread {
    protected final ElevatorNode context;
    protected ElevatorState(ElevatorNode context) {
        this.context = context;
    }
}
