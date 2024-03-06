package src;

import src.events.Event;
import src.scheduler_state.SchedulerState;
import src.scheduler_state.SchedulerIdleState;
import src.instruction.Instruction;
import src.elevator.ElevatorNode;
import src.events.EventType;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchedulerSystem extends Thread {
    private static ArrayList<ElevatorNode> elevatorNodes = new ArrayList<>();
    private static ArrayList<Event> log = new ArrayList<>();
    private static BlockingQueue<Instruction> instructions = new ArrayBlockingQueue<>(10);
    private static SchedulerState state;
    public static volatile boolean running = true; // Flag to indicate if the scheduler system should keep running

    public static HashMap<Integer, DatagramPacket> floorPhoneBook = new HashMap<>();



    private DatagramSocket SchedulerSend;

    {
        try {
            SchedulerSend = new DatagramSocket(6000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static DatagramSocket SchedulerReceive;

    static {
        try {
            SchedulerReceive = new DatagramSocket(5000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean receivedData() {
        return !instructions.isEmpty();
    }


    public static ArrayList<Integer> decodePacket(String message){

        ArrayList<Integer> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(\\d+)\\]");

        // Match the pattern against the message
        Matcher matcher = pattern.matcher(message);

        // Check if the pattern is found and extract the number
        while (matcher.find()) {
            String numberStr = matcher.group(1);
            int number = Integer.parseInt(numberStr);
            results.add(number);

        }

        System.out.println(results);

        return results;


    }

//    public void receiveInstructionPacket(){
//
//        byte [] recievePacket = new byte[1024];
//        DatagramPacket recieveDatagramPacket = new DatagramPacket(recievePacket, recievePacket.length);
//
//        try {
//            SchedulerReceive.receive(recieveDatagramPacket);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void receivePacket() throws IOException {

        byte[] messagercv = new byte[1024];
        DatagramPacket rcvpacket = new DatagramPacket(messagercv, 1024);


                try {
                    SchedulerReceive.receive(rcvpacket);
                    ArrayList<Integer> encryptionNum = decodePacket(new String(rcvpacket.getData()));

                    if (encryptionNum.get(0) == 0) {


                        String message = new String(rcvpacket.getData());
                        System.out.println("OK receieved: " + message);


                        Pattern pattern = Pattern.compile("\\[(\\d+)\\]");

                        // Match the pattern against the message
                        Matcher matcher = pattern.matcher(message);

                        // Check if the pattern is found and extract the number
                        if (matcher.find()) {
                            String numberStr = matcher.group(1);
                            int number = Integer.parseInt(numberStr);
                            System.out.println("Number inside []: " + number);

                            floorPhoneBook.put(number, rcvpacket);
                        } else {
                            System.out.println("No number encased in [] found.");
                        }
                    } if (encryptionNum.get(0) == 1) {


                        byte[] recievePacket = new byte[1024];
                        DatagramPacket recieveDatagramPacket = new DatagramPacket(recievePacket, recievePacket.length);
                        try {
                            SchedulerReceive.receive(recieveDatagramPacket);

                            System.out.println("Received Instruction Packet: " + new String(recieveDatagramPacket.getData()));

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (IOException | RuntimeException e) {
                    throw new RuntimeException(e);
                }


    }



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

    public static void main(String[] args) throws InterruptedException, IOException {
        final int FLOOR_NUM = 4;
        final int ELEVATOR_NUM = 1;



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

        SchedulerSystem.receivePacket();

        SchedulerSystem.setSchedulerState(new SchedulerIdleState());


        // Keep the scheduler system running for some time (for testing purpose)
        Thread.sleep(5000); // Sleep for 5 seconds

        // Stop the scheduler system (for testing purpose)
        stopScheduler(true);
    }
}