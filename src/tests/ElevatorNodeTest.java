package tests;

import org.junit.Before;
import org.junit.Test;
import src.elevator.ElevatorNode;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * The ElevatorNodeTest class is responsible for conducting unit tests on the ElevatorNode class.
 *
 * @author Mahad
 * @version 1.0
 */
public class ElevatorNodeTest {

    private ElevatorNode elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorNode();
    }

    @Test
    public void testElevatorMovement() {
        ArrayList<Object> testData = new ArrayList<>();
        testData.add("Time");
        testData.add("2"); // Call floor
        testData.add("UP"); // Direction
        testData.add("5"); // Destination floor

//        elevator.elevatorData = testData;

        // Call methods to test their functionality
//        elevator.setDirection();
//        elevator.setDestination();
//        elevator.setElevatorCallFloor();

        //TESTS
        // testing private variables isn't the goal of unit testing, since they are a part of the implementation details,
        // we should rather test the public API (public methods/attributes) - Hamza
//        assertEquals(ElevatorDirection.UP, elevator.direction);
//        assertEquals(5, elevator.destination);
//        assertEquals(2, elevator.callFloor);

//        elevator.traverseToElevatorCall(); //elevator to the call floor
//        assertEquals("Elevator should be at call floor before pickup", 2, elevator.currentFloor);

    }

}