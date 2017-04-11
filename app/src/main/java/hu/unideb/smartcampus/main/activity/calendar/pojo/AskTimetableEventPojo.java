package hu.unideb.smartcampus.main.activity.calendar.pojo;

import java.util.List;

import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;
import hu.unideb.smartcampus.sqlite.models.TimetableEvent;

public class AskTimetableEventPojo extends BasePojo {

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