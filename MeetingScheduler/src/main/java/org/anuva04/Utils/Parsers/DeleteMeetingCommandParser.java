package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeleteMeetingCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.REQUESTER, Constants.MEETING_ID);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        String requester = args.get(Constants.REQUESTER);
        UUID meetingId = UUID.fromString(args.get(Constants.MEETING_ID));

        meetingDb.deleteMeeting(meetingId, requester);
    }
}
