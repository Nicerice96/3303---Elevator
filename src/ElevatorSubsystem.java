import java.util.ArrayList;

import static java.lang.Math.abs;

public class ElevatorSubsystem extends Thread{


    private ArrayList<Object> elevatorData;

    private Object data;


    private double timeAdjacent = 8.6276;

    private float timeDoorOpen;

    private float timeDoorClose;

    private float loadTime;

    private float unloadTime;

    private int destination; //WHERE I NEED TO GO

    private String direction; //UP DOWN


    private int currentElevatorFloor = 0;

    private int elevatorCallFloor; //Floor on which the elevator was called


    ElevatorSubsystem(){
        elevatorData = new ArrayList<Object>();

        this.timeDoorOpen = 3.36428571F;
        this.timeDoorClose = 3.32428571F;
        this.loadTime = 4.71428571F;
        this.unloadTime = 4.71285714F;

    }



    public void traverseOneFloor(){

        //Need to add to global time for each floor that the elevator travels towards destination

        if (direction.equals("UP")){
            currentElevatorFloor++;

        }
        else if (direction.equals("DOWN")){
            currentElevatorFloor--;
        }

        try {

            sleep(8628);
        }
        catch(Exception e){

            System.out.println("something happened...");

        }

        System.out.println("Current Floor: " + currentElevatorFloor);
        System.out.println("---------------------------------------------------");

    }


    public boolean differenceBetweenDestinationAndCurrentFloor(){
        //Why do we need an abs method here since we're checking to see if its 0? We can do it without it right?
        if(abs(destination-currentElevatorFloor) == 0){

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
             //converting the string to an int and storing it in destination. Need to do this as we compare destination with currentFloor (which is an int)

            System.out.println("Destination: " + destination);

            return this.destination;

        } else {
            System.out.println("The object is not an Integer and cannot be cast to int.");

            return 0;
        }


    }


    public String setDirection(){

        this.data = elevatorData.get(2);

        if (data instanceof String) {
            this.direction = (String) data;
            // Now you can use 'destination' as an int
            System.out.println("setDirection: " + this.direction);

            return this.direction;

        } else {
            System.out.println("The object cannot be cast to String.");
            return "";
        }



    }


    public int setElevatorCallFloor() {
        this.data = elevatorData.get(1);

        if (data != null) {
            this.elevatorCallFloor = Integer.parseInt((String) data);
            System.out.println("Elevator Call Floor: " + this.elevatorCallFloor);
            System.out.println("-------------------------------------------");
            return this.elevatorCallFloor;
        } else {
            System.out.println("The object is not an Integer and cannot be cast to int.");
            return 0;
        }
    }






    public void traverseToElevatorCall(){


        // Need to add implementation to add to global time for each floor that the elevator traverses to reach caller

        while(currentElevatorFloor != elevatorCallFloor){

            if(currentElevatorFloor > elevatorCallFloor){

                currentElevatorFloor--;
            }
            else {
                currentElevatorFloor++;
            }


            try {

                sleep(8628); //time taken from iteration 0
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


    public synchronized void elevatorActions() {
        try {

                this.elevatorData.addAll((ArrayList<Object>) SchedulerSystem.getData());


                setDestination();
                setDirection();
                setElevatorCallFloor();


                traverseToElevatorCall();
                System.out.println("Elevator arrived at: " + this.currentElevatorFloor + " Elevator was called at: " + this.elevatorCallFloor);

                doorOpen();
                peopleLoad();
                doorClose();

                while (!differenceBetweenDestinationAndCurrentFloor()) {
                    traverseOneFloor();
                }

                doorOpen();
                peopleUnload();
                doorClose();

                System.out.println("Elevator arrived at Floor: " + currentElevatorFloor);


                SchedulerSystem.elevatorArrived = true;


                this.currentElevatorFloor = this.destination;
        } catch (Exception e) {
            System.out.println("ERROR :: ElevatorSubsystem :: elevatorActions() " + e);
        }
    }

        @Override
        public void run(){
            elevatorActions();
        }
}
