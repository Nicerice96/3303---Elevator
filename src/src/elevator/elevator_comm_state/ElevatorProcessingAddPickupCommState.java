package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

public class ElevatorProcessingAddPickupCommState extends ElevatorProcessingCommState {
    public ElevatorProcessingAddPickupCommState(ElevatorNode context, String msg) {
        super(context, msg);
    }

    @Override
    public void run() {
        // TODO: call addPickup and return OK
    }
}
