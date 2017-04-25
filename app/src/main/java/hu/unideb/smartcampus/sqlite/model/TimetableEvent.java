package hu.unideb.smartcampus.sqlite.model;

public class TimetableEvent {
    private int id;
    private Long timetableEventDate;
    private String timetableEventName;
    private String timetableEventDescription;
    private String timetableEventPlace;
    private Long timetableEventStartTime;
    private Long timetableEventEndTime;

    public TimetableEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTimetableEventDate() {
        return timetableEventDate;
    }

    public void setTimetableEventDate(Long timetableEventDate) {
        this.timetableEventDate = timetableEventDate;
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

    public Long getTimetableEventStartTime() {
        return timetableEventStartTime;
    }

    public void setTimetableEventStartTime(Long timetableEventStartTime) {
        this.timetableEventStartTime = timetableEventStartTime;
    }

    public Long getTimetableEventEndTime() {
        return timetableEventEndTime;
    }

    public void setTimetableEventEndTime(Long timetableEventEndTime) {
        this.timetableEventEndTime = timetableEventEndTime;
    }
}


