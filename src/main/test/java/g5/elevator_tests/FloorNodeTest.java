package g5.elevator_tests;

import g5.elevator.model.events.Event;
import g5.elevator.model.events.EventType;
import g5.elevator.model.floor.FloorNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FloorNodeTest {
    private String testFilename = "testInstructions.txt";
    private FloorNode floorNode;
    private File testFile;

    @Before
    public void setUp() throws Exception {
        testFile = new File(testFilename);
        try (PrintWriter out = new PrintWriter(testFile)) {
            out.println("00:00:01,1,UP,2");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        floorNode = new FloorNode(1, testFilename);
    }

    @After
    public void tearDown() {
        if (floorNode != null) {
            floorNode.close();
        }

        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testEventLogging() {
        Event testEvent = new Event(EventType.RECEIVED, "Test event for logging");
        floorNode.addEvent(testEvent);
        ArrayList<Event> log = floorNode.getLog();

        assertFalse("Event log should not be empty", log.isEmpty());
        assertEquals("Event log should contain the test event", testEvent, log.get(log.size() - 1));
    }

    @Test
    public void testGetterMethods() {
        assertNotNull("sSocket should be initialized", floorNode.getSSocketPort());
        assertNotNull("rSocket should be initialized", floorNode.getRSocketPort());
        assertEquals("Floor number should match the initialized value", 1, floorNode.getFloor());
    }

    @Test
    public void testSetFilename() {
        FloorNode floorNode = new FloorNode(null, 10);
        floorNode.setFilename("testInstructions.txt");

        String newFilename = "newInstructions.txt";
        floorNode.setFilename(newFilename);

        assertEquals("Filename should be set correctly", newFilename, floorNode.getFilename());
    }

    @Test
    public void testEventLoggingWithNoEvents() {
        FloorNode floorNode = new FloorNode(null, 10);

        assertTrue("Event log should be empty initially", floorNode.getLog().isEmpty());
    }

    @Test
    public void testIsRegisteredAfterConstruction() {
        assertFalse("Floor node should not be registered initially", floorNode.isRegistered());
    }
}
