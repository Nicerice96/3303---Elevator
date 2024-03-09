package src.scheduler_state;

import src.SchedulerSystem;
import src.events.Event;
import src.events.EventType;
import src.scheduler_state.elevator.SchedulerProcessingElevatorRequestState;
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
                SchedulerSystem.addEvent(new Event(EventType.RECEIVED, msg));
                String origin = msg.split(",")[0].strip();
                if(origin.startsWith("floor")) {
                    SchedulerSystem.setState(new SchedulerProcessingFloorRequestState(msg));
                } else if(origin.startsWith("elevator")) {
                    SchedulerSystem.setState(new SchedulerProcessingElevatorRequestState(msg));
                }


            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
