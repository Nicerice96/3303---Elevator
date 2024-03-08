package src;

import src.events.Event;
import src.scheduler_state.SchedulerState;
import src.scheduler_state.SchedulerIdleState;
import src.instruction.Instruction;
import src.elevator.ElevatorNode;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static src.defs.Defs.SCHEDULER_PORT;

public class SchedulerSystem extends Thread {
    private static ArrayList<Event> log = new ArrayList<>();
    private static BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(10);
    private static SchedulerState state;
    public static volatile boolean running = true; // Flag to indicate if the scheduler system should keep running
    public static HashMap<Integer, Integer> elevators = new HashMap<>();
    public static HashMap<Integer, Integer> floors = new HashMap<>();

    public static DatagramSocket sSocket;
    public static DatagramSocket rSocket;

    static {
        try {
            rSocket = new DatagramSocket(SCHEDULER_PORT);
            sSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    public static void stopScheduler(boolean flag) {
        running = false;
    }

    public static void addInstruction(Instruction instruction) {
        try {
            instructions.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Instruction getInstruction() {
        if (instructions.isEmpty()) return null;
        try {
            return instructions.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearInstructions() {
        instructions.clear();
    }

    public static void pollElevators() {
        // TODO: move this to ProcessingFloorAddInstructionState
        System.out.println("polling?");
        for (Instruction i : instructions) {
            int min = Integer.MAX_VALUE;
            ElevatorNode elevatorNode = null;
//            for (ElevatorNode e : elevatorNodes) {
//                int pickupIndex = e.getPickupIndex(i);
//                if (pickupIndex < min) {
//                    min = pickupIndex;
//                    elevatorNode = e;
//                }
//            }
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

    public static void setState(SchedulerState state) {
        SchedulerSystem.state = state;
        SchedulerSystem.state.handle();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Scheduler Online.");
        SchedulerSystem.setState(new SchedulerIdleState());
    }
}