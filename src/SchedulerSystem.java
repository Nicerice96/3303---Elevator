import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {

    private static BlockingQueue<ArrayList<Object>> floorDataQueue = new ArrayBlockingQueue<>(10);
    private static BlockingQueue<ArrayList<Object>> elevatorDataQueue = new ArrayBlockingQueue<>(10);

    public static boolean elevatorArrived = true;

    @Override
    public void run() {
        while (true) {
            try {
                ArrayList<Object> data = floorDataQueue.take();
                System.out.println("Scheduler Received data from Floor: " + data);



                if(elevatorArrived) {

                    elevatorArrived = false;
                    elevatorDataQueue.put(data);
                    System.out.println("Sending data to Elevator: " + data);
                }

                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void putData(ArrayList<Object> data) {
        try {
            floorDataQueue.put(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Object> getData() {
        try {
            return elevatorDataQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        int i = 0;

        while(i < 4) {

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

            i++;
        }
    }
}
