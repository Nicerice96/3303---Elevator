import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SchedulerSystem {


    DatagramPacket schedulerPacketRcv;
    DatagramSocket receiveSocket;


    SchedulerSystem(){



        try{

            receiveSocket = new DatagramSocket(10);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }


    public void receiveMessage() {

        int messagesRcv = 0;


        while (messagesRcv < 2) {


            try {

                byte[] msgrcv = new byte[100];

                schedulerPacketRcv = new DatagramPacket(msgrcv, msgrcv.length);

                receiveSocket.receive(schedulerPacketRcv);

                System.out.println("Scheduler received: " + new String(schedulerPacketRcv.getData()));
                
                messagesRcv++;
            }

            //temporary Exception Handler
            catch (Exception e) {

                System.out.println("ERROR :: SchedulerSystem :: receiveMessage");


            }

        }
    }

    public static void main (String [] args){


        SchedulerSystem schedulerSystem = new SchedulerSystem();

        schedulerSystem.receiveMessage();



    }
}
