package org.anuva04.Controllers;

import org.anuva04.Models.MeetingDb;

import java.util.*;

// Entry point to a Meeting Scheduler app
public class MeetingScheduler {
    public MeetingDb meetingDb;

    public MeetingScheduler() {
        this.meetingDb = MeetingDb.getInstance();
    }

    // Simulates calling various methods on UI
    public void init() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Enter command: ");
            String command = sc.nextLine();
            Scanner scanner = new Scanner(command);
            List<String> tokens = new ArrayList<>();
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
            scanner.close();

            // Command
            String method = tokens.get(0);
            // List of arguments
            Map<String, String> args = new HashMap<>();
            for (int i = 1; i < tokens.size(); i += 2) {
                String option = tokens.get(i);
                String value = tokens.get(i + 1);
                args.put(option, value);
            }

            try {
                String errorMessage = CommandLineParser.parseAndExecuteCommand(method, args);
                if(errorMessage == null) {
                    System.out.println("Command executed successfully.");
                } else {
                    System.out.println("Command validation failed at: " + errorMessage);
                }
            } catch (Exception e) {
                System.out.println("An exception has occurred while executing command: " + e.getMessage());
            }
        }
    }
}
