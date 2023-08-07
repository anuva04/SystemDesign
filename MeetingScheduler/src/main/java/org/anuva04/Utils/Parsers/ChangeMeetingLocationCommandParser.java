package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChangeMeetingLocationCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.MEETING_ID, Constants.LOCATION, Constants.REQUESTER);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        UUID meetingId = UUID.fromString(args.get(Constants.MEETING_ID));
        String location = args.get(Constants.LOCATION);
        String requester = args.get(Constants.REQUESTER);

        meetingDb.changeMeetingLocation(meetingId, location, requester);
    }
}
