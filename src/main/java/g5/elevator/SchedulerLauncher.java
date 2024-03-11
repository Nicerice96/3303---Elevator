package g5.elevator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SchedulerLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulerLauncher.class.getResource("scheduler-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Scheduler System");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) { launch(); }
}
