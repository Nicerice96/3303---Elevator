package src;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Scheduler Sub-system which carries out Scheduler related behaviour
 * @authors Zarif Nabeel Arun Hamza
 * @version 1.0
 */

public class SchedulerSystem {

    private static BlockingQueue<Instruction> payloads = new ArrayBlockingQueue<>(10);
    public static long GLOBAL_TIME = System.currentTimeMillis();//0; //this is not implemented yet...but basically we want a mechanism that tracks the time from the start of
    //application to the end, (note: the first element in the text file is the time at which the button is pressed)


    public static void addPayload(Instruction instruction) {
        try {
            payloads.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Instruction getPayload() {
        // TODO: Check if this requires a lock as it *might* end the
        //  program too soon if getPayload() is called before putData() - Hamza
        if (payloads.isEmpty()) return null;
        try {
            return payloads.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // return the elapsed time in milliseconds since program started
    public long getElapsedTime(){
        return System.currentTimeMillis() - SchedulerSystem.GLOBAL_TIME;
    }

    public static void main(String[] args) throws InterruptedException {
        // Create an instance of FloorSubsystem and start its thread
        FloorSubsystem floorSubsystem = new FloorSubsystem("testCase_1.txt");
        floorSubsystem.setName("floorSubsystem");
        floorSubsystem.start();
        floorSubsystem.join();

        ElevatorNode elevatorSubsystem = new ElevatorNode();
        elevatorSubsystem.setName("elevatorSubsystem");
        elevatorSubsystem.start();
    }
}