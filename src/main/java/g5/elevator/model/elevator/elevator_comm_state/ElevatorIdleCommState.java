package g5.elevator.model.elevator.elevator_comm_state;

import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.defs.Defs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class ElevatorIdleCommState extends ElevatorCommState {
    public ElevatorIdleCommState(ElevatorNode context) {
        super(context);
    }

    @Override
    public void run() {
        while (context.running) {
            // Wait until packet is received
            byte[] buffer = new byte[Defs.MSG_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                context.rSocket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println();
                context.addEvent(new Event(EventType.RECEIVED, receivedMessage));
                context.setCommState(new ElevatorProcessingCommState(context, receivedMessage));
            } catch (SocketException e) {
                if(e.getMessage().equals("Socket closed")) return;
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
