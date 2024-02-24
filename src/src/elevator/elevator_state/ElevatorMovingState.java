package src.elevator.elevator_state;

import src.defs.ElevatorDefs;
import src.elevator.ElevatorNode;
import src.events.Event;
import src.events.EventType;
import src.instruction.Direction;

import java.time.Duration;
import java.time.Instant;

public class ElevatorMovingState extends ElevatorState {
    Direction direction;

    @Override
    public void handle(ElevatorNode context) {
        // calculate direction
        direction = context.getNextDestination() - context.getCurrentFloor() > 0 ? Direction.UP : Direction.DOWN;
        // lil tick system here

        Instant prevTick = Instant.now(); //What do these do?
        Instant firstTick = prevTick;
        Instant floorTick = prevTick;
        Instant approachTick = null;
        context.addEvent(new Event(EventType.ELEVATOR_DEPARTED, context.getElevatorId(), context.getNextDestination()));
        while(context.getCurrentFloor() != context.getNextDestination()) {
            Instant tick = Instant.now();
            long duration = Duration.between(prevTick, tick).toMillis();
            long lifetime = Duration.between(firstTick, tick).toMillis();
            long floorDuration = Duration.between(floorTick, tick).toMillis();
            // logic

            // accelerating?
            if(lifetime < ElevatorDefs.ACCELERATION_TIME) {
                context.velocity = ElevatorDefs.MAX_SPEED * ((float) lifetime /ElevatorDefs.ACCELERATION_TIME);
            } else {
                context.velocity = ElevatorDefs.MAX_SPEED;
                // SENSOR CODE HERE!!
                if (floorDuration > ElevatorDefs.ADJACENT_FLOOR_TIME) {
                    // stop or continue to next floor
                    context.traverse(direction);
                    context.addEvent(new Event(EventType.ELEVATOR_TRAVERSED_FLOOR, context.getElevatorId(), context.getCurrentFloor()));
                    System.out.println(context.getNextDestination());
                    if (context.getCurrentFloor() == context.getNextDestination()) break;
                    floorTick = tick;
                } else if(floorDuration > ElevatorDefs.ADJACENT_FLOOR_TIME - ElevatorDefs.ACCELERATION_TIME &&
                        context.getCurrentFloor() + direction.ordinal()*2-1 == context.getNextDestination()) {
                    // approaching destination floor, slow down
                    if(approachTick == null) {
                        approachTick = tick;
                    } else {
                        long slowdownDuration = Duration.between(approachTick, tick).toMillis();
                        context.velocity = ElevatorDefs.MAX_SPEED - ElevatorDefs.MAX_SPEED * ((float) slowdownDuration /ElevatorDefs.ACCELERATION_TIME);
                    }
                }

            }
            // vectorize velocity
            context.velocity *= direction.ordinal()*2-1;
            context.updateAltitude(context.velocity * (duration / 1000.0F));
            // clamp altitude
//            System.out.printf("lifetime: %d, velocity: %.4f, currentFloor: %d, altitude: %.4f\n", lifetime, context.velocity, context.getCurrentFloor(), context.getAltitude());
            prevTick = tick;
            try {
                Thread.sleep(ElevatorDefs.TICK_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        context.velocity = 0.0F;
        context.updateAltitude(10.0F * context.getCurrentFloor() - context.getAltitude());
//        System.out.printf("velocity: %.4f, currentFloor: %d, altitude: %.4f\n",context.velocity, context.getCurrentFloor(), context.getAltitude());
        context.addEvent(new Event(EventType.ELEVATOR_ARRIVED, context.getElevatorId(), context.getCurrentFloor()));
        context.clearDestination();
        context.setState(new ElevatorDoorOpeningState());
    }


}
