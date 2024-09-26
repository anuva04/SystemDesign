package org.anuva04.Models;

import java.time.LocalDateTime;
import java.util.*;

public class Meeting {
    public String title;
    public String location;
    public String organizer;
    public LocalDateTime startTime;
    public int duration;
    public UUID id;
    public Set<String> invitees;

    public Meeting(String title, String location, String organizer, LocalDateTime startTime, int duration, UUID id) {
        this.title = title;
        this.location = location;
        this.organizer = organizer;
        this.startTime = startTime;
        this.duration = duration;
        this.id = id;
        this.invitees = new HashSet<>();
    }
}
