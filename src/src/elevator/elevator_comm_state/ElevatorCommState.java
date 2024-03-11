package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;

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
