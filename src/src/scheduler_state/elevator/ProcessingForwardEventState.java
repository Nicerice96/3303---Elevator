package src.scheduler_state.elevator;

import src.SchedulerSystem;
import src.events.Event;
import src.events.EventType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
