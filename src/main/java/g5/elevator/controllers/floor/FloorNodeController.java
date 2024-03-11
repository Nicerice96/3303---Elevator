package g5.elevator.controllers.floor;

import g5.elevator.controllers.Updatable;
import g5.elevator.model.events.Event;
import g5.elevator.model.floor.FloorNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class FloorNodeController implements Initializable, Updatable {
    @FXML
    Label floorLabel, registeredLabel, rSocketLabel, sSocketLabel;
    @FXML
    ListView<Event> logList;
    private FloorNode floorNode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void init(int floor) {
        floorNode = new FloorNode(this, floor);
        // TODO: remove this and implement proper file loading
        floorNode.setFilename("testCase_1.txt");
        floorNode.start();
        floorLabel.setText(String.valueOf(floorNode.getFloor()));
        update();
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            updateData();
            updateLogList();
        });
    }

    private void updateLogList() {
        logList.setItems(FXCollections.observableArrayList(floorNode.getLog().reversed()));
    }

    private void updateData() {
        registeredLabel.setText(floorNode.isRegistered() ? "Yes" : "No");
        rSocketLabel.setText(String.valueOf(floorNode.getRSocketPort()));
        sSocketLabel.setText(String.valueOf(floorNode.getSSocketPort()));
    }

    public void close() { floorNode.close(); }
}
