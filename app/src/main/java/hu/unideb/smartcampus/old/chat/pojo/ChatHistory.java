package hu.unideb.smartcampus.old.chat.pojo;

import java.util.LinkedList;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class ChatHistory {

    private String chatName;
    private LinkedList<ChatConversationItem> chatConversationItems;

    public ChatHistory() {
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public LinkedList<ChatConversationItem> getChatConversationItems() {
        return chatConversationItems;
    }

    public void setChatConversationItems(LinkedList<ChatConversationItem> chatConversationItems) {
        this.chatConversationItems = chatConversationItems;
    }
}
