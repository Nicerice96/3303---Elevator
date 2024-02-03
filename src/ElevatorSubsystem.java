import java.util.ArrayList;

import static java.lang.Math.abs;
enum ElevatorStatus {
    OK, UNLOADING, LOADING
}
enum ElevatorDirection {
    DOWN, STATIONARY, UP
}


public class ElevatorSubsystem extends Thread {


    private ArrayList<Object> elevatorData;

    private Object data;
    private final long timeAdjacent = 8628;
    private final float timeDoorOpen = 3.36428571F;
    private final float timeDoorClose = 3.32428571F;
    private final float loadTime = 4.71428571F;
    private final float unloadTime = 4.71285714F;
    private int destination; //WHERE I NEED TO GO
    private ElevatorStatus status;
    private ElevatorDirection direction;
    private int currentFloor = 0;
    private int callFloor; //Floor on which the elevator was called

    ElevatorSubsystem(){
        elevatorData = new ArrayList<>();
    }

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


    public boolean differenceBetweenDestinationAndCurrentFloor(){
        if(abs(destination- currentFloor) == 0){

            return true;
        }
        else{

            return false;
        }


    }

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

    @Override
    public void run(){
        while (true) {
            ArrayList<Object> data = SchedulerSystem.getData();
            if (data == null) {
                System.out.println("No more data from Scheduler");
                break;
            }
            System.out.println("Receiving data from Scheduler: " + data);
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
