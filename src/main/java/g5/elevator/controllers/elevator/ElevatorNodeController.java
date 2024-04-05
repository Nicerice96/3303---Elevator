package g5.elevator.controllers.elevator;

import g5.elevator.controllers.Updatable;
import g5.elevator.model.elevator.ElevatorNode;
import g5.elevator.model.elevator.elevator_comm_state.*;
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
    RadioButton idleButton, movingButton, stuckButton, doorOpeningButton, doorOpenButton, doorClosingButton, doorStuckButton,
        idleCommButton, processingCommButton, getPickupButton, addPickupButton;
    @FXML
    Label idLabel, registeredLabel, altitudeLabel, velocityLabel, destinationsLabel, rSocketLabel, sSocketLabel, capacityLabel;
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
        ElevatorState state = elevatorNode.getElevatorState();
        idleButton.setSelected(state instanceof ElevatorIdleState);
        movingButton.setSelected(state instanceof ElevatorMovingState);
        stuckButton.setSelected(state instanceof ElevatorStuckState);
        doorOpeningButton.setSelected(state instanceof ElevatorDoorOpeningState);
        doorStuckButton.setSelected(state instanceof ElevatorDoorStuckState);
        doorOpenButton.setSelected(state instanceof ElevatorDoorOpenState);
        doorClosingButton.setSelected(state instanceof ElevatorDoorClosingState);
    }

    private void updateCommStatus() {
        ElevatorCommState state = elevatorNode.getCommState();
        idleCommButton.setSelected(state instanceof ElevatorIdleCommState);
        processingCommButton.setSelected(state instanceof ElevatorProcessingCommState);
        getPickupButton.setSelected(state instanceof ElevatorProcessingGetPickupIndexCommState);
        addPickupButton.setSelected(state instanceof ElevatorProcessingAddPickupCommState);
    }

    private void updateData() {
        registeredLabel.setText(elevatorNode.isRegistered() ? "Yes" : "No");
        altitudeLabel.setText(String.format("%.4f", elevatorNode.getAltitude()));
        velocityLabel.setText(String.format("%.4f", elevatorNode.getVelocity()));
        destinationsLabel.setText(String.valueOf(elevatorNode.getDestinations()));
        rSocketLabel.setText(String.valueOf(elevatorNode.rSocket.getLocalPort()));
        sSocketLabel.setText(String.valueOf(elevatorNode.sSocket.getLocalPort()));
        capacityLabel.setText(String.valueOf(elevatorNode.getCapacity()));
    }

    private void updateEventLog() {
        logList.setItems(FXCollections.observableArrayList(elevatorNode.getLog().reversed()));
    }


    @FXML
    public void injectStuckHandler() {
        elevatorNode.injectStuck();
    }

    @FXML
    public void injectDoorStuckHandler() {
        elevatorNode.injectDoorStuck();
    }

    public void close() { elevatorNode.close(); }
}
