package g5.elevator_tests;

import g5.elevator.controllers.SchedulerController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SchedulerControllerTest {

    @Test
    public void testGetPickupMean() {
        SchedulerController controller = new SchedulerController();

        long pickupSum = 1000;
        int pickupSampleSize = 10;

        double expectedMean = (double) pickupSum / pickupSampleSize;

        double actualMean = controller.getPickupMean(pickupSum, pickupSampleSize);

        assertEquals(expectedMean, actualMean, 0.001);
    }

    @Test
    public void testGetDropoffMean() {
        SchedulerController controller = new SchedulerController();

        long dropoffSum = 1500;
        int dropoffSampleSize = 10;

        double expectedMean = (double) dropoffSum / dropoffSampleSize;

        double actualMean = controller.getDropoffMean(dropoffSum, dropoffSampleSize);

        assertEquals(expectedMean, actualMean, 0.001);
    }

    @Test
    public void testGetTotalMean() {
        SchedulerController controller = new SchedulerController();

        long totalSum = 2000;
        int totalSampleSize = 10;

        double expectedMean = (double) totalSum / totalSampleSize;

        double actualMean = controller.getTotalMean(totalSum, totalSampleSize);

        assertEquals(expectedMean, actualMean, 0.001);
    }
}
