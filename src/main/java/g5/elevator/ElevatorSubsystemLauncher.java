package g5.elevator;

import g5.elevator.controllers.elevator.ElevatorSubsystemController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ElevatorSubsystemLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulerLauncher.class.getResource("elevators-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 520);
        stage.setTitle("Elevator Subsystem");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> onClose(fxmlLoader.getController()));
    }

    private void onClose(ElevatorSubsystemController controller) {
        controller.close();
    }


    public static void main(String[] args) { launch(); }
}
