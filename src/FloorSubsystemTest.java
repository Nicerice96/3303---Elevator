import org.junit.Test;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FloorSubsystemTest {

    /**
     * Test method for parseData to verify it parses data correctly and places it into SchedulerSystem.
     */
    @Test
    public void testParseDataWithScheduler() {
        //test file with sample data
        String testFilename = "test_data.txt";
        try (PrintWriter out = new PrintWriter(testFilename)) {
            out.println("1,UP,2,3");

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Failed to create test file", false);
        }

        FloorSubsystem subsystem = new FloorSubsystem(testFilename);
        subsystem.start();

        //wait for the thread to finish its execution.
        try {
            subsystem.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            assertTrue("Thread interrupted", false);
        }

       //Tests
        ArrayList<Object> data = SchedulerSystem.getData();
        assertNotNull("Data should not be null", data);
        assertEquals("Expected 4 elements in the array list", 4, data.size());
        assertEquals("First element should match", "1", data.get(0));
        assertEquals("Second element should match", "UP", data.get(1));
        assertEquals("Third element should match", "2", data.get(2));
        assertEquals("Fourth element should match", "3", data.get(3));

       //remove test file
        new File(testFilename).delete();
    }

}
