import java.net.*;

public class FloorSubsystem {



    DatagramPacket floorSubsystemData;

    DatagramSocket sendAndReceiveSocket;

    FloorSubsystem(){

        try{

            sendAndReceiveSocket = new DatagramSocket(11);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }



    public void sendMessage(String message){
        try {

            byte[] msg = message.getBytes();

            floorSubsystemData = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), 10);

            sendAndReceiveSocket.send(floorSubsystemData);

            System.out.println("FloorSubsytem sent some Data!");
        }

        // very temporary catch; will fix
        catch (Exception e){

            System.out.println("ERROR :: FloorSubsystem :: sendMessage()");

        }
    }








    public static void main (String [] args){


        FloorSubsystem newFloor = new FloorSubsystem();

        newFloor.sendMessage("someStupidMessageFromFloor");






    }

}
