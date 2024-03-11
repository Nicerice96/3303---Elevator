package g5.elevator.model.scheduler_state.floor;

import g5.elevator.defs.Defs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.instruction.Instruction;

import java.io.IOException;
import java.net.*;

public class ProcessingFloorAddInstructionState extends SchedulerProcessingFloorRequestState {
    public ProcessingFloorAddInstructionState(String msg) {
        super(msg);
        SchedulerSystem.instructions.add(Instruction.parse(msg.split(",")[2]));
    }

    @Override
    public void handle() {
        DatagramSocket tempSocket;
        try {
            tempSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        for (Instruction i : SchedulerSystem.instructions) {
            int id = getMinPickupIndex(i, tempSocket);
            // send pickup request
            // "addPickup,[i.toString()],"
            String sString = String.format("scheduler %d,addPickup,%s", tempSocket.getLocalPort(), i);
            byte[] sBytes = sString.getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), SchedulerSystem.elevators.get(id));
                SchedulerSystem.sSocket.send(packet);
                SchedulerSystem.addEvent(new Event(EventType.SENT, sString));
                byte[] rBytes = new byte[Defs.MSG_SIZE];
                DatagramPacket rPacket = new DatagramPacket(rBytes, rBytes.length);
                try {
                    tempSocket.receive(rPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String res = Defs.getMessage(rBytes, rPacket.getLength());
                SchedulerSystem.addEvent(new Event(EventType.RECEIVED, res));
                String[] split = res.split(",");
                if(!split[0].equals("g5 " + id)) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown originator \"%s\"\n", id, split[0]);
                    tempSocket.close();
                    return;
                } else if(!split[1].equals("addPickup")) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown action \"%s\"\n", id, split[1]);
                    tempSocket.close();
                    return;
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        tempSocket.close();
    }

    /**
     * Gets the elevator with the minimum pickup index/the highest priority
     * @return the id of the elevator with the minimum pickup index/the highest priority
     */
    private int getMinPickupIndex(Instruction instruction, DatagramSocket tempSocket) {
        int min = Integer.MAX_VALUE;
        String sString = String.format("scheduler %d,getPickupIndex,%s", tempSocket.getLocalPort(), instruction.toString());
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
            byte[] rBytes = new byte[Defs.MSG_SIZE];
            DatagramPacket rPacket = new DatagramPacket(rBytes, rBytes.length);
            try {
                tempSocket.receive(rPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String res = Defs.getMessage(rBytes, rPacket.getLength());
            SchedulerSystem.addEvent(new Event(EventType.RECEIVED, res));
            String[] split = res.split(",");
            if(!split[0].equals("g5 " + id)) {
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
