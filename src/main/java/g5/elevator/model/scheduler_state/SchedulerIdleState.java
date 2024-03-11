package g5.elevator.model.scheduler_state;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.defs.Defs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.scheduler_state.elevator.SchedulerProcessingElevatorRequestState;
import g5.elevator.model.scheduler_state.floor.SchedulerProcessingFloorRequestState;

import java.io.IOException;
import java.net.DatagramPacket;


public class SchedulerIdleState extends SchedulerState  {
    @Override
    public void handle() {
        while(true) {
            byte[] messagercv = new byte[Defs.MSG_SIZE];
            DatagramPacket rcvpacket = new DatagramPacket(messagercv, messagercv.length);
            try {
                SchedulerSystem.rSocket.receive(rcvpacket);
                String msg = Defs.getMessage(messagercv, rcvpacket.getLength());
                System.out.println();
                SchedulerSystem.addEvent(new Event(EventType.RECEIVED, msg));
                String origin = msg.split(",")[0].strip();
                if(origin.startsWith("floor")) {
                    SchedulerSystem.setState(new SchedulerProcessingFloorRequestState(msg));
                } else if(origin.startsWith("g5")) {
                    SchedulerSystem.setState(new SchedulerProcessingElevatorRequestState(msg));
                }


            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
