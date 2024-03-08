package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

public class ElevatorProcessingCommState extends ElevatorCommState {
    public ElevatorProcessingCommState(ElevatorNode context, String msg) {
        super(context, msg);
    }

    @Override
    public void run() {
        // TODO: if this is getPickupIndex or addPickup

    }
}
