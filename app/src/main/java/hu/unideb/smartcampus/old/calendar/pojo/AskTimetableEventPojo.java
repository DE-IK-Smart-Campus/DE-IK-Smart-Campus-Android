package hu.unideb.smartcampus.old.calendar.pojo;


import java.util.List;

import hu.unideb.smartcampus.old.sqlite.model.TimetableEvent;

public class AskTimetableEventPojo {

    private List<TimetableEvent> timetableEvents;

    public AskTimetableEventPojo() {
    }

    public AskTimetableEventPojo(List<TimetableEvent> timetableEvents) {
        this.timetableEvents = timetableEvents;
    }

    public List<TimetableEvent> getTimetableEvents() {
        return timetableEvents;
    }

    public void setTimetableEvents(List<TimetableEvent> timetableEvents) {
        this.timetableEvents = timetableEvents;
    }
}