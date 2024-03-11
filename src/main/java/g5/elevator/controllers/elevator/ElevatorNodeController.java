package g5.elevator.controllers.elevator;

import g5.elevator.controllers.Updatable;
import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.elevator.elevator_comm_state.ElevatorIdleCommState;
import g5.elevator.model.elevator.elevator_comm_state.ElevatorProcessingAddPickupCommState;
import g5.elevator.model.elevator.elevator_comm_state.ElevatorProcessingCommState;
import g5.elevator.model.elevator.elevator_comm_state.ElevatorProcessingGetPickupIndexCommState;
import g5.elevator.model.elevator.elevator_state.*;
import g5.elevator.model.events.Event;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class ElevatorNodeController implements Initializable, Updatable {
    @FXML
    Slider altitudeSlider;
    @FXML
    RadioButton idleButton, movingButton, doorOpeningButton, doorOpenButton, doorClosingButton,
        idleCommButton, processingCommButton, getPickupButton, addPickupButton;
    @FXML
    Label idLabel, registeredLabel, altitudeLabel, velocityLabel, destinationsLabel, rSocketLabel, sSocketLabel;
    @FXML
    ListView<Event> logList;
    private ElevatorNode elevatorNode;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elevatorNode = new ElevatorNode(this);
        elevatorNode.start();
        update();
        idLabel.setText(String.valueOf(elevatorNode.getElevatorId()));
    }

    @Override
    public void update() {
        Platform.runLater(() -> {;
            if(elevatorNode == null) return;
            updateSlider();
            updateStatus();
            updateCommStatus();
            updateData();
            updateEventLog();
        });
    }

    private void updateSlider() {
        int min = (int) altitudeSlider.getMin(), max = (int) altitudeSlider.getMax();
        for(int d : elevatorNode.getDestinations()) {
            int altitude = d * 10;
            max = Math.max(max, altitude);
            min = Math.min(min, altitude);
        }
        max = Math.max(max, (int) elevatorNode.getAltitude());
        min = Math.min(min, (int) elevatorNode.getAltitude());
        min = Math.min(min, 0);
        altitudeSlider.setMin(min);
        altitudeSlider.setMax(max);
        altitudeSlider.setValue(elevatorNode.getAltitude());
    }

    private void updateStatus() {
        idleButton.setSelected(elevatorNode.getElevatorState() instanceof ElevatorIdleState);
        movingButton.setSelected(elevatorNode.getElevatorState() instanceof ElevatorMovingState);
        doorOpeningButton.setSelected(elevatorNode.getElevatorState() instanceof ElevatorDoorOpeningState);
        doorOpenButton.setSelected(elevatorNode.getElevatorState() instanceof ElevatorDoorOpenState);
        doorClosingButton.setSelected(elevatorNode.getElevatorState() instanceof ElevatorDoorClosingState);
    }

    private void updateCommStatus() {
        idleCommButton.setSelected(elevatorNode.getCommState() instanceof ElevatorIdleCommState);
        processingCommButton.setSelected(elevatorNode.getCommState() instanceof ElevatorProcessingCommState);
        getPickupButton.setSelected(elevatorNode.getCommState() instanceof ElevatorProcessingGetPickupIndexCommState);
        addPickupButton.setSelected(elevatorNode.getCommState() instanceof ElevatorProcessingAddPickupCommState);
    }

    private void updateData() {
        registeredLabel.setText(elevatorNode.isRegistered() ? "Yes" : "No");
        altitudeLabel.setText(String.format("%.4f", elevatorNode.getAltitude()));
        velocityLabel.setText(String.format("%.4f", elevatorNode.getVelocity()));
        destinationsLabel.setText(String.valueOf(elevatorNode.getDestinations()));
        rSocketLabel.setText(String.valueOf(elevatorNode.rSocket.getLocalPort()));
        sSocketLabel.setText(String.valueOf(elevatorNode.sSocket.getLocalPort()));
    }

    private void updateEventLog() {
        logList.setItems(FXCollections.observableArrayList(elevatorNode.getLog().reversed()));
    }

    public void close() { elevatorNode.close(); }
}
