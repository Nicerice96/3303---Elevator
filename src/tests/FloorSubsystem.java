package tests;

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

    private String[] dataList;
    private String[] previousLine = {"", "", "", ""};
    private ArrayList<Object> dataObjectList = new ArrayList<>();
    private String filename;
    private File file;
    private static Scanner scanner;
    private boolean flag = true;

    FloorSubsystem(String filename) {
        this.filename = filename;
        file = new File(this.filename);
    }

    public synchronized void parseData() {
        try {
            scanner = new Scanner(this.file);

            while (flag && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                this.dataList = line.split(",");

                if (Arrays.equals(dataList, previousLine)) {
                    continue;
                }

                System.arraycopy(dataList, 0, previousLine, 0, dataList.length);

                dataObjectList.add(this.dataList[0]);
                dataObjectList.add(this.dataList[1]);
                dataObjectList.add(this.dataList[2]);
                dataObjectList.add(this.dataList[3]);

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