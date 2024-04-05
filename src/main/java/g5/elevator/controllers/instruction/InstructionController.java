package g5.elevator.controllers.instruction;

import g5.elevator.controllers.Updatable;
import g5.elevator.model.instruction.Instruction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructionController implements Initializable {
    private Instruction instruction;
    @FXML
    Label timestampLabel, pickupTimeLabel, dropOffTimeLabel, totalTimeLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Sets the instruction only for the first time
     * @param instruction instruction
     */
    public void setInstruction(Instruction instruction) {
        if(this.instruction != null) return;
        this.instruction = instruction;
        timestampLabel.setText(instruction.getTimestamp().toString());
        update();
    }

    public boolean equals(Instruction instruction) {
        return this.instruction.getTimestamp().equals(instruction.getTimestamp());
    }

    public void update() {
        pickupTimeLabel.setText(String.format("%s ms", instruction.getPickupTime()));
        dropOffTimeLabel.setText(String.format("%s ms", instruction.getDropoffTime()));
        totalTimeLabel.setText(String.format("%s ms", instruction.getTotalTime()));
    }


}
