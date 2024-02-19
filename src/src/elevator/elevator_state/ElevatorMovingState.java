package src.elevator.elevator_state;

import src.defs.ElevatorDefs;
import src.elevator.ElevatorNode;
import src.instruction.Direction;

public class ElevatorMovingState extends ElevatorState {
    //

    Direction direction;


    @Override
    public void handle(ElevatorNode context) {

       if (context.getVelocity() < 0){

        setDirection(Direction.DOWN);
    }

       else{

        setDirection(Direction.UP);
    }

//        try {
//            Thread.sleep(ElevatorDefs.DOOR_CLOSING_TIME);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        ElevatorState s = new ElevatorDoorClosedState();
//        context.setState(s);
//        s.handle(context);
//    }
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
}


    public void setDirection(Direction direction){

        this.direction = direction;
    }


}
