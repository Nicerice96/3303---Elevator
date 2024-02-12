package src.elevator;

import src.SchedulerSystem;
import src.elevator.elevator_state.ElevatorState;
import src.events.Event;
import src.instruction.Instruction;

import java.util.ArrayList;

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
    private ArrayList<Integer> destinations;
    private ArrayList<Event> log;
    private ArrayList<Instruction> pendingInstructions;

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
        pendingInstructions = new ArrayList<>();
    }


    public void setState(ElevatorState state) { this.state = state; }

    public int getPickupIndex(Instruction instruction) {
        // TODO: implement method
        return 0;
    }

    public void addPickup(Instruction instruction) {
        pendingInstructions.add(instruction);
        destinations.add(getPickupIndex(instruction), instruction.getPickupFloor());
    }

    /**
     * Allows the Elevator thread to run
     */
    @Override
    public void run(){
        while (true) {
            Instruction instruction = SchedulerSystem.getPayload();
            if (instruction == null) {
                System.out.println("No more data from Scheduler");
                break;
            }
            System.out.println("Receiving data from Scheduler: " + instruction);
//            this.elevatorData.clear();
//            this.elevatorData.addAll(instruction);


            System.out.println("Elevator arrived at Floor: " + currentFloor + " for drop off");
//            this.currentFloor = this.destination;
        }
    }
}