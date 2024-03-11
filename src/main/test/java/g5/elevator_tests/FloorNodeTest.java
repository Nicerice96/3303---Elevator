package g5.elevator_tests;

import g5.elevator.model.floor.FloorNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * The g5.elevator.FloorNodeTest class is responsible for conducting unit tests on the FloorNode class.
 *
 * @author Mahad
 * @version 1.0
 */
public class FloorNodeTest {
    private FloorNode subsystem;
    // Defined the FloorNode instance here instead - Nabeel

    @Test
    public void testParseDataWithScheduler() {
        //test file with sample data
//        String testFilename = "test_data.txt";
//        try (PrintWriter out = new PrintWriter(testFilename)) {
//            out.println("1,UP,2,3");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            assertTrue("Failed to create test file", false);
//        }
//
//        subsystem = new FloorNode(testFilename);
//        subsystem.start();
//
//        //wait for the thread to finish its execution.
//        try {
//            subsystem.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            assertTrue("Thread interrupted", false);
//        }
//
//        //Tests
//        ArrayList<Object> data = SchedulerSystem.getPayload();
//        assertNotNull("Data should not be null", data);
//        assertEquals("Expected 4 elements in the array list", 4, data.size());
//        assertEquals("First element should match", "1", data.get(0));
//        assertEquals("Second element should match", "UP", data.get(1));
//        assertEquals("Third element should match", "2", data.get(2));
//        assertEquals("Fourth element should match", "3", data.get(3));

        //remove test file
//        new File(testFilename).delete();
    }

}