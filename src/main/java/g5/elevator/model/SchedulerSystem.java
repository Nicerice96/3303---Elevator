package g5.elevator.model;

import g5.elevator.controllers.Updatable;
import g5.elevator.model.events.Event;
import g5.elevator.defs.Defs;
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
    private ArrayList<Event> log = new ArrayList<>();
    private final BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(128);
    private SchedulerState state;
    public volatile boolean running = true; // Flag to indicate if the scheduler system should keep running
    // id, port
    public HashMap<Integer, Integer> elevators = new HashMap<>();
    // floor, port
    public HashMap<Integer, Integer> floors = new HashMap<>();

    public DatagramSocket sSocket;
    public DatagramSocket rSocket;
    private final Updatable controller;

    public SchedulerSystem() {
        this(null);
    }
    public SchedulerSystem(Updatable controller) {
        this.controller = controller;
        try {
            rSocket = new DatagramSocket(Defs.SCHEDULER_PORT);
            sSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    public void close() {
        running = false;
        sSocket.close();
        rSocket.close();
    }

    public void addInstruction(Instruction instruction) {
        try {
            instructions.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Instruction getInstruction() {
        if (instructions.isEmpty()) return null;
        try {
            return instructions.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearInstructions() {
        instructions.clear();
    }

    public void addEvent(Event event) {
        log.add(event);
        updateController();
        System.out.println(event);
    }

    public SchedulerState getSchedulerState() {
        return state;
    }

    public void setState(SchedulerState state) {
        this.state = state;
        updateController();
        this.state.start();
    }
    /**
     * Calls the update method on the controller, use with a UI
     */
    public void updateController() {
        if(controller == null) return;
        controller.update();
    }
    public ArrayList<Event> getLog() { return log; }

    @Override
    public void run() {
        System.out.println("Scheduler Online.");
        setState(new SchedulerIdleState(this));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        SchedulerSystem s = new SchedulerSystem();
        s.start();
    }
}