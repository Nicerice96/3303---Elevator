package g5.elevator.controllers;

import g5.elevator.SchedulerLauncher;
import g5.elevator.model.elevator.ElevatorNode;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ElevatorSubsystemController implements Initializable, Updatable {
    @FXML
    GridPane startPane;
    @FXML
    Slider startSlider;
    @FXML
    Label startCountLabel;
    @FXML
    FlowPane elevators;
    private ArrayList<ElevatorNode> elevatorNodes;
    private boolean started = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        elevatorNodes = new ArrayList<>();
        startCountLabel.textProperty().bind(Bindings.format("%.0f", startSlider.valueProperty()));
        update();
    }

    @Override
    public void update() {
        startPane.setVisible(!started);
        startPane.setMaxHeight(started ? 0 : startPane.getMaxHeight());
    }



    @FXML
    public void startHandler() {
        for(int i = 0; i < startSlider.getValue(); i++) {
            ElevatorNode elevatorNode = new ElevatorNode(this);
            elevatorNode.start();
            FXMLLoader fxmlLoader = new FXMLLoader(SchedulerLauncher.class.getResource("elevator-view.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            elevators.getChildren().add(fxmlLoader.getRoot());

            elevatorNodes.add(elevatorNode);
        }
        started = true;
        update();
    }


    public void close() {
        for(ElevatorNode e : elevatorNodes) e.close();
    }
}
