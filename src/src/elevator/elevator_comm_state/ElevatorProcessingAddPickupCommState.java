package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import static src.defs.Defs.SCHEDULER_PORT;


public class ElevatorProcessingAddPickupCommState extends ElevatorProcessingCommState {

    private final Instruction instruction;
    public ElevatorProcessingAddPickupCommState(ElevatorNode context, String msg) {
        super(context, msg);
        this.instruction = Instruction.parse(msg.split(",")[1]);
    }

    @Override
    public void run() {
        context.addPickup(instruction);


        String successMessage = String.format("elevator %d,addPickup,OK", context.getElevatorId());

        byte[] messageBytes = successMessage.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, InetAddress.getLocalHost(), SCHEDULER_PORT);
            context.sSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        context.setCommState(new ElevatorIdleCommState(context));
    }
}
