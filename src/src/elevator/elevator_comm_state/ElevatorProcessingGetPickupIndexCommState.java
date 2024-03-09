package src.elevator.elevator_comm_state;

import src.elevator.ElevatorNode;
import src.instruction.Instruction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static src.defs.Defs.SCHEDULER_PORT;

public class ElevatorProcessingGetPickupIndexCommState extends ElevatorProcessingCommState {
    private final Instruction instruction;
    public ElevatorProcessingGetPickupIndexCommState(ElevatorNode context, String msg) {
        super(context, msg);
        // msg                  = "getPickupIndex,Instruction - 00:00:01.000|3|DOWN|0"
        // msg.split(",")       = ["getPickupIndex", "Instruction - 00:00:01.000|3|DOWN|0"]
        // msg.split(","([1]    = "Instruction - 00:00:01.000|3|DOWN|0"
        this.instruction = Instruction.parse(msg.split(",")[1]);
    }
    @Override
    public void run() {
        int index = context.getPickupIndex(instruction);
        // formulate the response
        // "elevator [id],getPickupIndex,[index]"
        String sString = String.format("elevator %d,getPickupIndex,%d", context.getElevatorId(), index);
        byte[] sByte = sString.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(sByte, sByte.length, InetAddress.getLocalHost(), SCHEDULER_PORT);
            context.sSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        context.setCommState(new ElevatorIdleCommState(context));
    }
}
