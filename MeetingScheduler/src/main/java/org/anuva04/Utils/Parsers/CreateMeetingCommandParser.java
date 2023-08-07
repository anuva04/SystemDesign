package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;
import org.anuva04.Utils.Helper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CreateMeetingCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.ORGANIZER, Constants.START_TIME, Constants.TITLE, Constants.DURATION, Constants.LOCATION, Constants.INVITEES);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        String organizer = args.get(Constants.ORGANIZER);
        String title = args.get(Constants.TITLE);
        LocalDateTime startTime = Helper.getLocalDateTime(args.get(Constants.START_TIME));
        int duration = Integer.parseInt(args.get(Constants.DURATION));
        String location = args.get(Constants.LOCATION);
        List<String> invitees = List.of(args.get(Constants.INVITEES).split(Constants.INVITEES_DELIMITER));

        meetingDb.createMeeting(organizer, startTime, duration, location, title, invitees);
    }
}
