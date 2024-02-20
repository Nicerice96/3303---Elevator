package src;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;
import src.scheduler_state.SchedulerIdleState;
import src.scheduler_state.SchedulerState;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Scheduler Sub-system which carries out Scheduler related behavior.
 * This class manages the scheduling of instructions between the floor subsystem and elevator subsystem.
 * Instructions are passed through a blocking queue for synchronization.
 * @authors: Zarif, Nabeel, Arun, Hamza
 * @version: 1.0
 */
public class SchedulerSystem extends Thread {

    private static ArrayList <ElevatorNode> elevatorNodes = new ArrayList<>();

    /**
     * Blocking queue to hold instructions between the floor subsystem and elevator subsystem.
     * It ensures thread safety for adding and retrieving instructions.
     */
    private static BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(10);
    private static SchedulerState state;

    /**
     * Adds an instruction to the scheduler system.
     *
     * @param instruction the instruction to be added
     */
    public static void addPayload(Instruction instruction) {
        try {
            instructions.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an instruction from the scheduler system.
     *
     * @return the retrieved instruction, or null if the queue is empty
     */
    public static Instruction getPayload() {
        if (instructions.isEmpty()) return null;
        try {
            return instructions.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void pollElevators(){
        System.out.println("polling?");
        while (true) {



            for(Instruction i : instructions) {
                System.out.println("im here");
                int min = Integer.MAX_VALUE;
                ElevatorNode elevatorNode = null;
                for (ElevatorNode e : elevatorNodes) {
                    int pickupIndex = e.getPickupIndex(i);
                    if (pickupIndex < min) {
                        min = pickupIndex;
                        elevatorNode = e;
                    }
                }

                if (elevatorNode != null) {
                    elevatorNode.addPickup(i);
                    instructions.remove(i);
                }
            }
        }
    }


    public static boolean receievedData(){

        if (instructions.isEmpty()){
            return false;
        }
        else{
            return true;
        }



    }
    public static void setState(SchedulerState state) {
        SchedulerSystem.state = state;
        SchedulerSystem.state.handle();
    }

    /**
     * The main method of the SchedulerSystem class.
     * It initializes and starts the floor subsystem and elevator subsystem threads.
     *
     * @param args command line arguments (not used)
     * @throws InterruptedException if any thread interrupts the current thread before or while the activity is in progress
     */
    public static void main(String[] args) throws InterruptedException {
        final int FLOOR_NUM = 4;
        final int ELEVATOR_NUM = 3;



        // Create multiple instances of FloorNode and start its thread
        for(int i = 0; i < FLOOR_NUM; i++) {
            FloorNode floorSubsystem = new FloorNode(i, "testCase_1.txt");
            floorSubsystem.setName("floorSubsystem-"+i);
            floorSubsystem.start();
            floorSubsystem.join();
        }

        // Create multiple instances of ElevatorNode and start its thread
        for(int i = 0; i < ELEVATOR_NUM; i++) {
            ElevatorNode e = new ElevatorNode();
            e.setName("elevatorNode-"+i);
            e.start();
            elevatorNodes.add(e);
        }
        SchedulerSystem.setState(new SchedulerIdleState());

    }
}
