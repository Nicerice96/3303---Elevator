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
        this.data = elevatorData.get(1);

        if (data != null) {
            this.elevatorCallFloor = Integer.parseInt((String) data);
            System.out.println("Elevator Call Floor: " + this.elevatorCallFloor);
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

        //Make sure this thread waits for the appropriate time : "timeDoorOpen"
        //Make sure to add to the globalTime


    }

    public void doorClose(){

        //Make sure this thread waits for the appropriate time : "timeDoorClose"
        //Make sure to add to the globalTime

    }


    public void peopleLoad(){

        //Make sure this thread waits for the appropriate time : "loadTime"
        //Make sure to add to the globalTime


    }

    public void peopleUnload(){

        //Make sure this thread waits for the appropriate time : "unloadTime"
        //Make sure to add to the globalTime
    }






    @Override

    public void run(){



            this.elevatorData.addAll((ArrayList<Object>) SchedulerSystem.getData());


            setDestination();
            setDirection();
            setElevatorCallFloor();


            traverseToElevatorCall();
            System.out.println("Elevator arrived at: " + this.currentElevatorFloor + " Elevator was called at: " + this.elevatorCallFloor);


            while (!differenceBetweenDestinationAndCurrentFloor()) {
                traverseOneFloor();
            }


            System.out.println("Elevator arrived at Floor: " + currentElevatorFloor);


            SchedulerSystem.elevatorArrived = true;


            this.currentElevatorFloor = this.destination;




    }







}
