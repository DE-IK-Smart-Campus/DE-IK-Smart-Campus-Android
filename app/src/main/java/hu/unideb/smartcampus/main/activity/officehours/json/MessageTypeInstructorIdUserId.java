package hu.unideb.smartcampus.main.activity.officehours.json;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class MessageTypeInstructorIdUserId {

    private String messageType;
    private String instructorId;

    public MessageTypeInstructorIdUserId(String messageType, String instructorId) {
        this.messageType = messageType;
        this.instructorId = instructorId;
    }

    public MessageTypeInstructorIdUserId() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
