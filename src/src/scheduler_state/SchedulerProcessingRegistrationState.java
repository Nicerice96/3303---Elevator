package src.scheduler_state;

import src.SchedulerSystem;
import src.events.Event;
import src.events.EventType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

public class SchedulerProcessingRegistrationState extends SchedulerState {
    private final String msg;
    private final int id;
    private final HashMap<Integer, Integer> hashMap;
    private final int port;

    /**
     * Registration state, accessible by the elevator and floor states
     * @param msg the message
     * @param id the floor number/elevator id
     * @param hashMap the hashmap to add floor/elevator data to
     */
    public SchedulerProcessingRegistrationState(String msg, int id, HashMap<Integer, Integer> hashMap) {
        super();
        this.msg = msg;
        this.id = id;
        this.hashMap = hashMap;
        this.port = Integer.parseInt(msg.split(",")[2].strip());
    }

    @Override
    public void handle() {
        hashMap.put(id, port);
        byte[] res = "OK".getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(res, res.length, InetAddress.getLocalHost(), port);
            SchedulerSystem.addEvent(new Event(EventType.SENT, "OK"));
            SchedulerSystem.sSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SchedulerSystem.setState(new SchedulerIdleState());
    }
}
