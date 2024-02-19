package src.elevator;

import src.SchedulerSystem;
import src.elevator.elevator_state.ElevatorState;
import src.events.Event;
import src.instruction.Instruction;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Elevator Sub-system which manages elevator-related behavior.
 * This class represents an elevator node that receives instructions from the scheduler system
 * and performs elevator operations accordingly.
 *
 * Authors: Arun, Hamza, Mahad, Nabeel, Zarif
 * Version: 1.0
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
     * Constructs an ElevatorNode object with default values.
     * Initializes elevator properties such as id, current floor, altitude, velocity, state, and data structures.
     */
    public ElevatorNode() {
        id = ElevatorNode.nextId++;
        currentFloor = 0;
        altitude = 0.0f;
        velocity = 0.0f;
        // TODO: init state
        destinations = new ArrayList<>();
        log = new ArrayList<>();
        pendingInstructions = new ArrayList<>();
    }

    /**
     * Sets the state of the elevator.
     *
     * @param state the state to set
     */
    public void setState(ElevatorState state) {
        this.state = state;
    }

    /**
     * Determines the pickup index for the given instruction.
     * This method calculates the index at which the pickup floor should be inserted into the destinations list.
     *
     * @param instruction the instruction containing pickup and destination floors
     * @return the index where the pickup floor should be inserted
     */
    public int getPickupIndex(Instruction instruction) {
        // TODO: implement method
        return 0;
    }

    /**
     * Adds a pickup instruction to the pending instructions list.
     *
     * @param instruction the pickup instruction to add
     */
    public void addPickup(Instruction instruction) {
        pendingInstructions.add(instruction);
        destinations.add(getPickupIndex(instruction), instruction.getPickupFloor());
    }

    public boolean getAvailableInstruction(){

        if (!destinations.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public float getVelocity(){

        return velocity;
    }



    /**
     * Overrides the run method of Thread class.
     * This method is the entry point for the elevator thread.
     * It continuously receives instructions from the scheduler system and processes them.
     */
    @Override
    public void run() {
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
