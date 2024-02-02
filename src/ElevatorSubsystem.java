import java.util.ArrayList;

import static java.lang.Math.abs;

public class ElevatorSubsystem extends Thread{


    private ArrayList<Object> elevatorData;

    private Object data;


    private double timeAdjacent = 8.6276;

    private float timeDoorOpen;

    private float timeDoorClose;

    private float loadTime;

    private int destination; //WHERE I NEED TO GO

    private String direction; //UP DOWN


    private int currentElevatorFloor = 0;

    private int elevatorCallFloor; //Floor on which the elevator was called


    ElevatorSubsystem(){
        elevatorData = new ArrayList<Object>();
    }



    public void traverseOneFloor(){

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



    }


    public boolean differenceBetweenDestinationAndCurrentFloor(){

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
            System.out.println("The object is not an Integer and cannot be cast to int.");

            return "";
        }



    }


    public int setElevatorCallFloor() {
        this.data = elevatorData.get(0);

        if (data != null) {
            this.elevatorCallFloor = Integer.parseInt((String) data);
            System.out.println("Elevator Call Floor: " + elevatorCallFloor);
            return this.elevatorCallFloor;
        } else {
            System.out.println("The object is not an Integer and cannot be cast to int.");
            return 0;
        }
    }






    public void traverseToElevatorCall(){

        while(currentElevatorFloor != elevatorCallFloor){

            if(currentElevatorFloor > elevatorCallFloor){

                currentElevatorFloor--;
            }
            else {
                currentElevatorFloor++;
            }


            try {

                sleep(8628);
            }
            catch(Exception e){

                System.out.println("something happened...");

            }




        }


    }







    @Override

    public void run(){

        this.elevatorData.addAll((ArrayList<Object>) SchedulerSystem.getData());


        setDestination();
        setDirection();
        setElevatorCallFloor();


        traverseToElevatorCall();
        System.out.println("Elevator arrived at: " + currentElevatorFloor + " Elevator was called at: " + elevatorCallFloor);



        while(!differenceBetweenDestinationAndCurrentFloor()){
            traverseOneFloor();
        }


        System.out.println("Arrived at Floor: " + currentElevatorFloor);

        synchronized (SchedulerSystem.elevatorQueueLock) {
            SchedulerSystem.elevatorArrived = true;
        }


        this.currentElevatorFloor = this.destination;



    }







}
