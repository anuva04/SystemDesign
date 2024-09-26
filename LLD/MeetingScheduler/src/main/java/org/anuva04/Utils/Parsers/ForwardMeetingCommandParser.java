package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ForwardMeetingCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.MEETING_ID, Constants.INVITEES);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        UUID meetingId = UUID.fromString(args.get(Constants.MEETING_ID));
        List<String> invitees = List.of(args.get(Constants.INVITEES).split(Constants.INVITEES_DELIMITER));

        meetingDb.forwardMeeting(meetingId, invitees);
    }
}
