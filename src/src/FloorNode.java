package src;

import src.defs.Defs;
import src.events.Event;
import src.events.EventType;
import src.instruction.Direction;
import src.instruction.Instruction;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static src.defs.Defs.*;

/**
 * Floor Sub-system which manages floor-related behavior.
 * This class reads instructions from a file, parses them, and sends them to the SchedulerSystem.
 *
 * @authors: Zarif, Nabeel, Arun, Hamza
 * @version: 1.0
 */
public class FloorNode extends Thread {

    private DatagramSocket sSocket;

    private DatagramSocket rSocket;
    private String filename;
    private final int floor;
    private ArrayList<Event> log;


    /**
     * Constructs a FloorSubsystem object with the given filename.
     *
     * @param filename the name of the file containing floor instructions
     */
    public FloorNode(int floor, String filename) {
        super();
        this.log = new ArrayList<>();
        this.floor = floor;
        this.filename = filename;

        try {
            sSocket = new DatagramSocket(7000 + this.floor);
            rSocket = new DatagramSocket(8000 + this.floor);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Parses the floor instruction data from the file and sends instructions to the SchedulerSystem.
     * This method ensures thread safety by synchronizing access to shared resources.
     */
    public synchronized void parseData() {
        String[] dataList;
        String[] previousLine = {"", "", "", ""};
        Scanner scanner;
        File file = new File(filename);
        try {
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataList = line.split(",");

                if (Arrays.equals(dataList, previousLine)) {
                    continue;
                }

                System.arraycopy(dataList, 0, previousLine, 0, dataList.length);
                LocalTime timestamp = LocalTime.parse(dataList[0], TIMESTAMP_FORMATTER);
                int pickupFloor = 0;
                int destinationFloor = 0;
                try {
                    pickupFloor = Integer.parseInt(dataList[1]);
                    destinationFloor = Integer.parseInt(dataList[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("Error parsing timestamp/pickupFloor/destinationFloor, check FloorSubsystem.parseData()!!");
                    System.exit(1);
                }
                // Skip instruction if the pickup floor is not the same as the floor node.
                if (this.floor != pickupFloor) continue;
                
                Instruction instruction = new Instruction(timestamp, dataList[2].equals("DOWN") ? Direction.DOWN : Direction.UP, pickupFloor, destinationFloor);
                sendInstructionPacket(instruction);
            }


            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR :: FloorSubsystem :: parseData() " + e);
        }
    }

    public boolean registerPort() {
        String msgString = String.format("floor %d,register,%d", this.floor, this.rSocket.getLocalPort());

        byte [] resByte = new byte[MSG_SIZE];

        try {
            byte [] msg = msgString.getBytes();
            DatagramPacket registerFloorPacket = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), SCHEDULER_PORT);
            DatagramPacket rPacket = new DatagramPacket(resByte, resByte.length);
            this.sSocket.send(registerFloorPacket);
            addEvent(new Event(EventType.SENT, msgString));
            this.rSocket.receive(rPacket);
            String res = Defs.getMessage(rPacket.getData(), rPacket.getLength());
            if(res.equals("OK")) {
                addEvent(new Event(EventType.RECEIVED, res));
                System.out.println("Registration complete!");
                return true;
            } else {
                System.out.println("Registration failed. Trying again...");
                return false;
            }
        } catch (UnknownHostException e){
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public void sendInstructionPacket(Instruction instruction){
        byte [] sendInstructionPacket = ("[1]" + instruction).getBytes();
        try {
            DatagramPacket instructionPacket = new DatagramPacket(sendInstructionPacket, sendInstructionPacket.length, InetAddress.getLocalHost(), 5000);
            sSocket.send(instructionPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addEvent(Event e) {
        this.log.add(e);
        System.out.println(e);
    }

    /**
     * Overrides the run method of Thread class.
     * Calls the parseData method to start processing floor instructions.
     */
    @Override
    public void run() {
        registerPort();
        while(true) {
//            do nothing, wait
        }
//        parseData();
    }


    public static void main(String[] args) {
        final int FLOORS = 5;
        for (int i = 0; i < FLOORS; i++) {
            FloorNode floorNode = new FloorNode(i, "testCase_1.txt");
            floorNode.setName("floorNode-" + i);
            floorNode.start();

        }
    }
}
