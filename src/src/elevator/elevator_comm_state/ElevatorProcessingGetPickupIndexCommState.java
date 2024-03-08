package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

public class ElevatorProcessingGetPickupIndexCommState extends ElevatorProcessingCommState {
    public ElevatorProcessingGetPickupIndexCommState(ElevatorNode context, String msg) {
        super(context, msg);
    }
    @Override
    public void run() {
        // TODO: parse msg and return the pickup index
    }
}
