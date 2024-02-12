package src;

import src.elevator.ElevatorSubsystem;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Scheduler Sub-system which carries out Scheduler related behaviour
 * @authors Zarif Nabeel Arun Hamza
 * @version 1.0
 */

public class SchedulerSystem {

    private static BlockingQueue<ArrayList<Object>> floorDataQueue = new ArrayBlockingQueue<>(10);
    protected static final Object floorQueueLock = new Object();
    protected static final Object elevatorQueueLock = new Object();
    public static long GLOBAL_TIME = System.currentTimeMillis();//0; //this is not implemented yet...but basically we want a mechanism that tracks the time from the start of
    //application to the end, (note: the first element in the text file is the time at which the button is pressed)

    /**
     * Takes an ArrayList of type Object and puts its data into a blocked queue
     * @param data ArrayList containing data from the floor sub-system
     * @author Zarif
     * @author Nabeel
     */
    public static void putData(ArrayList<Object> data) {

        synchronized (floorQueueLock) {
            try {
                floorDataQueue.put(data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ArrayList<Object> getData() {
        // TODO: Check if this requires a lock as it *might* end the
        //  program too soon if getData() is called before putData() - Hamza
        if (floorDataQueue.isEmpty()) return null;
        synchronized (elevatorQueueLock) {
            try {
                return floorDataQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

        ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
        elevatorSubsystem.setName("elevatorSubsystem");
        elevatorSubsystem.start();


    }
}