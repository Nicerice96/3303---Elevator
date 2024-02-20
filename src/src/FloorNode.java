package src;

import src.instruction.Direction;
import src.instruction.Instruction;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Floor Sub-system which manages floor-related behavior.
 * This class reads instructions from a file, parses them, and sends them to the SchedulerSystem.
 *
 * @authors: Zarif, Nabeel, Arun, Hamza
 * @version: 1.0
 */
public class FloorNode extends Thread {
    private String filename;
    private final int floor;

    /**
     * Constructs a FloorSubsystem object with the given filename.
     *
     * @param filename the name of the file containing floor instructions
     */
    public FloorNode(int floor, String filename) {
        super();
        this.floor = floor;
        this.filename = filename;
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
                int timestamp = 0;
                int pickupFloor = 0;
                int destinationFloor = 0;
                try {
                    timestamp = Integer.parseInt(dataList[0]);
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

                SchedulerSystem.addPayload(instruction);
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR :: FloorSubsystem :: parseData() " + e);
        }
    }

    /**
     * Overrides the run method of Thread class.
     * Calls the parseData method to start processing floor instructions.
     */
    @Override
    public void run() {
        parseData();
    }
}
