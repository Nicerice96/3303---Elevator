package src;

import java.io.File;
import java.util.ArrayList;
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
        ArrayList<Object> dataObjectList = new ArrayList<>();
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

                dataObjectList.add(dataList[0]);
                dataObjectList.add(dataList[1]);
                dataObjectList.add(dataList[2]);
                dataObjectList.add(dataList[3]);

                SchedulerSystem.putData(new ArrayList<>(dataObjectList));

                dataObjectList.clear();
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