module g5.elevator {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens g5.elevator to javafx.fxml;
    opens g5.elevator.controllers to javafx.fxml;
    exports g5.elevator.controllers;
    exports g5.elevator;
    exports g5.elevator.model.elevator;
    exports g5.elevator.model.instruction;
    exports g5.elevator.model.floor;
    exports g5.elevator.model.events;
    exports g5.elevator.defs;
    exports g5.elevator.model;
    exports g5.elevator.model.scheduler_state;
}