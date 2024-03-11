package g5.elevator.model.elevator.elevator_comm_state;

import g5.elevator.defs.Defs;
import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;

import java.io.IOException;
import java.net.DatagramPacket;

public class ElevatorIdleCommState extends ElevatorCommState {
    public ElevatorIdleCommState(ElevatorNode context) {
        super(context);
    }

    @Override
    public void run() {
        while (true) {
            // Wait until packet is received
            byte[] buffer = new byte[Defs.MSG_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                context.rSocket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println();
                context.addEvent(new Event(EventType.RECEIVED, receivedMessage));
                context.setCommState(new ElevatorProcessingCommState(context, receivedMessage));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }
}
