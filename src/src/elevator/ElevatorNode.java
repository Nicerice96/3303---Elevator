package src.elevator;

import src.SchedulerSystem;
import src.elevator.elevator_state.ElevatorState;
import src.events.Event;
import src.payload.Payload;

import java.util.ArrayList;
import java.util.Queue;

import static java.lang.Math.abs;




/**
 * Elevator Sub-system which carries out Elevator related behaviour
 * @authors Arun Hamza Mahad Nabeel Zarif
 * @version 1.0
 */
public class ElevatorNode extends Thread {
    private static int nextId = 0;
    private final int id;
    private int currentFloor;
    private float altitude;
    private float velocity;
    private ElevatorState state;
    private int nextDestination;
    private ArrayList<Integer> destinations;
    private ArrayList<Event> log;
    private ArrayList<Payload> pendingPayloads;

    /**
     * Constructor which initializes the elevatorData
     */
    public ElevatorNode(){
        id = ElevatorNode.nextId++;
        currentFloor = 0;
        altitude = 0.0f;
        velocity = 0.0f;
        // TODO: init state
        destinations = new ArrayList<>();
        log = new ArrayList<>();
        pendingPayloads = new ArrayList<>();
    }


    public void setState(ElevatorState state) { this.state = state; }

    public int getPickupIndex() {
        // TODO: implement method
        return 0;
    }

    public void addPickup(Payload payload) {
        pendingPayloads.add(payload);
        destinations.add(getPickupIndex(), payload.getPickupFloor());
    }

    /**
     * Allows the Elevator thread to run
     */
    @Override
    public void run(){
        while (true) {
            Payload payload = SchedulerSystem.getPayload();
            if (payload == null) {
                System.out.println("No more data from Scheduler");
                break;
            }
            System.out.println("Receiving data from Scheduler: " + payload);
//            this.elevatorData.clear();
//            this.elevatorData.addAll(payload);


            System.out.println("Elevator arrived at Floor: " + currentFloor + " for drop off");
//            this.currentFloor = this.destination;
        }
    }
}