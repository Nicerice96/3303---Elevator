import java.util.ArrayList;

public class SchedulerSystem extends Thread {

    static Box box = new Box();

    SchedulerSystem() {
    }

    @Override
    public void run() {

        while (true) {
            ArrayList<Object> data = (ArrayList<Object>) box.get();

                System.out.println("Received data: " + data);

        }
    }

    public static void putData(ArrayList<Object> data) {
        box.put(data);
    }

    public static void main(String[] args) {

        SchedulerSystem schedulerSystem = new SchedulerSystem();
        schedulerSystem.start();


        FloorSubsystem floorSubsystem = new FloorSubsystem("testCase_1.txt");
        floorSubsystem.start();
    }
}
