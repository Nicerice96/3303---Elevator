package src.scheduler_state.floor;

import src.SchedulerSystem;
import src.events.Event;
import src.events.EventType;
import src.scheduler_state.SchedulerIdleState;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ProcessingFloorRegistrationState extends SchedulerProcessingFloorRequestState {
    private final int port;
    public ProcessingFloorRegistrationState(String msg) {
        super(msg);
        this.port = Integer.parseInt(msg.split(",")[2].strip());
    }

    @Override
    public void handle() {
        byte[] res = "OK".getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(res, res.length, InetAddress.getLocalHost(), port);
            SchedulerSystem.addEvent(new Event(EventType.SENT, "'OK' to floor " + floor));
            SchedulerSystem.sSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SchedulerSystem.setState(new SchedulerIdleState());
    }
}
