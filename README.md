Elevator Control System
=======================

Description:
------------
This project implements a simulated elevator control system designed for efficiency and scalability. The system consists of three main components: a Scheduler, an ElevatorNode, and a FloorSubsystem. These components work together to manage and respond to elevator requests from various floors, simulating the operation of a real-world elevator system in a multi-story building.

Team:
-------------------
- Mahad Mohamed Yonis #101226808
- Nabeel Azard #101152007
- Zarif Khan #101224172
- Hamza Alsarakbi #101221018
- Arun Karki #101219923

Files:
------
- ElevatorNodeController.java: Elevator Sub-system which manages an elevator's behaviour. This class recieves instructions from the Scheduler and contains methods that carry-out the given instructions.
- ElevatorSubsystemController.java: Implements the UI for the Elevator Subsystem Window.
- FloorNodeController.java: This class is a controller class responsible for managing the user interface (UI) components associated with a floor node.
- 'FloorNode.java': Floor Sub-system which manages floor-related behavior. This class reads instructions from a file, parses them, and sends them to the SchedulerSystem.
- 'ProcessingFloorAddInstructionState.java': Process the instructions sent to the floor and forwards them to the scheduler.
- 'SchedulerProcessingFloorRequestState': Process the floor requests sent by the scheduler.
- `ElevatorNode.java`: Represents the elevator's functionality, responding to requests and simulating elevator movements.
- `SchedulerSystem.java`: Acts as the central coordinator, receiving requests from the `FloorSubsystem.java` and assigning them to the `ElevatorNode`.
- `Direction.java`: Contains an enum contain possible directions for a given elevator node. 
- `Event.java`: Summarizes the occuring events. This would be printed in the Java UI frame.
- `Event Type.java`: Describes an enum to house each type of event that can be encountered during the execution of the project.
- `Event Test.java`: Adequately test's each event for correctness.
- 'SchedulerFaultState.java': Handles errors that may occur in the program.
- 'SchedulerIdleState.java': Waits for an instruction.
- 'SchedulerProcessingRegistrationState.java': Registers an elevator.
- 'ProcessingForwardEventState.java': Represents the even state that is to be processed.
- 'SchedulerProcessingElevatorRequestState.java': Where the scheduler processes the requests from the floor and sends it to the elevator.
- 'ElevatorDoorClosingState.java': Performs the action of when the elevator door is closing.
- 'ElevatorDoorOpeningState.java': Performs the action of when the elevator door is opening.
- 'ElevatorDoorStuckState.java': Performs the action of when the elevator door is stuck.
- 'ElevatorDoorOpenState.java': Performs the action of when the elevator door is open.
- 'ElevatorIdleState.java': Represents what occurs when the elevator waits for an instruction.
- 'ElevatorMovingState.java': Represents what occurs when the elevator executes an instruction.
- 'ElevatorStuckState.java': Represents what occurs when the elevator is stuck.
- 'ElevatorState.java': Represents the general elevator state.
- 'ElevatorCommState.java': General Comm State, acts as an interface and holds the common attributes.
- 'ElevatorIdleCommState.java': Waits until datagram packet is received and processes the packet once it is recieved.
- 'ElevatorProcessingAddPickupCommState.java': Represents the process of adding a pickup to an elevator.
- 'ElevatorProcessingCommState.java': Represents the communication between Elevator and the Scheduler.
- 'ElevatorProcessingGetPickupIndexCommState.java': Represents the process of getting the index of the pickup locaiton in a datagram packet.
- `ElevatorNodeTest.java`: Provides tests for the `ElevatorNode` to verify its operations and state transitions.
- `SchedulerSystemTest.java`: Tests the scheduling logic within the `SchedulerSystem` to ensure efficient and correct request handling.
- `testCase_1.txt`: Sample input file used by `FloorSubsystem` for testing. Contains elevator request scenarios to simulate real-world elevator calls.
- `Instruction`: Representation of an elevator instruction that is read from a text file.

Setup Instructions:
-------------------
1. Clone or download the project to your local machine.
2. Open a terminal or command prompt and navigate to the project's root directory.
3. Compile the Java files with the command: `javac *.java`.
4. Run the SchedulerLauncher, ElevatorSubsystemLauncher, and FloorSubsystemLauncher in any 
   order.
5. Click start on the Elevator window.
6. Click start on the Floor window.
7. (Optional) Run the "tests" package.
 

