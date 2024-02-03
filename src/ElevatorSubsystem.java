import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Describes the possible status of the elevator
 */
enum ElevatorStatus {
    OK, UNLOADING, LOADING
}

/**
 * Describes the possible directions the elevator can traverse in
 */
enum ElevatorDirection {
    DOWN, STATIONARY, UP
}


/**
 * Elevator System which carries out Elevator related behaviour
 * @authors Arun Hamza Mahad Nabeel Zarif
 * @version 1.0
 */

public class ElevatorSubsystem extends Thread {


    ArrayList<Object> elevatorData;

    private Object data;
    private final long timeAdjacent = 8628;
    private final float timeDoorOpen = 3.36428571F;
    private final float timeDoorClose = 3.32428571F;
    private final float loadTime = 4.71428571F;
    private final float unloadTime = 4.71285714F;
    int destination; //WHERE I NEED TO GO
    private ElevatorStatus status;
    ElevatorDirection direction;
    int currentFloor = 0;
    int callFloor; //Floor on which the elevator was called

    /**
     * Constructor which initializes the elevatorData
     */
    ElevatorSubsystem(){
        elevatorData = new ArrayList<>();
    }

    /**
     * Allows the elevator to traverse one floor
     */

    public void traverseOneFloor(){
        //Need to add to global time for each floor that the elevator travels towards destination
        // down=-1, stationary=0, up=1
        currentFloor += direction.ordinal()-1;
        try {
            sleep(timeAdjacent);
        } catch(Exception e){
            System.out.println("something happened...");
        }
        System.out.println("Current Floor: " + currentFloor);
    }

    /**
     * Describes if the elevator has reached the destination or not
     * @return
     */

    public boolean differenceBetweenDestinationAndCurrentFloor(){
        if(abs(destination- currentFloor) == 0){

            return true;
        }
        else{

            return false;
        }


    }

    /**
     * Set's the elevators destination
     * @return
     */

    public int setDestination(){
        this.data = elevatorData.get(3);
        if (data != null) {
            this.destination = Integer.parseInt((String) data);
            System.out.println("Destination: " + destination);
            return this.destination;
        } else {
            System.out.println("Error in setDestination: e.elevatorData.get(3) is null and cannot be cast to int.");
            return 0;
        }
    }

    /**
     * Sets the direction of the elevator, in other words the caller's requested direction
     * @return
     */

    public String setDirection(){
        this.data = elevatorData.get(2);
        if (data instanceof String) {
            String directionString = (String) data;
            this.direction =
                    directionString.equals("DOWN") ? ElevatorDirection.DOWN :
                    directionString.equals("UP") ? ElevatorDirection.UP : ElevatorDirection.STATIONARY;
            System.out.println("setDirection: " + this.direction);
            return directionString;
        } else {
            System.out.println("The object is not an Integer and cannot be cast to int.");
            return "";
        }
    }

    /**
     * Sets the floor at which the elevator was requested
     * @return
     */

    public int setElevatorCallFloor() {
        this.data = elevatorData.get(1);
        if (data != null) {
            this.callFloor = Integer.parseInt((String) data);
            System.out.println("Elevator Call Floor: " + this.callFloor);
            return this.callFloor;
        } else {
            System.out.println("The object is not an Integer and cannot be cast to int.");
            return 0;
        }
    }

    /**
     * Allows Elevator to traverse to caller floor
     */

    public void traverseToElevatorCall(){
        // Need to add implementation to add to global time for each floor that the elevator traverses to reach caller
        while(currentFloor != callFloor){
            if(currentFloor > callFloor){
                currentFloor--;
            } else {
                currentFloor++;
            }
            try {
                sleep(timeAdjacent); //time taken from iteration 0
            }
            catch(Exception e){
                System.out.println("something happened...");
            }
        }
    }

    /**
     * Simulates the Door opening action of an elevator
     */

    public void doorOpen(){
        try {
            System.out.println("Opening doors...");
            Thread.sleep((long) (timeDoorOpen * 1000));
            System.out.println("Doors opened.");
            //GLOBAL VARIABLE UPDATES HERE
        } catch (InterruptedException e) {
            System.out.println("Interrupted while opening doors: " + e.getMessage());
        }
    }

    /**
     * Simulates the Door closing action of an elevator
     */
    public void doorClose(){
        try {
            System.out.println("Closing doors...");
            Thread.sleep((long) (timeDoorClose * 1000));
            System.out.println("Doors closed.");
            //GLOBAL VARIABLE GOES HERE ASWELL
        } catch (InterruptedException e) {
            System.out.println("Interrupted while closing doors: " + e.getMessage());
        }
    }

    /**
     * Simulates people entering the elevator
     */

    public void peopleLoad(){
        try {
            System.out.println("Loading people...");
            Thread.sleep((long) (loadTime * 1000));
            System.out.println("People loaded.");
            //UPDATE GLOBAL VARIABLE HERE
        } catch (InterruptedException e) {
            System.out.println("Interrupted while loading people: " + e.getMessage());
        }
    }

    /**
     * Simulates people getting off the elevator
     */

    public void peopleUnload(){
        try {
            System.out.println("Unloading people...");
            Thread.sleep((long) (unloadTime * 1000));
            System.out.println("People unloaded.");
            //UPDATE GLOBAL VAR HERE
        } catch (InterruptedException e) {
            System.out.println("Interrupted while unloading people: " + e.getMessage());
        }
    }

    /**
     * Allows the Elevator thread to run
     */
    @Override
    public void run(){
        while (true) {
            ArrayList<Object> data = SchedulerSystem.getData();
            if (data == null) {
                System.out.println("No more data from Scheduler");
                break;
            }
            System.out.println("Receiving data from Scheduler: " + data);
            this.elevatorData.clear();
            this.elevatorData.addAll(data);

            setDestination();
            setDirection();
            setElevatorCallFloor();

            traverseToElevatorCall();
            System.out.println("Elevator arrived at: " + this.currentFloor + " for pick up on floor " + this.callFloor);

            doorOpen();
            peopleLoad();
            doorClose();

            while (!differenceBetweenDestinationAndCurrentFloor()) {
                traverseOneFloor();
            }

            doorOpen();
            peopleUnload();
            doorClose();

            System.out.println("Elevator arrived at Floor: " + currentFloor + " for drop off");
            this.currentFloor = this.destination;
        }
    }
}
