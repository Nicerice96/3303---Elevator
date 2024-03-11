package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;
import src.instruction.Direction;
import src.instruction.Instruction;

import java.io.IOException;
import java.net.DatagramPacket;

import static src.defs.Defs.MSG_SIZE;

public class ElevatorIdleCommState extends ElevatorCommState {
    public ElevatorIdleCommState(ElevatorNode context) {
        super(context);
    }

    @Override
    public void run() {
        while (true) {
            // Wait until packet is received
            byte[] buffer = new byte[MSG_SIZE];
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
