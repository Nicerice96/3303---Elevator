package g5.elevator.model.elevator.elevator_comm_state;

import g5.elevator.model.elevator.ElevatorNode;

/**
 * General Comm State, acts as an interface and holds the common attributes
 * @authors Mahad, Nabeel, Hamza
 */
public abstract class ElevatorCommState extends Thread {
    protected final ElevatorNode context;
    public ElevatorCommState(ElevatorNode context) {
        this.context = context;
    }
}
