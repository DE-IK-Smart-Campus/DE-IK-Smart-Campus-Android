package hu.unideb.smartcampus.task.chat;


import java.io.Serializable;
import java.util.List;

import hu.unideb.smartcampus.pojo.chat.ChatMessage;

/**
 * Created by Headswitcher on 2018. 02. 20..
 */

public class SelectedChatForwardedMessages implements Serializable {

    List<ChatMessage> forwardedMessages;

    public SelectedChatForwardedMessages(List<ChatMessage> forwardedMessages) {
        this.forwardedMessages = forwardedMessages;
    }

    public List<ChatMessage> getForwardedMessages() {
        return forwardedMessages;
    }

    public void setForwardedMessages(List<ChatMessage> forwardedMessages) {
        this.forwardedMessages = forwardedMessages;
    }
}
