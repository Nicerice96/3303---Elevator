package src.elevator.elevator_state;

import src.elevator.ElevatorNode;

public class ElevatorIdleState extends ElevatorState {
    @Override
    public void handle(ElevatorNode context) {

        while (true){

           if (context.getAvailableInstruction()){

               ElevatorState s = new ElevatorMovingState();
               context.setState(s);
               s.handle(context);

           }
        }


    }
}
