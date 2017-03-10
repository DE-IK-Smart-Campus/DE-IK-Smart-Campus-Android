package hu.unideb.smartcampus.xmpp.pojos;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class MessageTypeInstructorIdUserId {

    private String messageType;
    private String userId;
    private String instructorId;

    public MessageTypeInstructorIdUserId(String messageType, String userId, String instructorId) {
        this.messageType = messageType;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
