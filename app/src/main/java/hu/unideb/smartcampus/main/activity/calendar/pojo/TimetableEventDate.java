package hu.unideb.smartcampus.main.activity.calendar.pojo;

import java.util.List;

public class TimetableEventDate {

    private Long timetableEventDate;
    private List<TimetableEvent> timetableEventList;

    public TimetableEventDate(Long timetableEventDate, List<TimetableEvent> timetableEventList) {
        this.timetableEventDate = timetableEventDate;
        this.timetableEventList = timetableEventList;
    }

    public Long getTimetableEventDate() {
        return timetableEventDate;
    }

    public void setTimetableEventDate(Long timetableEventDate) {
        this.timetableEventDate = timetableEventDate;
    }

    public List<TimetableEvent> getTimetableEventList() {
        return timetableEventList;
    }

    public void setTimetableEventList(List<TimetableEvent> timetableEventList) {
        this.timetableEventList = timetableEventList;
    }

    @Override
    public String toString() {
        return "TimetableEventDate{" +
                "timetableEventDate=" + timetableEventDate +
                ", timetableEventList=" + timetableEventList +
                '}';
    }
}
