package g5.elevator.model.scheduler_state.elevator;

import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.scheduler_state.SchedulerIdleState;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ProcessingForwardEventState extends SchedulerProcessingElevatorRequestState{
    public ProcessingForwardEventState(SchedulerSystem context, String msg) {
        super(context, msg);
    }

    @Override
    public void run() {
        String sString = "event," + msg.split(",")[2].strip();
        byte[] sBytes = sString.getBytes();
        for(int floor : context.floors.keySet()) {
            try {
                DatagramPacket packet = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), context.floors.get(floor));
                context.sSocket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        context.addEvent(new Event(EventType.FORWARDED, sString));
        // check if it's an event that needs timing
        if (sString.contains("passenger (")) {
            String clean = sString.split("\\(")[1].replace(").", "");
            System.out.println(clean);
            Instruction temp = Instruction.parse(clean);
            assert temp != null;
            Instruction instruction = context.getInstructionFromTimestamp(temp.getTimestamp());
            if(sString.contains("unloading")) {
                instruction.setDropoffTick();
            } else if(sString.contains("loading")) {
                instruction.setPickupTick();
            }
        }
        context.setState(new SchedulerIdleState(context));
    }
}
