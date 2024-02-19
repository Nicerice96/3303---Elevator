package src;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;

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
public class SchedulerSystem {

    private static ArrayList <ElevatorNode> elevatorNodes = new ArrayList<>();

    /**
     * Blocking queue to hold instructions between the floor subsystem and elevator subsystem.
     * It ensures thread safety for adding and retrieving instructions.
     */
    private static BlockingQueue<Instruction> payloads = new ArrayBlockingQueue<>(10);

    /**
     * Adds an instruction to the scheduler system.
     *
     * @param instruction the instruction to be added
     */
    public static void addPayload(Instruction instruction) {
        try {
            payloads.put(instruction);
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
        if (payloads.isEmpty()) return null;
        try {
            return payloads.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main method of the SchedulerSystem class.
     * It initializes and starts the floor subsystem and elevator subsystem threads.
     *
     * @param args command line arguments (not used)
     * @throws InterruptedException if any thread interrupts the current thread before or while the activity is in progress
     */
    public static void main(String[] args) throws InterruptedException {


        // Create an instance of FloorSubsystem and start its thread
        FloorSubsystem floorSubsystem = new FloorSubsystem("testCase_1.txt");
        floorSubsystem.setName("floorSubsystem");
        floorSubsystem.start();
        floorSubsystem.join();

        // Create an instance of ElevatorNode and start its thread


        ElevatorNode E1 = new ElevatorNode();
        E1.setName("elevatorSubsystem");
        E1.start();
        elevatorNodes.add(E1);

    }
}
