package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

public class ElevatorProcessingCommState extends ElevatorCommState {
    protected final String msg;
    protected final int callbackPort;
    public ElevatorProcessingCommState(ElevatorNode context, String msg) {
        super(context);
        this.msg = msg;
        this.callbackPort = Integer.parseInt(msg.split(",")[0].replaceAll("[a-zA-Z]", "").strip());
    }

    @Override
    public void run() {
        // route
        String action = msg.split(",")[1];
        if (action.equals("addPickup")) {
            context.setCommState(new ElevatorProcessingAddPickupCommState(context, msg));
        } else if(action.equals("getPickupIndex")){
            context.setCommState(new ElevatorProcessingGetPickupIndexCommState(context, msg));
        }
    }
}
