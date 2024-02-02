import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {

    private static BlockingQueue<ArrayList<Object>> floorDataQueue = new ArrayBlockingQueue<>(10);
    private static BlockingQueue<ArrayList<Object>> elevatorDataQueue = new ArrayBlockingQueue<>(10);

    protected static final Object floorQueueLock = new Object();
    protected static final Object elevatorQueueLock = new Object();

    public volatile static boolean elevatorArrived = true;

    @Override
    public void run() {
        while (true) {
            try {
                ArrayList<Object> data = floorDataQueue.take();

                if(elevatorArrived) {


                    elevatorDataQueue.put(data);
                    System.out.println("Sending data to Elevator: " + data);
                }
                else{

                    System.out.println("Elevator is running...");
                }

                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
