package g5.elevator.controllers;

import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.scheduler_state.SchedulerFaultState;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.scheduler_state.SchedulerProcessingRegistrationState;
import g5.elevator.model.scheduler_state.elevator.ProcessingForwardEventState;
import g5.elevator.model.scheduler_state.elevator.SchedulerProcessingElevatorRequestState;
import g5.elevator.model.scheduler_state.floor.ProcessingFloorAddInstructionState;
import g5.elevator.model.scheduler_state.floor.SchedulerProcessingFloorRequestState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedulerController implements Initializable {
    private SchedulerSystem schedulerSystem;
    @FXML
    ListView<String> logList;
    @FXML
    RadioButton idleButton, faultButton, pElevatorRequestButton, pFloorRequestButton, pRegistrationRequestButton, forwardEventButton, addInstructionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulerSystem = new SchedulerSystem();
        update();
    }

    public void update() {
        idleButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);
        faultButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerFaultState);
        pElevatorRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingElevatorRequestState);
        pFloorRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingFloorRequestState);
        pRegistrationRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingRegistrationState);
        forwardEventButton.setSelected(schedulerSystem.getSchedulerState() instanceof ProcessingForwardEventState);
        addInstructionButton.setSelected(schedulerSystem.getSchedulerState() instanceof ProcessingFloorAddInstructionState);
    }
}
