package g5.elevator.model.scheduler_state.floor;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.defs.Defs;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.scheduler_state.SchedulerIdleState;

import java.io.IOException;
import java.net.*;

public class ProcessingFloorAddInstructionState extends SchedulerProcessingFloorRequestState {
    public ProcessingFloorAddInstructionState(SchedulerSystem context, String msg) {
        super(context, msg);
        context.addInstruction(Instruction.parse(msg.split(",")[2]));
    }

    @Override
    public void run() {
        DatagramSocket tempSocket;
        try {
            tempSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            Instruction i = context.getInstruction();
            if(i == null) break;
            int id = getMinPickupIndex(i, tempSocket);
            // send pickup request
            // "addPickup,[i.toString()],"
            String sString = String.format("scheduler %d,addPickup,%s", tempSocket.getLocalPort(), i);
            byte[] sBytes = sString.getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), context.elevators.get(id));
                context.sSocket.send(packet);
                context.addEvent(new Event(EventType.SENT, sString));
                byte[] rBytes = new byte[Defs.MSG_SIZE];
                DatagramPacket rPacket = new DatagramPacket(rBytes, rBytes.length);
                try {
                    tempSocket.receive(rPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String res = Defs.getMessage(rBytes, rPacket.getLength());
                context.addEvent(new Event(EventType.RECEIVED, res));
                String[] split = res.split(",");
                if(!split[0].equals("elevator " + id)) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown originator \"%s\"\n", id, split[0]);
                    tempSocket.close();
                    context.setState(new SchedulerIdleState(context));
                    return;
                } else if(!split[1].equals("addPickup")) {
                    System.out.printf("ERROR: failed to get return of addPickup from elevator %d, unknown action \"%s\"\n", id, split[1]);
                    tempSocket.close();
                    context.setState(new SchedulerIdleState(context));
                    return;
                } else if(!split[2].equals("OK")) {
                    System.out.printf("ERROR: unknown response %s\n", split[2]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        tempSocket.close();
        context.setState(new SchedulerIdleState(context));
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
        for (int id : context.elevators.keySet()) {
            int port = context.elevators.get(id);
            // get pick up index
            try {
                DatagramPacket sPacket = new DatagramPacket(sBytes, sBytes.length, InetAddress.getLocalHost(), port);
                context.sSocket.send(sPacket);
                context.addEvent(new Event(EventType.SENT, sString));
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
            context.addEvent(new Event(EventType.RECEIVED, res));
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
