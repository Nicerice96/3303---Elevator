package src;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {

    private static BlockingQueue<ArrayList<Object>> floorDataQueue = new ArrayBlockingQueue<>(10);
    private static BlockingQueue<ArrayList<Object>> elevatorDataQueue = new ArrayBlockingQueue<>(10);

    protected static final Object floorQueueLock = new Object();
    protected static final Object elevatorQueueLock = new Object();

    public volatile static boolean elevatorArrived = true;



    public static float globalTime = 0; //this is not implemented yet...but basically we want a mechanism that tracks the time from the start of
    //application to the end, (note: the first element in the text file is the time at which the button is pressed)

    public SchedulerSystem(){
    }
    @Override
    public void run() {
            try {
                    ArrayList<Object> data = floorDataQueue.take();
                    //Takes the first item of floorDataQueue and puts it into data when floorDataQueue gets an arraylist

                    if (elevatorArrived) {


                        elevatorDataQueue.put(data);
                        //Puts the single item that is in data into the elevatorDataQueue
                        System.out.println("Sending data to Elevator: " + data);
                        elevatorArrived = false;
                        //manager.incrementCoffeesBrewed();
                    } else {

                        System.out.println("Elevator is running...");
                    }

                    sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }
// Why is putData not a synchronized method?
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

        synchronized (elevatorQueueLock) {
            try {
                return elevatorDataQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SchedulerSystem schedulerSystem = new SchedulerSystem();
        schedulerSystem.setName("schedulerSystem");
        schedulerSystem.start();
        //for (int i = 0; i < 3; i++) {
            // Create an instance of FloorSubsystem and start its thread
            FloorSubsystem floorSubsystem = new FloorSubsystem("testCase_1.txt");
            floorSubsystem.setName("floorSubsystem");
            floorSubsystem.start();
            //floorSubsystem.join();

            ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
            elevatorSubsystem.setName("elevatorSubsystem");
            elevatorSubsystem.start();
        //}


    }
}
