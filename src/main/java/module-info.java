module g5.elevator {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens g5.elevator to javafx.fxml;
    exports g5.elevator;
}