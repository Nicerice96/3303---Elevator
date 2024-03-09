package src.scheduler_state.floor;

import src.SchedulerSystem;
import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;
import src.instruction.Instruction;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static src.defs.Defs.MSG_SIZE;
import static src.defs.Defs.getMessage;

public class ProcessingFloorAddInstructionState extends SchedulerProcessingFloorRequestState {

    public ProcessingFloorAddInstructionState(String msg) {
        super(msg);
        SchedulerSystem.instructions.add(Instruction.parse(msg.split(",")[2]));
    }

    @Override
    public void handle() {
        for (Instruction i : SchedulerSystem.instructions) {
            int id = getMinPickupIndex(i);
            // send pickup request
            // "addPickup,[i.toString()]
            String sString = "addPickup," + i;
            byte[] sBytes = sString.getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), SchedulerSystem.elevators.get(id));
                SchedulerSystem.sSocket.send(packet);
                SchedulerSystem.addEvent(new Event(EventType.SENT, sString));
                byte[] rBytes = new byte[MSG_SIZE];
                DatagramPacket rPacket = new DatagramPacket(rBytes, rBytes.length);
                try {
                    SchedulerSystem.rSocket.receive(rPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String res = getMessage(rBytes, rPacket.getLength());
                SchedulerSystem.addEvent(new Event(EventType.RECEIVED, res));
                String[] split = res.split(",");
                if(!split[0].equals("elevator " + id)) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown originator \"%s\"\n", id, split[0]);
                    return;
                } else if(!split[1].equals("addPickup")) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown action \"%s\"\n", id, split[1]);
                    return;
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the elevator with the minimum pickup index/the highest priority
     * @return the id of the elevator with the minimum pickup index/the highest priority
     */
    private int getMinPickupIndex(Instruction instruction) {
        int min = Integer.MAX_VALUE;
        String sString = "getPickupIndex," + instruction.toString();
        byte[] sBytes = sString.getBytes();
        int bestId = -1;
        for (int id : SchedulerSystem.elevators.keySet()) {
            int port = SchedulerSystem.elevators.get(id);
            // get pick up index
            try {
                DatagramPacket sPacket = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), port);
                SchedulerSystem.sSocket.send(sPacket);
                SchedulerSystem.addEvent(new Event(EventType.SENT, sString));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] rBytes = new byte[MSG_SIZE];
            DatagramPacket rPacket = new DatagramPacket(rBytes, rBytes.length);
            try {
                SchedulerSystem.rSocket.receive(rPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String res = getMessage(rBytes, rPacket.getLength());
            SchedulerSystem.addEvent(new Event(EventType.RECEIVED, res));
            String[] split = res.split(",");
            if(!split[0].equals("elevator " + id)) {
                System.out.printf("ERROR: failed to get return of getPickupIndex from elevator %d, unknown originator \"%s\"\n", id, split[0]);
                continue;
            } else if(!split[1].equals("getPickupIndex")) {
                System.out.printf("ERROR: failed to get return of getPickupIndex from elevator %d, unknown action \"%s\"\n", id, split[1]);
                continue;
            }

            int pickupIndex = Integer.parseInt(split[2]);
            if (pickupIndex < min) {
                min = pickupIndex;
                bestId = id;
            }
        }
        return bestId;
    }
}
