package org.anuva04.Utils.Parsers;

import org.anuva04.Models.MeetingDb;
import org.anuva04.Utils.Constants;
import org.anuva04.Utils.Helper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ShowCalendarCommandParser extends AbstractCommandParser {
    private static List<String> requiredArgs = List.of(Constants.DATE, Constants.INVITEES);

    @Override
    protected List<String> getRequiredArgs() {
        return requiredArgs;
    }

    @Override
    public void callMethod(Map<String, String> args) {
        MeetingDb meetingDb = MeetingDb.getInstance();

        LocalDate date = Helper.getLocalDate(args.get(Constants.DATE));
        List<String> invitees = List.of(args.get(Constants.INVITEES).split(Constants.INVITEES_DELIMITER));

        meetingDb.showCalendar(date, invitees);
    }
}
