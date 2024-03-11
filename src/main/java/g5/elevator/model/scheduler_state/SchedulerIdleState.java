package g5.elevator.model.scheduler_state;

import g5.elevator.defs.Defs;
import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.scheduler_state.elevator.SchedulerProcessingElevatorRequestState;
import g5.elevator.model.scheduler_state.floor.SchedulerProcessingFloorRequestState;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;


public class SchedulerIdleState extends SchedulerState  {
    public SchedulerIdleState(SchedulerSystem context) {
        super(context);
    }

    @Override
    public void run() {
        while(context.running) {
            byte[] messagercv = new byte[Defs.MSG_SIZE];
            DatagramPacket rcvpacket = new DatagramPacket(messagercv, messagercv.length);
            try {
                context.rSocket.receive(rcvpacket);
                String msg = Defs.getMessage(messagercv, rcvpacket.getLength());
                System.out.println();
                context.addEvent(new Event(EventType.RECEIVED, msg));
                String origin = msg.split(",")[0].strip();
                if(origin.startsWith("floor")) {
                    context.setState(new SchedulerProcessingFloorRequestState(context, msg));
                    return;
                } else if(origin.startsWith("elevator")) {
                    context.setState(new SchedulerProcessingElevatorRequestState(context, msg));
                    return;
                }
            } catch (SocketException e) {
                if(e.getMessage().equals("Socket closed")) return;
                throw new RuntimeException(e);
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
