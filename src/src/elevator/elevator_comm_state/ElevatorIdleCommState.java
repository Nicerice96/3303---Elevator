package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

public class ElevatorIdleCommState extends ElevatorCommState {
    public ElevatorIdleCommState(ElevatorNode context) {
        super(context, "");
    }

    @Override
    public void run() {
        // TODO: busy wait until a packet is received.

    }
}
