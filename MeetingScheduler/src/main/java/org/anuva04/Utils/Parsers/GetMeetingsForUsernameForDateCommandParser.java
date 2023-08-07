package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;
import org.anuva04.Utils.Helper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class GetMeetingsForUsernameForDateCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.USERNAME, Constants.DATE);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        String username = args.get(Constants.USERNAME);
        LocalDate date = Helper.getLocalDate(args.get(Constants.DATE));

        meetingDb.getMeetingsForUsernameForDate(username, date);
    }
}
