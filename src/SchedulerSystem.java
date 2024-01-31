import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SchedulerSystem {


    DatagramPacket schedulerPacketRcv;

    DatagramPacket schedulerPacketSnd;
    DatagramSocket receiveSocket;

    byte [] msgrcv;


    SchedulerSystem(){
        try{

            receiveSocket = new DatagramSocket(10);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    public void receiveMessage() {


            try {

                msgrcv = new byte[100];

                schedulerPacketRcv = new DatagramPacket(msgrcv, msgrcv.length);

                receiveSocket.receive(schedulerPacketRcv);

                System.out.println("Scheduler received: " + new String(schedulerPacketRcv.getData()));

            }

            //temporary Exception Handler
            catch (Exception e) {

                System.out.println("ERROR :: SchedulerSystem :: receiveMessage() ::" + e);


            }
    }



    public void sendMessage() {


        try {

            schedulerPacketSnd = new DatagramPacket(msgrcv, msgrcv.length, InetAddress.getLocalHost(), 12);

            receiveSocket.send(schedulerPacketSnd);

            System.out.println("Scheduler sent: " + new String(schedulerPacketSnd.getData()));
        }
        catch(Exception e){


            System.out.println("ERROR :: SchedulerSystem :: sendMessage() ::" + e);
        }
    }


    public static void main (String [] args){


        SchedulerSystem schedulerSystem = new SchedulerSystem();

        schedulerSystem.receiveMessage();

        schedulerSystem.sendMessage();



    }
}
