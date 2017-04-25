package hu.unideb.smartcampus.sqlite.model;

public class CustomEvent {

    private String uuid;
    private String eventName;
    private String eventDescription;
    private String eventPlace;
    private Long eventStartDate;
    private Long eventStartTime;
    private Long eventEndDate;
    private Long eventEndTime;
    private String evenetRepeat;
    private String eventReminder;

    public CustomEvent() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public Long getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Long eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Long getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Long eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Long getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Long eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Long getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEvenetRepeat() {
        return evenetRepeat;
    }

    public void setEvenetRepeat(String evenetRepeat) {
        this.evenetRepeat = evenetRepeat;
    }

    public String getEventReminder() {
        return eventReminder;
    }

    public void setEventReminder(String eventReminder) {
        this.eventReminder = eventReminder;
    }

}
