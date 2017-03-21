package hu.unideb.smartcampus.main.activity.calendar.pojo;

public class TimetableEvent {

    private String timetableEventName;
    private String timetableEventDescription;
    private String timetableEventPlace;
    private Long timetableStartTime;
    private Long timetableEndTime;

    public TimetableEvent(String timetableEventName, String timetableEventDescription, String timetableEventPlace, Long timetableStartTime, Long timetableEndTime) {
        this.timetableEventName = timetableEventName;
        this.timetableEventDescription = timetableEventDescription;
        this.timetableEventPlace = timetableEventPlace;
        this.timetableStartTime = timetableStartTime;
        this.timetableEndTime = timetableEndTime;
    }

    public String getTimetableEventName() {
        return timetableEventName;
    }

    public void setTimetableEventName(String timetableEventName) {
        this.timetableEventName = timetableEventName;
    }

    public String getTimetableEventDescription() {
        return timetableEventDescription;
    }

    public void setTimetableEventDescription(String timetableEventDescription) {
        this.timetableEventDescription = timetableEventDescription;
    }

    public String getTimetableEventPlace() {
        return timetableEventPlace;
    }

    public void setTimetableEventPlace(String timetableEventPlace) {
        this.timetableEventPlace = timetableEventPlace;
    }

    public Long getTimetableStartTime() {
        return timetableStartTime;
    }

    public void setTimetableStartTime(Long timetableStartTime) {
        this.timetableStartTime = timetableStartTime;
    }

    public Long getTimetableEndTime() {
        return timetableEndTime;
    }

    public void setTimetableEndTime(Long timetableEndTime) {
        this.timetableEndTime = timetableEndTime;
    }
}
