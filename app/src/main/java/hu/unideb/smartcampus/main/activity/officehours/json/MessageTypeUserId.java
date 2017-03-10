package hu.unideb.smartcampus.main.activity.officehours.json;


public class MessageTypeUserId {

    private String messageType;
    private String userId;

    public MessageTypeUserId() {
    }

    public MessageTypeUserId(String messageType, String userId) {
        this.messageType = messageType;
        this.userId = userId;
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
}
