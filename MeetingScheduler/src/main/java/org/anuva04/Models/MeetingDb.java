package org.anuva04.Models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// This is a singleton class
// In a real application, this class will contain Database connections and methods to perform CRUD operations on DB
public class MeetingDb {
    // Using ConcurrentHashMap to make it thread-safe

    // This represents Calendar table where all meetings are bucketized for each day
    // Each bucket contains a document {username : list of meetings for that day}
    public ConcurrentHashMap<LocalDate, Map<String, HashSet<UUID>>> meetingCalendar;
    public ConcurrentHashMap<UUID, Meeting> meetingMetadata;

    private static MeetingDb instance;

    private MeetingDb() {
        this.meetingCalendar = new ConcurrentHashMap<>();
        this.meetingMetadata = new ConcurrentHashMap<>();
    }

    // Creates a new instance of DB if not already created. This ensures that only 1 instance of DB exists
    public static MeetingDb getInstance() {
        // This if condition ensures that all getInstance calls are not synchronized in case instance is already initialised.
        if(instance == null) {
            synchronized (MeetingDb.class) {
                // This if condition ensures that 2 parallel threads calling getInstance() at the same time don't create 2 instances
                if(instance == null) {
                    instance = new MeetingDb();
                }
            }
        }
        return instance;
    }

    // Method to create a new meeting
    public void createMeeting(String organizer, LocalDateTime startTime, int duration, String location, String title, List<String> invitees) {
        UUID id = UUID.randomUUID();
        LocalDate date = startTime.toLocalDate();
        Meeting meeting = new Meeting(title, location, organizer, startTime, duration, id);

        for(String invitee : invitees) {
            addInvitee(date, invitee, id);
        }
        addInvitee(date, organizer, id);
        meetingMetadata.put(id, meeting);

        // Output
        System.out.println("Meeting created successfully with id: " + id);
        getMeetingsForUsernameForDate(organizer, date);
    }

    // Method to delete an existing meeting
    public void deleteMeeting(UUID meetingId, String requester) {
        Meeting meeting = meetingMetadata.get(meetingId);
        if(meeting == null) {
            System.out.println("Meeting doesn't exist!");
            return;
        }
        if(!meeting.organizer.equals(requester)) {
            System.out.println("You don't have permission to delete this meeting!");
            return;
        }
        LocalDate date = meeting.startTime.toLocalDate();
        Set<String> invitees = meeting.invitees;
        for(String invitee : invitees) {
            meetingCalendar.get(date).get(invitee).remove(meetingId);
        }
        meetingCalendar.get(date).get(requester).remove(meetingId);
        meetingMetadata.remove(meetingId);

        // Output
        System.out.println("Meeting deleted successfully!");
        getMeetingsForUsernameForDate(requester, date);
    }

    // Method to retrieve all meetings of a given user on a given date
    public void getMeetingsForUsernameForDate(String username, LocalDate date) {
        List<Meeting> meetings = new ArrayList<>();
        if(meetingCalendar.get(date) == null) {
            System.out.println("No meetings on date: " + date);
            return;
        }
        if(meetingCalendar.get(date).get(username) == null) {
            System.out.println("No meetings of user " + username + " on " + date);
            return;
        }
        for(UUID id : meetingCalendar.get(date).get(username)) {
            meetings.add(meetingMetadata.get(id));
        }

        if(meetings.size() == 0) {
            System.out.println("No meetings available for user: " + username);
            return;
        }

        // Output
        System.out.println(username + "'s meetings on " + date + " are:");
        for(Meeting meeting : meetings) {
            System.out.println(
                    "Title: " + meeting.title +
                    " | Id: " + meeting.id +
                    " | StartTime: " + meeting.startTime.toString() +
                    " | Duration: " + meeting.duration
            );
        }
    }

    // Method to change meeting time and duration
    public void changeMeetingTime(UUID meetingId, LocalDateTime startTime, int duration, String requester) {
        Meeting meeting = meetingMetadata.get(meetingId);
        if(meeting == null) {
            System.out.println("Meeting doesn't exist!");
            return;
        }

        if(!meeting.organizer.equals(requester)) {
            System.out.println("You don't have permission to edit this meeting!");
            return;
        }

        // Moving meeting for invitees from previous date bucket to new date bucket in calendar
        Set<String> invitees = meetingMetadata.get(meetingId).invitees;
        LocalDate originalDate = meetingMetadata.get(meetingId).startTime.toLocalDate();
        LocalDate newDate = startTime.toLocalDate();
        for(String invitee : invitees) {
            meetingCalendar.get(originalDate).get(invitee).remove(meetingId);
            addInvitee(newDate, invitee, meetingId);
        }

        addInvitee(newDate, requester, meetingId);

        // Changing startTime and duration in metadata table
        this.meetingMetadata.get(meetingId).startTime = startTime;
        this.meetingMetadata.get(meetingId).duration = duration;

        // Output
        System.out.println(
                "Title: " + this.meetingMetadata.get(meetingId).title +
                " | StartTime: " + this.meetingMetadata.get(meetingId).startTime.toString() +
                " | Duration: " + this.meetingMetadata.get(meetingId).duration +
                " | Location: " + this.meetingMetadata.get(meetingId).location
        );
    }

    // Method to change meeting location
    public void changeMeetingLocation(UUID meetingId, String location, String requester) {
        Meeting meeting = meetingMetadata.get(meetingId);
        if(meeting == null) {
            System.out.println("Meeting doesn't exist!");
            return;
        }

        if(!meeting.organizer.equals(requester)) {
            System.out.println("You don't have permission to edit this meeting!");
            return;
        }

        this.meetingMetadata.get(meetingId).location = location;

        // Output
        System.out.println(
                "Title: " + this.meetingMetadata.get(meetingId).title +
                        " | StartTime: " + this.meetingMetadata.get(meetingId).startTime.toString() +
                        " | Duration: " + this.meetingMetadata.get(meetingId).duration +
                        " | Location: " + this.meetingMetadata.get(meetingId).location
        );
    }

    // Method to forward meeting to a list of invitees
    public void forwardMeeting(UUID meetingId, List<String> invitees) {
        meetingMetadata.get(meetingId).invitees.addAll(invitees);

        LocalDate date = meetingMetadata.get(meetingId).startTime.toLocalDate();
        for(String invitee : invitees) {
            addInvitee(date, invitee, meetingId);
        }

        // Output
        System.out.println("Current list of invitees: ");
        System.out.println(meetingMetadata.get(meetingId).organizer);
        for(String invitee : meetingMetadata.get(meetingId).invitees) {
            System.out.println(invitee);
        }
    }

    // Method to show calendar for a given list of invitees
    public void showCalendar(LocalDate date, List<String> invitees) {
        for(String invitee: invitees) {
            getMeetingsForUsernameForDate(invitee, date);
        }
    }

    // Internal method to add a new meeting for an invitee in a bucket
    private void addInvitee(LocalDate date, String invitee, UUID meetingId) {
        meetingCalendar.putIfAbsent(date, new HashMap<>());
        meetingCalendar.get(date).putIfAbsent(invitee, new HashSet<>());
        meetingCalendar.get(date).get(invitee).add(meetingId);
    }
}
