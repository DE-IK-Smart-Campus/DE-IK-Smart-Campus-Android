package hu.unideb.smartcampus.main.activity.officehours.pojo;

import java.util.List;

/**
 * This class for the JSON parse  ( with Jackson),
 * when messageType at Office hours is "AskInstructorConsultingHoursProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */

public class AskInstructorConsultingHoursProcessMessagePojo {
    private String messageType;
    private List<OfficeHour> consultingHours;

    public AskInstructorConsultingHoursProcessMessagePojo() {
    }

    public AskInstructorConsultingHoursProcessMessagePojo(String messageType, List<OfficeHour> consultingHours) {
        this.messageType = messageType;
        this.consultingHours = consultingHours;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<OfficeHour> getConsultingHours() {
        return consultingHours;
    }

    public void setConsultingHours(List<OfficeHour> consultingHours) {
        this.consultingHours = consultingHours;
    }
}
