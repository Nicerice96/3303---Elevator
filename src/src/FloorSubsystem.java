package src;

import src.payload.Direction;
import src.payload.Payload;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Floor Sub-system which carries out floor related behaviour
 * @authors Zarif Nabeel Arun Hamza
 * @version 1.0
 */
public class FloorSubsystem extends Thread {
    private String filename;
    public FloorSubsystem(String filename) {
        super();
        this.filename = filename;
    }

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
                    pickupFloor = Integer.parseInt(dataList[2]);
                    destinationFloor = Integer.parseInt(dataList[3]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("Error parsing timestamp/pickupFloor/destinationFloor, check FloorSybsystem.parseData()!!");
                    System.exit(1);
                }

                Payload payload = new Payload(timestamp,
                        dataList[1].equals("DOWN") ? Direction.DOWN : Direction.UP,
                        pickupFloor, destinationFloor);

                SchedulerSystem.addPayload(payload);
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR :: FloorSubsystem :: parseData() " + e);
        }
    }

    @Override
    public void run() {

        parseData();

    }
}