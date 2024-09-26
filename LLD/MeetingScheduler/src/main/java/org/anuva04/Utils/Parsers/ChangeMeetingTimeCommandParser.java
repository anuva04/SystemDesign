package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;
import org.anuva04.Utils.Helper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChangeMeetingTimeCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.MEETING_ID, Constants.START_TIME, Constants.DURATION, Constants.REQUESTER);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        UUID meetingId = UUID.fromString(args.get(Constants.MEETING_ID));
        int duration = Integer.parseInt(args.get(Constants.DURATION));
        LocalDateTime startTime = Helper.getLocalDateTime(args.get(Constants.START_TIME));
        String requester = args.get(Constants.REQUESTER);

        meetingDb.changeMeetingTime(meetingId, startTime, duration, requester);
    }
}
