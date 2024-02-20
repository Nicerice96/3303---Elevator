package src.elevator.elevator_state;

import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;

public class ElevatorIdleState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {
        while (true){
            if (!context.destinationsEmpty()){
                context.setState(new ElevatorMovingState());
            }
        }
    }
}
