package g5.elevator.model.scheduler_state.elevator;

import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.SchedulerSystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ProcessingForwardEventState extends SchedulerProcessingElevatorRequestState{
    public ProcessingForwardEventState(String msg) {
        super(msg);
    }

    @Override
    public void handle() {
        String sString = "event," + msg.split(",")[2].strip();
        byte[] sBytes = sString.getBytes();
        for(int floor : SchedulerSystem.floors.keySet()) {
            try {
                DatagramPacket packet = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), SchedulerSystem.floors.get(floor));
                SchedulerSystem.sSocket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        SchedulerSystem.addEvent(new Event(EventType.FORWARDED, sString));
    }
}
