import Controllers.ElevatorSystem;
import Enums.ElevatorType;
import Strategies.SimpleElevatorAllocationStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem();
        elevatorSystem.setAllocationStrategy(new SimpleElevatorAllocationStrategy());
        System.out.println("For elevator status - 1");
        System.out.println("For requesting elevator - 2");
        System.out.println("For adding stop - 3");
        System.out.println("For adding new elevator - 4");
        Scanner scanner = new Scanner(System.in);
        int floor;
        while (true) {
            int req = scanner.nextInt();
            switch(req){
                case 1:
                    elevatorSystem.getStatus();
                    break;
                case 2:
                    System.out.print("Type floor number: ");
                    floor = scanner.nextInt();
                    elevatorSystem.requestElevator(floor);
                    break;
                case 3:
                    System.out.print("Type floor number: ");
                    floor = scanner.nextInt();
                    System.out.print("Type elevator number: ");
                    int elevator = scanner.nextInt();
                    elevatorSystem.addStop(elevator, floor);
                    break;
                case 4:
                    System.out.print("Enter type of elevator (CLASSIC/SERVICE/ACCEL): ");
                    String type = scanner.next();
                    ElevatorType elevatorType;
                    try {
                        elevatorType = ElevatorType.valueOf(type.toUpperCase());
                        elevatorSystem.addElevator(elevatorType);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid elevator type: " + type);
                    }
                    break;
                default:
                    System.out.println("Invalid input!!!");
            }
        }
    }
}