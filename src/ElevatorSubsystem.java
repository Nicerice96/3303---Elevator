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


    private int currentFloor = 0;


    ElevatorSubsystem(){
        elevatorData = new ArrayList<Object>();


    }



    public void traverseOneFloor(){

        if (direction.equals("UP")){
            currentFloor++;

        }
        else if (direction.equals("DOWN")){
            currentFloor--;
        }

        try {

            sleep(8628);
        }
        catch(Exception e){

            System.out.println("something happened...");

        }

        System.out.println("Current Floor: " + currentFloor);



    }


    public boolean differenceBetweenFloors(){

        if(abs(destination-currentFloor) == 0){

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



    @Override

    public void run(){

        this.elevatorData.addAll((ArrayList<Object>) SchedulerSystem.getData());


        setDestination();
        setDirection();


        while(!differenceBetweenFloors()){
            traverseOneFloor();
        }


        System.out.println("Arrived at Floor: " + currentFloor);

        SchedulerSystem.elevatorArrived = true;


        this.currentFloor = this.destination;



    }







}
