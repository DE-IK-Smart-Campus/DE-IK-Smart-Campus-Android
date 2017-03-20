package hu.unideb.smartcampus.main.activity.officehours.json;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class MessageTypeConsultingHourIdReasonDuration {
    private String messageType;
    private String consultingHourId;
    private String reason;
    private String duration;
    private String userId;

    public MessageTypeConsultingHourIdReasonDuration() {
    }

    public MessageTypeConsultingHourIdReasonDuration(String messageType, String consultingHourId, String reason, String duration, String userId) {
        this.messageType = messageType;
        this.consultingHourId = consultingHourId;
        this.reason = reason;
        this.duration = duration;
        this.userId = userId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getConsultingHourId() {
        return consultingHourId;
    }

    public void setConsultingHourId(String consultingHourId) {
        this.consultingHourId = consultingHourId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
