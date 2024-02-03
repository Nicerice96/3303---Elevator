import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * The ElevatorSubsystemTest class is responsible for conducting unit tests on the ElevatorSubsystem class.
 *
 * @author Mahad
 * @version 1.0
 */
public class ElevatorSubsystemTest {

    private ElevatorSubsystem elevator;

    @Before
    public void setUp() {
        elevator = new ElevatorSubsystem();
    }

    @Test
    public void testElevatorMovement() {
        ArrayList<Object> testData = new ArrayList<>();
        testData.add("Time");
        testData.add("2"); // Call floor
        testData.add("UP"); // Direction
        testData.add("5"); // Destination floor

        elevator.elevatorData = testData;

        // Call methods to test their functionality
        elevator.setDirection();
        elevator.setDestination();
        elevator.setElevatorCallFloor();

        //TESTS
        assertEquals(ElevatorDirection.UP, elevator.direction);
        assertEquals(5, elevator.destination);
        assertEquals(2, elevator.callFloor);

        elevator.traverseToElevatorCall(); //elevator to the call floor
        assertEquals("Elevator should be at call floor before pickup", 2, elevator.currentFloor);

    }

}