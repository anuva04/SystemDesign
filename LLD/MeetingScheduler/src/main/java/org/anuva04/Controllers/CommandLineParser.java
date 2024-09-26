package org.anuva04.Controllers;

import org.anuva04.Utils.Parsers.*;
import org.anuva04.Utils.Constants;

import java.util.Map;

public class CommandLineParser {
    // Method to determine command type and call appropriate CommandParser instance
    public static String parseAndExecuteCommand(String command, Map<String, String> args) {
        AbstractCommandParser parser;
        switch (command) {
            case Constants.CREATE_MEETING:
                parser = new CreateMeetingCommandParser();
                break;
            case Constants.DELETE_MEETING:
                parser = new DeleteMeetingCommandParser();
                break;
            case Constants.GET_MEETINGS:
                parser = new GetMeetingsForUsernameForDateCommandParser();
                break;
            case Constants.CHANGE_LOCATION:
                parser = new ChangeMeetingLocationCommandParser();
                break;
            case Constants.CHANGE_TIME:
                parser = new ChangeMeetingTimeCommandParser();
                break;
            case Constants.FORWARD_MEETING:
                parser = new ForwardMeetingCommandParser();
                break;
            case Constants.SHOW_CALENDAR:
                parser = new ShowCalendarCommandParser();
                break;
            default:
                throw new IllegalArgumentException("Command doesn't exist.");
        }
        String error = parser.validate(args);
        if(error == null) {
            parser.callMethod(args);
        }
        return error;
    }
}
