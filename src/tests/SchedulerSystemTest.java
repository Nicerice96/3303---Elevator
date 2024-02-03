package tests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

/**
 * The SchedulerSystemTest class is responsible for conducting unit tests on the SchedulerSubsystem class.
 *
 * @author Mahad
 * @version 1.0
 */
public class SchedulerSystemTest {

    private SchedulerSystem schedulerSystem;

    @Before
    public void setUp() throws Exception {
        schedulerSystem = new SchedulerSystem();
        Thread.sleep(1000);
    }

    @Test
    public void testDataTransfer() throws InterruptedException {
        ArrayList<Object> testData = new ArrayList<>(Arrays.asList("0", "1", "UP", "2"));

        SchedulerSystem.putData(testData);

        Thread.sleep(500);

        ArrayList<Object> receivedData = SchedulerSystem.getData();

        assertEquals("The received data should match the test data sent.", testData, receivedData);
    }


}