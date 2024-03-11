package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;

import java.time.Duration;
import java.time.Instant;

public class ElevatorIdleState extends ElevatorState {
    public ElevatorIdleState(ElevatorNode context) {
        super(context);
    }
    @Override
    public void run() {
        Instant idleTick = Instant.now(); //What does this do?
        while (context.running){
            Instant tick = Instant.now();
            long idleDuration = Duration.between(idleTick, tick).toMillis();
            if (!context.destinationsEmpty()){
                idleTick = tick;
                context.setState(new ElevatorMovingState(context));
                return;
            } else if (idleDuration > 10000) {
                // No need for Idling, also code doesn't work lol
//                context.addEvent(new Event(EventType.ELEVATOR_IDLE_TOO_LONG, context.getElevatorId()));
//                break;
            }
        }
    }
}
