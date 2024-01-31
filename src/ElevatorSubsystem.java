import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ElevatorSubsystem {

    DatagramPacket elevatorSubsystemData;

    DatagramSocket sendAndReceiveSocket;


    ElevatorSubsystem(){


        try{

            sendAndReceiveSocket = new DatagramSocket(12);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }



    public void sendMessage(String message){


        try {
            byte[] msg = message.getBytes();

            elevatorSubsystemData = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), 10);

            sendAndReceiveSocket.send(elevatorSubsystemData);

            System.out.println("Elevator Subsytem sent some Data!");



        }
        //temporary very ugly catch
        catch (Exception e){

            System.out.println("ERROR :: ElevatorSubsystem :: sendMessage()");
        }




    }






    public static void main (String [] args){
        ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();

        elevatorSubsystem.sendMessage("someStupidMessageFromElevator");



    }




}
