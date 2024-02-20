package src;

import src.events.Event;
import src.scheduler_state.SchedulerState;
import src.scheduler_state.SchedulerIdleState;
import src.instruction.Instruction;
import src.elevator.ElevatorNode;
import src.events.EventType;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {
    private static ArrayList<ElevatorNode> elevatorNodes = new ArrayList<>();
    private static ArrayList<Event> log = new ArrayList<>();
    private static BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(10);
    private static SchedulerState state;
    public static volatile boolean running = true; // Flag to indicate if the scheduler system should keep running

    public static void stopScheduler(boolean flag) {
        if (true) {
            running = false;
        }
        else{
            running = true;
        }
    }

    public static void addPayload(Instruction instruction) {
        try {
            instructions.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Instruction getPayload() {
        if (instructions.isEmpty()) return null;
        try {
            return instructions.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearPayLoad() {
        instructions.clear();
    }

    public static void pollElevators() {
        System.out.println("polling?");
        for (Instruction i : instructions) {
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

    public static void addEvent(Event event) {
        log.add(event);
        System.out.println(event);
    }

    public static SchedulerState getSchedulerState() {
        return state;
    }

    public static void setSchedulerState(SchedulerState state) {
        SchedulerSystem.state = state;
        SchedulerSystem.state.handle();
    }

    public static boolean receievedData() {
        return !instructions.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        final int FLOOR_NUM = 4;
        final int ELEVATOR_NUM = 3;

        for (int i = 0; i < FLOOR_NUM; i++) {
            FloorNode floorSubsystem = new FloorNode(i, "testCase_1.txt");
            floorSubsystem.setName("floorSubsystem-" + i);
            floorSubsystem.start();
            floorSubsystem.join();
        }

        for (int i = 0; i < ELEVATOR_NUM; i++) {
            ElevatorNode e = new ElevatorNode();
            e.setName("elevatorNode-" + i);
            e.start();
            elevatorNodes.add(e);
        }

        SchedulerSystem.setSchedulerState(new SchedulerIdleState());

        // Keep the scheduler system running for some time (for testing purpose)
        Thread.sleep(5000); // Sleep for 5 seconds

        // Stop the scheduler system (for testing purpose)
        stopScheduler(true);
    }
}
