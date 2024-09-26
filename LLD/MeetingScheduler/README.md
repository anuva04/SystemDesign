# Meeting Scheduler App

The Meeting Scheduler app is a command-line-based application for managing and scheduling meetings. It provides functionality to create, update, and delete meetings in a meeting database.

# Packages

The app is organized into the following packages:

1. `org.anuva04.Controllers`: Contains the `MeetingScheduler` class, which serves as the entry point to the Meeting Scheduler app. It handles user interactions and executes commands on the meeting database.
2. `org.anuva04.Models`: Contains the `MeetingDb` class, which represents the meeting database. It manages the storage and retrieval of meeting data.
3. `org.anuva04.Utils.Parsers`: Contains command parsers that handle the parsing and execution of various commands for the Meeting Scheduler app.
4. `org.anuva04.Utils`: Contains utility classes and constants used throughout the application.

## Classes

### MeetingScheduler

The `MeetingScheduler` class in the `org.anuva04.Controllers` package is the entry point to the Meeting Scheduler app. It is responsible for handling user interactions and executing commands on the meeting database.

#### Constructor

- `public MeetingScheduler()`: Initializes the `MeetingDb` instance.

#### Methods

- `public void init()`: Simulates calling various methods on the user interface (UI). It prompts the user to enter commands and arguments for performing operations on the meeting database.

### MeetingDb

The `MeetingDb` class in the `org.anuva04.Models` package represents the meeting database. It provides methods for managing meetings.

#### Constructor

- `private MeetingDb()`: Creates a new instance of the Meeting Database. This class follows the singleton pattern to ensure that only one instance of the database exists.

#### Methods

- `public static MeetingDb getInstance()`: Returns the instance of the Meeting Database. If an instance already exists, it returns the existing instance; otherwise, it creates a new instance.

- `public void createMeeting(String organizer, LocalDateTime startTime, int duration, String location, String title, List<String> invitees)`: Creates a new meeting with the given details and adds it to the database. The method also updates the meeting calendar and metadata with the new meeting information.

- `public void deleteMeeting(UUID meetingId, String requester)`: Deletes an existing meeting with the specified `meetingId`. The method verifies the requester's permissions and ensures that the requester is the organizer of the meeting.

- `public void getMeetingsForUsernameForDate(String username, LocalDate date)`: Retrieves all meetings of a given user on a specific date. The method outputs the details of the meetings to the console.

- `public void changeMeetingTime(UUID meetingId, LocalDateTime startTime, int duration, String requester)`: Changes the start time and duration of a meeting with the specified `meetingId`. The method ensures that the requester is the organizer of the meeting and updates the meeting information in the metadata and calendar accordingly.

- `public void changeMeetingLocation(UUID meetingId, String location, String requester)`: Changes the location of a meeting with the specified `meetingId`. The method ensures that the requester is the organizer of the meeting and updates the meeting information in the metadata accordingly.

- `public void forwardMeeting(UUID meetingId, List<String> invitees)`: Forwards a meeting to a list of invitees, adding them to the meeting's invitee list and updating the meeting calendar.

- `public void showCalendar(LocalDate date, List<String> invitees)`: Displays the calendar for a given date and list of invitees. The method shows all meetings scheduled for each invitee on that date.

### Meeting

The `Meeting` class in the `org.anuva04.Models` package represents a single meeting in the Meeting Scheduler app. Each meeting has a title, location, organizer, start time, duration, and a unique identifier (UUID). The class also maintains a set of invitees for the meeting.

#### Constructor

- `public Meeting(String title, String location, String organizer, LocalDateTime startTime, int duration, UUID id)`: Creates a new instance of the `Meeting` class with the specified details. The constructor initializes the meeting with the provided title, location, organizer, start time, duration, and unique identifier. It also initializes the invitees as an empty set.

### AbstractCommandParser

The `AbstractCommandParser` class in the `org.anuva04.Utils.Parsers` package is an abstract class that serves as the base for all command parsers in the Meeting Scheduler app. Command parsers handle the parsing and execution of various commands.

#### Methods

- `public String validate(Map<String, String> args)`: Validates the provided arguments for a command by checking if all required arguments are present. If any required argument is missing, the method returns the name of the missing argument; otherwise, it returns `null`.

- `protected abstract List<String> getRequiredArgs()`: This abstract method must be implemented by the subclasses to provide a list of required arguments for the specific command.

- `public abstract void callMethod(Map<String, String> args)`: This abstract method must be implemented by the subclasses to execute the specific command with the provided arguments.

ChangeMeetingTimeCommandParser, CreateMeetingCommandParser, ChangeMeetingLocationCommandParser, DeleteMeetingCommandParser, ForwardMeetingCommandParser, GetMeetingsForUsernameForDateCommandParser, ShowCalendarCommandParser classes extend AbstractCommandParser.

## Prerequisites
Before running the Meeting Scheduler app, make sure you have the following installed on your system:

- Java 11 or higher
- Gradle build system

## Sample Input File

Below is a sample input file containing various commands to interact with the Meeting Scheduler app. Each command is followed by a brief description of its purpose.

```plaintext
// Create new meeting by user1
create-meeting --organizer user1 --startTime 2023-08-08T12:00 --duration 30 --title SampleMeeting --location MSTeams --invitees in1,in2,in3

// Create another meeting for user1
create-meeting --organizer user1 --startTime 2023-08-08T14:00 --duration 30 --title AnotherSampleMeeting --location MSTeams --invitees user2,in2,in4

// Create new meeting for user2
create-meeting --organizer user2 --startTime 2023-08-09T12:00 --duration 60 --title DifferentSampleMeeting --location MSTeams --invitees in1,in2,user1

// Get meetings for user1
get-meetings --username user1 --date 2023-08-08

// Get meetings for user2
get-meetings --username user2 --date 2023-08-08

// Delete meeting by user1
delete-meeting --meetingId <meeting_id> --requester user1

// Change meeting time
change-time --meetingId <meeting_id> --startTime 2023-08-15T12:00 --duration 25 --requester user2

// Change meeting location
change-location --meetingId <meeting_id> --location GoogleMeet --requester user2

// Show calendar
show-calendar --date 2023-08-08 --invitees user1,user2

// Forward meeting
forward-meeting --meetingId <meeting_id> --invitees newInvitee
```

### Description

- `create-meeting`: Creates a new meeting with the specified details, such as organizer, start time, duration, title, location, and invitees.

- `get-meetings`: Retrieves all meetings of a given user on a specific date.

- `delete-meeting`: Deletes an existing meeting with the specified meetingId. The requester must be the organizer of the meeting.

- `change-time`: Changes the start time and duration of a meeting with the specified meetingId. The requester must be the organizer of the meeting.

- `change-location`: Changes the location of a meeting with the specified meetingId. The requester must be the organizer of the meeting.

- `show-calendar`: Displays the calendar for a given date and list of invitees. It shows all meetings scheduled for each invitee on that date.

- `forward-meeting`: Forwards a meeting to a new list of invitees.

Please note that `<meeting_id>` in the commands should be replaced with the actual UUID of the respective meetings. Additionally, dates and times in the commands are provided in the format `YYYY-MM-DDTHH:mm`. For example, `2023-08-08T12:00` represents August 8, 2023, at 12:00 PM. The `invitees` field in the commands is a comma-separated list of usernames for attendees.
