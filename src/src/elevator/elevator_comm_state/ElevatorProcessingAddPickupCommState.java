package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class ElevatorProcessingAddPickupCommState extends ElevatorProcessingCommState {

    private final Instruction instruction;
    public ElevatorProcessingAddPickupCommState(ElevatorNode context, String msg) {
        super(context, msg);
        this.instruction = Instruction.parse(msg.split(",")[2]);
    }

    @Override
    public void run() {
        context.addPickup(instruction);


        String successMessage = String.format("elevator %d,addPickup,OK", context.getElevatorId());

        byte[] messageBytes = successMessage.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, InetAddress.getLocalHost(), callbackPort);
            context.sSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        context.setCommState(new ElevatorIdleCommState(context));
    }
}
