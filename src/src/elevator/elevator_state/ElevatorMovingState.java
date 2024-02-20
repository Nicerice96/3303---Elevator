package src.elevator.elevator_state;

import src.defs.ElevatorDefs;
import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;
import src.instruction.Direction;

public class ElevatorMovingState extends ElevatorState {

    @Override
    public void handle(ElevatorNode context) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        context.addEvent(new Event(EventType.ELEVATOR_ARRIVED, context.getElevatorId()));
        context.currentFloor = context.getNextDestination();
        System.out.printf("e%d: currentFloor = %d\n", context.getElevatorId(), context.currentFloor);
        context.clearDestination();
        context.setState(new ElevatorIdleState());
    }


}
