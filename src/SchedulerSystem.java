import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Acts as an intermediate messanger between FloorSubsystem and ElevatorSubsytem
 * @authors Arun Hamza Mahad Nabeel Zarif
 * @version 1.0
 */
public class SchedulerSystem {

    private static BlockingQueue<ArrayList<Object>> floorDataQueue = new ArrayBlockingQueue<>(10);
    protected static final Object floorQueueLock = new Object();
    protected static final Object elevatorQueueLock = new Object();
    public static long GLOBAL_TIME = System.currentTimeMillis();//0; //this is not implemented yet...but basically we want a mechanism that tracks the time from the start of
    //application to the end, (note: the first element in the text file is the time at which the button is pressed)

    /**
     * Places incoming data into the floorQueue from storage
     * @param data
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

    /**
     * Retrieves data from the elevatorQueue
     * @return
     */
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

    /**
     * return the elapsed time in milliseconds since program started
     * @return
     */
    public long getElapsedTime(){
        return SchedulerSystem.GLOBAL_TIME - System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {


            SchedulerSystem schedulerSystem = new SchedulerSystem();

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
