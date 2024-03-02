package src;

import src.events.Event;
import src.scheduler_state.SchedulerState;
import src.scheduler_state.SchedulerIdleState;
import src.instruction.Instruction;
import src.elevator.ElevatorNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {
    // [elevator.id, port]
    private static final HashMap<Integer, Integer> elevators = new HashMap<>();
    // [floor.floor, port]
    private static final HashMap<Integer, Integer> floors = new HashMap<>();
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
        // TODO: move this to SchedulerProcessingFloorRequestState and update it to adapt to the sockets
//        for (Instruction i : instructions) {
//            int min = Integer.MAX_VALUE;
//            ElevatorNode elevatorNode = null;
//            for (ElevatorNode e : elevatorNodes) {
//                int pickupIndex = e.getPickupIndex(i);
//                if (pickupIndex < min) {
//                    min = pickupIndex;
//                    elevatorNode = e;
//                }
//            }
//            if (elevatorNode != null) {
//                elevatorNode.addPickup(i);
//                instructions.remove(i);
//            }
//        }
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

    public static boolean receivedData() {
        return !instructions.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        SchedulerSystem.setSchedulerState(new SchedulerIdleState());
    }
}
