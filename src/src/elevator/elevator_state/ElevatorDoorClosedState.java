package src.elevator.elevator_state;

import src.elevator.ElevatorNode;

public class ElevatorDoorClosedState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {
        System.out.println("TODO ElevatorDoorClosedState");

        // when door closed 2 paths:
        // 1. goToFloor()
        // 2. idle

        //
        if(context.destinationsEmpty()){
            //ElevatorState s = new ElevatorMovingState();
            ElevatorState s = new ElevatorMovingState();
            context.setState(s);
            s.handle(context);
        }else{
            ElevatorState s = new ElevatorIdleState();
            context.setState(s);
            s.handle(context);
        }
    }
}