Test Instructions:
------------------
1. Unit tests are provided for each subsystem. They can be run to ensure each part of the system functions as expected.
2. Compile and run the test files separately, for example: `javac g5.elevator.FloorSubsystemTest.java && java g5.elevator.FloorSubsystemTest`.
3. To test the system as a whole, modify the input data in the `FloorSubsystem` to simulate different scenarios and observe how the system handles various elevator requests.

Credits:
-------------------


### Ieration 1
__Mahad Mohamed Yonis__: Co-author of README file. Author of all methods in FloorSubsystemTest, SchedulerSubsystemTest, ElevatorNodeTest classes. Author of methods doorOpen(), doorClose(), peopleLoad(), peopleUnload() methods in the ElevatorNode class.

__Nabeel Azard__: Co-author of README file. Author of UML class diagram for the program. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), traverseToElevatorCall(), differenceBetweenDestinationAndCurrentFloor(),  and run() methods in the ElevatorNode class.

__Zarif Khan__: Co-author of README file. Author of Sequence diagram for the program, Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), traverseToElevatorCall(), setElevatorCallFloor(), differenceBetweenDestinationAndCurrentFloor(), and run() methods in the ElevatorNode class.

__Hamza Alsarakbi__: Co-author of README file. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), setElevatorCallFloor(),  run() methods in the ElevatorNode class.

__Arun Karki__: Co-author of README file. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), setElevatorCallFloor(),  run() methods in the ElevatorNode class.

### Iteration 2
__Hamza Alsarakbi__: State diagrams, Elevator States, Class diagram

__Mahad Mohamed Yonis__: Elevator node tests, Documentation, State diagram assistance 

__Nabeel Azard__: Elevator state tests, Documentation, State diagram assitance

__Arun Karki__: Minor bug fixes, State diagram assistance

__Zarif Khan__: Scheduler States, Scheduler State test, Updated Class/State diagrams

### Iteration 3
__Hamza Alsarakbi__: 
Worked on Scheduler Communication: Floor Registration, Elevator Events, Floor Pickup Request. 
Worked on ElevatorNode communication: Initialize sockets on unspecified ports, Events List, Pickup Requests, implemented getPickupIndex() in ElevatorNode. Designed and implemented a UI for the system.
Documentation: Scheduler Sequence Diagram, Elevator State Diagram, Elevator Comm State Diagram

__Mahad Mohamed Yonis__: 
Worked on ElevatorNode communication: Implemented run() method in ElevatorProcessingAddPickupCommState. 
Documentation: Elevator Sequence Diagram
Implemented Unit Tests

__Nabeel Azard__: 
Worked on Scheduler Communication: Initialize sockets, Elevator Registration, 
Worked on ElevatorNode communication: implemetnted run() method in ElevatorIdleCommState, register() method in ElevatorNode, and sentEvent() method in ElevatorNode. 
Documentation: README file, Scheduer State Diagram

__Arun Karki__: 
Implemented FloorNode communication: Initialize sockets on unspecified ports, Floor Registration, Floor Pickup Request, Elevator Events.
Documentation: Class Diagram

__Zarif Khan__: 
Implemented FloorNode communication: Initialize sockets on unspecified ports, Floor Registration, Floor Pickup Request, Elevator Events.
Documentation: Floor Sequence Diagram

### Iteration 4
__Hamza Alsarakbi__: 
Finalized: System Design Planning, Completed UI elements of the project, finalized ElevatorNode, Final Project Report documentation, Project Class Diagram

__Mahad Mohamed Yonis__: 
Documentation: Final Project Report document

__Arun Karki__: 
Finalized: FloorNode, ElevatorNode, README file, ElevatorSubsystem Sequence Diagram. SchedulerSubsystem Sequence Diagram, FloorSubsystem Sequence Diagram, Project Class Diagram

__Nabeel Azard__: 
Finalized: ElevatorNode, SchedulerSystem, README file, Final Project Report Documentation, System Design Planning

__Zarif Khan__: 
Finalized: FloorNode, ElevatorNode, README file, ElevatorSubsystem Sequence Diagram. SchedulerSubsystem Sequence Diagram, FloorSubsystem Sequence Diagram, Project Class Diagram

MIT License:
-----------------
Copyright (c) 2023 [Nabeel Azard, Mahad Mohamed Yonis, Hamza Alsarakbi, Zarif Khan, Arun Karki]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
