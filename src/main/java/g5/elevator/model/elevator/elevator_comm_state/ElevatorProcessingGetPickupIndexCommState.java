package g5.elevator.model.elevator.elevator_comm_state;

import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.elevator.ElevatorNode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ElevatorProcessingGetPickupIndexCommState extends ElevatorProcessingCommState {
    private final Instruction instruction;
    public ElevatorProcessingGetPickupIndexCommState(ElevatorNode context, String msg) {
        super(context, msg);
        // msg                  = "scheduler 12313,getPickupIndex,Instruction - 00:00:01.000|3|DOWN|0"
        // msg.split(",")       = ["scheduler 12313", "getPickupIndex", "Instruction - 00:00:01.000|3|DOWN|0"]
        // msg.split(","[2])    = "Instruction - 00:00:01.000|3|DOWN|0"
        this.instruction = Instruction.parse(msg.split(",")[2]);
    }
    @Override
    public void run() {
        int index = context.getPickupIndex(instruction);
        // formulate the response
        // "elevator [id],getPickupIndex,[index]"
        String sString = String.format("elevator %d,getPickupIndex,%d", context.getElevatorId(), index);
        byte[] sByte = sString.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(sByte, sByte.length, InetAddress.getLocalHost(), callbackPort);
            context.sSocket.send(packet);
            context.addEvent(new Event(EventType.SENT, sString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        context.setCommState(new ElevatorIdleCommState(context));
    }
}
