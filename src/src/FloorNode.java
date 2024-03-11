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
    private synchronized void parseData() {
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

    private boolean registerPort() {
        String msgString = String.format("floor %d,register,%d", this.floor, this.rSocket.getLocalPort());
        try {
            byte [] msg = msgString.getBytes();
            DatagramPacket registerFloorPacket = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), SCHEDULER_PORT);
            this.sSocket.send(registerFloorPacket);
            addEvent(new Event(EventType.SENT, msgString));
            String res = awaitConfirmation();
            if(res.equals("OK")) {
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

    private String awaitConfirmation() throws IOException {
        byte [] resByte = new byte[MSG_SIZE];
        DatagramPacket rPacket = new DatagramPacket(resByte, resByte.length);
        this.rSocket.receive(rPacket);
        String res = Defs.getMessage(rPacket.getData(), rPacket.getLength());
        addEvent(new Event(EventType.RECEIVED, res));
        return res;
    }


    private void sendInstructionPacket(Instruction instruction) {
        String msg = String.format("floor %d,addInstruction,%s", floor, instruction);
        byte [] data = msg.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), SCHEDULER_PORT);
            sSocket.send(packet);
            addEvent(new Event(EventType.SENT, msg));
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
        System.out.printf("Floor node %d Online\n", floor);
        System.out.println("Registering");
        registerPort();
        System.out.println("\nParsing data:");
        parseData();
        System.out.println("\nListening");
        while(true) {
            byte[] rBytes = new byte[MSG_SIZE];
            DatagramPacket packet = new DatagramPacket(rBytes, rBytes.length);
            try {
                rSocket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("floor %d,received %s \n", floor, getMessage(rBytes, packet.getLength()));
        }
    }


    public static void main(String[] args) {
        final int FLOORS = 4;
        for (int i = 0; i < FLOORS; i++) {
            FloorNode floorNode = new FloorNode(i, "testCase_1.txt");
            floorNode.setName("floorNode-" + i);
            floorNode.start();
        }
    }
}
