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
- `FloorSubsystem.java`: Handles the simulation of floor-related events, including the generation of elevator requests.
- `FloorSubsystemTest.java`: Contains unit tests for the `FloorSubsystem`, ensuring its reliability in processing and forwarding requests.
- `ElevatorNode.java`: Represents the elevator's functionality, responding to requests and simulating elevator movements.
- `ElevatorNodeTest.java`: Provides tests for the `ElevatorNode` to verify its operations and state transitions.
- `SchedulerSystem.java`: Acts as the central coordinator, receiving requests from the `FloorSubsystem` and assigning them to the `ElevatorNode`.
- `SchedulerSystemTest.java`: Tests the scheduling logic within the `SchedulerSystem` to ensure efficient and correct request handling.
- `testCase_1.txt`: Sample input file used by `FloorSubsystem` for testing. Contains elevator request scenarios to simulate real-world elevator calls.

Setup Instructions:
-------------------
1. Clone or download the project to your local machine.
2. Open a terminal or command prompt and navigate to the project's root directory.
3. Compile the Java files with the command: `javac *.java`.
4. Run the Scheduler: `java SchedulerSystem`. to simulate the system:
 

Test Instructions:
------------------
1. Unit tests are provided for each subsystem. They can be run to ensure each part of the system functions as expected.
2. Compile and run the test files separately, for example: `javac FloorSubsystemTest.java && java FloorSubsystemTest`.
3. To test the system as a whole, modify the input data in the `FloorSubsystem` to simulate different scenarios and observe how the system handles various elevator requests.

Credits:
-------------------

### Milstone 1
Mahad Mohamed Yonis: Co-author of README file. Author of all methods in FloorSubsystemTest, SchedulerSubsystemTest, ElevatorNodeTest classes. Author of methods doorOpen(), doorClose(), peopleLoad(), peopleUnload() methods in the ElevatorNode class.

Nabeel Azard: Co-author of README file. Author of UML class diagram for the program. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), traverseToElevatorCall(), differenceBetweenDestinationAndCurrentFloor(),  and run() methods in the ElevatorNode class.

Zarif Khan: Co-author of README file. Author of Sequence diagram for the program, Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), traverseToElevatorCall(), setElevatorCallFloor(), differenceBetweenDestinationAndCurrentFloor(), and run() methods in the ElevatorNode class.

Hamza Alsarakbi: Co-author of README file. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), setElevatorCallFloor(),  run() methods in the ElevatorNode class.

Arun Karki: Co-author of README file. Co-author of all methods in the FloorSubsystem, Schedulersystem classes. Co-author of traverseOneFloor(), setDirection(), setElevatorCallFloor(),  run() methods in the ElevatorNode class.
### Milestone 2

Zarif & Arun : Diligently addressed and resolved bugs in the code, significantly enhancing its reliability and efficiency. 


MIT License:
-----------------
Copyright (c) 2023 [Nabeel Azard, Mahad Mohamed Yonis, Hamza Alsarakbi, Zarif Khan, Arun Karki]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
