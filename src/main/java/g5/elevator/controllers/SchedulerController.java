package g5.elevator.controllers;

import g5.elevator.SchedulerLauncher;
import g5.elevator.controllers.instruction.InstructionController;
import g5.elevator.model.SchedulerSystem;
import g5.elevator.model.events.Event;
import g5.elevator.model.instruction.Instruction;
import g5.elevator.model.scheduler_state.SchedulerFaultState;
import g5.elevator.model.scheduler_state.SchedulerIdleState;
import g5.elevator.model.scheduler_state.SchedulerProcessingRegistrationState;
import g5.elevator.model.scheduler_state.elevator.ProcessingForwardEventState;
import g5.elevator.model.scheduler_state.elevator.SchedulerProcessingElevatorRequestState;
import g5.elevator.model.scheduler_state.floor.ProcessingFloorAddInstructionState;
import g5.elevator.model.scheduler_state.floor.SchedulerProcessingFloorRequestState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SchedulerController implements Initializable, Updatable {
    private SchedulerSystem schedulerSystem;
    @FXML
    ListView<Event> logList;
    @FXML
    RadioButton idleButton, faultButton, pElevatorRequestButton, pFloorRequestButton, pRegistrationRequestButton, forwardEventButton, addInstructionButton;
    @FXML
    VBox instructionsPane;
    @FXML
    Label   pickupConfidence90Label, dropoffConfidence90Label, totalConfidence90Label,
            pickupConfidence95Label, dropoffConfidence95Label, totalConfidence95Label,
            pickupConfidence99Label, dropoffConfidence99Label, totalConfidence99Label,
            meanPickupTimeLabel, meanDropoffTimeLabel, meanTotalTimeLabel;
    ArrayList<InstructionController> instructions = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        schedulerSystem = new SchedulerSystem(this);
        schedulerSystem.start();
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            logList.setItems(FXCollections.observableArrayList(schedulerSystem.getLog().reversed()));
            idleButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerIdleState);
            faultButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerFaultState);
            pElevatorRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingElevatorRequestState);
            pFloorRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingFloorRequestState);
            pRegistrationRequestButton.setSelected(schedulerSystem.getSchedulerState() instanceof SchedulerProcessingRegistrationState);
            forwardEventButton.setSelected(schedulerSystem.getSchedulerState() instanceof ProcessingForwardEventState);
            addInstructionButton.setSelected(schedulerSystem.getSchedulerState() instanceof ProcessingFloorAddInstructionState);
            long pickupSum = 0;
            int pickupSampleSize = 0;
            long dropoffSum = 0;
            int dropoffSampleSize = 0;
            long totalSum = 0;
            for(Instruction instruction : schedulerSystem.getAllInstructions()) {
                InstructionController ic = getInstructionController(instruction);
                // make an instruction if one doesn't exist already
                if(ic == null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(SchedulerLauncher.class.getResource("instruction-view.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    instructionsPane.getChildren().add(fxmlLoader.getRoot());
                    instructions.add(fxmlLoader.getController());
                    ic = fxmlLoader.getController();
                    ic.setInstruction(instruction);
                }
                ic.update();
                // update sum values
                if(instruction.getPickupTime() > 0) {
                    pickupSum += instruction.getPickupTime();
                    pickupSampleSize++;
                }
                if(instruction.getDropoffTime() > 0) {
                    dropoffSum += instruction.getDropoffTime();
                    totalSum += instruction.getTotalTime();
                    dropoffSampleSize++;
                }
            }
            updateMeanTable(pickupSum, pickupSampleSize, dropoffSum, dropoffSampleSize, totalSum);
        });
    }

    private InstructionController getInstructionController(Instruction instruction) {
        for(InstructionController ic : instructions) {
            if(ic.equals(instruction)) return ic;
        }
        return null;
    }

    private void updateMeanTable(long pickupSum, int pickupSampleSize, long dropoffSum, int dropoffSampleSize, long totalSum) {
        if(pickupSampleSize > 0) {
            double pickupMean = (double) pickupSum /pickupSampleSize;
            meanPickupTimeLabel.setText(String.format("%.0f ms", pickupMean));
            double sd = getPickupSd(pickupMean, pickupSampleSize);
            double E90 = 1.645 * (sd/Math.sqrt(pickupSampleSize));
            double E95 = 1.96 * (sd/Math.sqrt(pickupSampleSize));
            double E99 = 2.575 * (sd/Math.sqrt(pickupSampleSize));
            pickupConfidence90Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, pickupMean-E90), pickupMean+E90));
            pickupConfidence95Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, pickupMean-E95), pickupMean+E95));
            pickupConfidence99Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, pickupMean-E99), pickupMean+E99));
        }
        if(dropoffSampleSize > 0) {
            // drop off
            double dropoffMean = (double) dropoffSum /dropoffSampleSize;
            meanDropoffTimeLabel.setText(String.format("%.0f ms", dropoffMean));
            double dropoffSd = getDropoffSd(dropoffMean, dropoffSampleSize);
            double dropoffE90 = 1.645 * (dropoffSd/Math.sqrt(dropoffSampleSize));
            double dropoffE95 = 1.96 * (dropoffSd/Math.sqrt(dropoffSampleSize));
            double dropoffE99 = 2.575 * (dropoffSd/Math.sqrt(dropoffSampleSize));
            dropoffConfidence90Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, dropoffMean-dropoffE90), dropoffMean+dropoffE90));
            dropoffConfidence95Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, dropoffMean-dropoffE95), dropoffMean+dropoffE95));
            dropoffConfidence99Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, dropoffMean-dropoffE99), dropoffMean+dropoffE99));

            // total
            double totalMean = (double) totalSum /dropoffSampleSize;
            meanTotalTimeLabel.setText(String.format("%.0f ms", totalMean));
            double totalSd = getTotalSd(totalMean, dropoffSampleSize);
            double totalE90 = 1.645 * (totalSd/Math.sqrt(dropoffSampleSize));
            double totalE95 = 1.96 * (totalSd/Math.sqrt(dropoffSampleSize));
            double totalE99 = 2.575 * (totalSd/Math.sqrt(dropoffSampleSize));
            totalConfidence90Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, totalMean-totalE90), totalMean+totalE90));
            totalConfidence95Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, totalMean-totalE95), totalMean+totalE95));
            totalConfidence99Label.setText(String.format("(%.0f ms, %.0f ms)", Math.max(0, totalMean-totalE99), totalMean+totalE99));
        }
    }

    private double getPickupSd(double mean, double sampleSize) {
        if(sampleSize == 0) return 0;
        double runningTotal = 0;
        for(Instruction i : schedulerSystem.getAllInstructions()) {
            if(i.getPickupTime() == 0) continue;
            runningTotal += Math.pow(i.getPickupTime() - mean, 2);
        }
        return Math.sqrt(runningTotal / sampleSize);
    }
    private double getDropoffSd(double mean, double sampleSize) {
        if(sampleSize == 0) return 0;
        double runningTotal = 0;
        for(Instruction i : schedulerSystem.getAllInstructions()) {
            if(i.getDropoffTime() == 0) continue;
            runningTotal += Math.pow(i.getDropoffTime() - mean, 2);
        }
        return Math.sqrt(runningTotal / sampleSize);
    }
    private double getTotalSd(double mean, double sampleSize) {
        if(sampleSize == 0) return 0;
        double runningTotal = 0;
        for(Instruction i : schedulerSystem.getAllInstructions()) {
            if(i.getTotalTime() == 0) continue;
            runningTotal += Math.pow(i.getTotalTime() - mean, 2);
        }
        return Math.sqrt(runningTotal / sampleSize);
    }

    public void close() { schedulerSystem.close(); }

    public double getPickupMean(long pickupSum, int pickupSampleSize) {
        if (pickupSampleSize == 0) {
            return 0;
        }
        return (double) pickupSum / pickupSampleSize;
    }

    public double getDropoffMean(long dropoffSum, int dropoffSampleSize) {
        if (dropoffSampleSize == 0) {
            return 0;
        }
        return (double) dropoffSum / dropoffSampleSize;
    }


    public double getTotalMean(long totalSum, int sampleSize) {
        if (sampleSize == 0) {
            return 0;
        }
        return (double) totalSum / sampleSize;
    }

}
