package g5.elevator.model;

import g5.elevator.defs.Defs;
import g5.elevator.model.events.Event;
import g5.elevator.model.scheduler_state.SchedulerState;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.instruction.Instruction;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SchedulerSystem extends Thread {
    private static ArrayList<Event> log = new ArrayList<>();
    public static BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(128);
    private static SchedulerState state;
    public static volatile boolean running = true; // Flag to indicate if the scheduler system should keep running
    // id, port
    public static HashMap<Integer, Integer> elevators = new HashMap<>();
    // floor, port
    public static HashMap<Integer, Integer> floors = new HashMap<>();

    public static DatagramSocket sSocket;
    public static DatagramSocket rSocket;

    static {
        try {
            rSocket = new DatagramSocket(Defs.SCHEDULER_PORT);
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