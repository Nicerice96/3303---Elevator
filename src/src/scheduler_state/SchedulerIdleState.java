package src.scheduler_state;

import src.SchedulerSystem;
import src.scheduler_state.floor.SchedulerProcessingFloorRequestState;

import java.io.IOException;
import java.net.DatagramPacket;

import static src.defs.Defs.MSG_SIZE;
import static src.defs.Defs.getMessage;


public class SchedulerIdleState extends SchedulerState  {
    @Override
    public void handle() {
        while(true) {
            byte[] messagercv = new byte[MSG_SIZE];
            DatagramPacket rcvpacket = new DatagramPacket(messagercv, messagercv.length);
            try {
                SchedulerSystem.rSocket.receive(rcvpacket);
                String msg = getMessage(messagercv, rcvpacket.getLength());
                System.out.println(msg);
                if(msg.contains("floor")) {
                    SchedulerSystem.setState(new SchedulerProcessingFloorRequestState(msg));
                }


            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
