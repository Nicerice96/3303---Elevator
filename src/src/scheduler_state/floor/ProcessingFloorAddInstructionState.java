package src.scheduler_state.floor;

import src.instruction.Instruction;

public class ProcessingFloorAddInstructionState extends SchedulerProcessingFloorRequestState {
    private final Instruction instruction;

    public ProcessingFloorAddInstructionState(String msg) {
        super(msg);
        this.instruction = Instruction.parse(msg.split(",")[2]);
    }

    @Override
    public void handle() {
        // TODO: go through all elevators

    }
}
