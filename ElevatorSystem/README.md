# Elevator System

The Elevator System is a program that simulates an elevator control system. It includes classes for managing elevators, different elevator types, and strategies for allocating elevators to requests.

## ElevatorSystem

The `ElevatorSystem` class is the main controller class for the elevator system. It maintains a list of elevators, handles elevator requests from external switches, and processes these requests using an elevator allocation strategy.

### Class Structure

- `ElevatorSystem`
  - `elevatorList`: A list of elevators in the system.
  - `numElevators`: The number of elevators currently in the system.
  - `requests`: A queue to store elevator requests from external switches.
  - `requestProcessorScheduler`: A scheduled executor service for processing elevator requests.
  - `allocationStrategy`: The elevator allocation strategy used to assign elevators to requests.

### Methods

- `addElevator(ElevatorType type)`: Adds a new elevator of the specified type to the system.
- `requestElevator(int fromFloor)`: Simulates an external switch requesting an elevator from a specific floor.
- `addStop(int elevatorId, int floor)`: Simulates an internal switch in an elevator, adding a stop at a specific floor.
- `requestProcessor()`: Processes elevator requests from the queue and assigns them to the appropriate elevator based on the allocation strategy.
- `getStatus()`: Prints the current status of all elevators in the system.
- `setAllocationStrategy(ElevatorAllocationStrategy allocationStrategy)`: Sets the elevator allocation strategy for the system.

## Elevator

The `Elevator` class represents an elevator in the system. It maintains information such as the current floor, direction, capacity, and stops requested by internal switches.

### Class Structure

- `Elevator`
  - `requestProcessorScheduler`: A scheduled executor service for processing elevator requests.
  - `id`: The unique identifier of the elevator.
  - `currentFloor`: The current floor where the elevator is located.
  - `status`: The status of the elevator (e.g., working, out of service).
  - `floors`: A list to track the floors where the elevator has stops.
  - `direction`: The direction in which the elevator is moving.
  - `maxCapacity`: The maximum capacity of the elevator.
  - `currentCapacity`: The current number of passengers in the elevator.

### Methods

- `addStop(int floor)`: Simulates an internal switch in the elevator, adding a stop at a specific floor.
- `requestProcessor()`: Processes the stops requested by internal switches and updates the elevator's state accordingly.
- `getStatus()`: Gets the status of the elevator.
- Getter and setter methods for various properties of the elevator.

## ElevatorAllocationStrategy

The `ElevatorAllocationStrategy` interface defines the contract for different strategies used to allocate elevators to requests. It includes a single method, `getElevator`, which takes a list of elevators and a floor as input and returns the ID of the elevator that should be assigned to the request.

### Interface Structure

- `ElevatorAllocationStrategy`
  - `getElevator(List<Elevator> elevatorList, int floor)`: Assigns an elevator from the given list to the specified floor.

### SimpleElevatorAllocationStrategy

The `SimpleElevatorAllocationStrategy` class is an implementation of the `ElevatorAllocationStrategy` interface. It provides a simple strategy for allocating elevators based on certain criteria.

#### Class Structure

- `SimpleElevatorAllocationStrategy`

#### Methods

- `getElevator(List<Elevator> elevatorList, int floor)`: Assigns the elevator with the closest distance to the specified floor, considering factors such as elevator direction and current capacity.

---

### TODO
- Error Handling and Exception Handling: The code lacks explicit error handling and exception handling mechanisms. Adding appropriate error handling and exception handling would make the code more robust and reliable.
- Improve Logging: Instead of using System.out.println() for logging, consider using a logging framework such as Log4j or java.util.logging. This allows for more advanced logging capabilities, such as log levels, log file rotation, and customizable log formats.
- Implement Unit Tests: Write unit tests to verify the functionality of the classes and ensure that they work as expected. This will help catch bugs early and make it easier to maintain and refactor the code in the future.
