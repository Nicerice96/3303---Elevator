package src.elevator.elevator_state;

import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;

import java.time.Duration;
import java.time.Instant;

public class ElevatorIdleState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {
        Instant idleTick = Instant.now();
        while (true){
            Instant tick = Instant.now();
            long idleDuration = Duration.between(idleTick, tick).toMillis();
            if (!context.destinationsEmpty()){
                idleTick = tick;
                context.setState(new ElevatorMovingState());
            } else if (idleDuration > 10000) {
                // No need for Idling, also code doesn't work lol
//                context.addEvent(new Event(EventType.ELEVATOR_IDLE_TOO_LONG, context.getElevatorId()));
//                break;
            }
        }
    }
}
