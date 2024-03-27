package g5.elevator.model.elevator.elevator_state;

import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.defs.ElevatorDefs;
import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.instruction.Direction;

import java.time.Duration;
import java.time.Instant;

public class ElevatorMovingState extends ElevatorState {
    Direction direction;

    protected ElevatorMovingState(ElevatorNode context) {
        super(context);
    }
    @Override
    public void run() {
        // calculate direction
        direction = context.getNextDestination() - context.getCurrentFloor() > 0 ? Direction.UP : Direction.DOWN;
        // lil tick system here

        Instant prevTick = Instant.now(); //What do these do?
        Instant firstTick = prevTick;
        Instant floorTick = prevTick;
        Instant approachTick = null;
        context.addEvent(new Event(EventType.ELEVATOR_DEPARTED, context.getElevatorId(), context.getNextDestination()));
        boolean stopFlag = false;
        float prevVelocity = context.velocity;
        while(context.getCurrentFloor() != context.getNextDestination() && !stopFlag) {
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
                    if (context.getCurrentFloor() == context.getNextDestination()) break;
                    floorTick = tick;
                } else if((floorDuration > ElevatorDefs.ADJACENT_FLOOR_TIME - ElevatorDefs.ACCELERATION_TIME &&
                        context.getCurrentFloor() + direction.ordinal()*2-1 == context.getNextDestination()) ||
                        context.isStuck()) {
                    // approaching destination floor or elevator stuck, slow down
                    if(approachTick == null) {
                        approachTick = tick;
                    } else {
                        long slowdownDuration = Duration.between(approachTick, tick).toMillis();
                        context.velocity = ElevatorDefs.MAX_SPEED - ElevatorDefs.MAX_SPEED * ((float) slowdownDuration /ElevatorDefs.ACCELERATION_TIME);
                        if(context.isStuck() && (context.velocity - prevVelocity) < context.velocity) {
                            stopFlag = true;
                        }
                    }
                }

            }
            // vectorize velocity
            context.velocity *= direction.ordinal()*2-1;
            context.updateAltitude(context.velocity * (duration / 1000.0F));
            // clamp altitude
            System.out.printf("lifetime: %d, prevVelocity: %.4f, velocity: %.4f, currentFloor: %d, altitude: %.4f\n", lifetime, prevVelocity, context.velocity, context.getCurrentFloor(), context.getAltitude());
            context.updateController();
            prevTick = tick;
            prevVelocity = context.velocity;
            try {
                Thread.sleep(ElevatorDefs.TICK_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        context.velocity = 0.0F;
        context.updateController();
        System.out.printf("velocity: %.4f, currentFloor: %d, altitude: %.4f\n",context.velocity, context.getCurrentFloor(), context.getAltitude());
        if(context.isStuck()) {
            context.setState(new ElevatorStuckState(context));
        } else {
            context.updateAltitude(10.0F * context.getCurrentFloor() - context.getAltitude());
            context.addEvent(new Event(EventType.ELEVATOR_ARRIVED, context.getElevatorId(), context.getCurrentFloor()));
            context.clearDestination();
            context.setState(new ElevatorDoorOpeningState(context));
        }
    }


}
