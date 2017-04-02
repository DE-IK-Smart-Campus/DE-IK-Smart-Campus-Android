package hu.unideb.smartcampus.main.activity.chat.pojo;

import java.util.LinkedList;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class ChatHistory {

    String chatName;
    LinkedList<ChatConversationItem> chatConversationItems;

    public ChatHistory() {
    }

    public ChatHistory(String chatName, LinkedList<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItems) {
        this.chatName = chatName;
        this.chatConversationItems = chatConversationItems;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public LinkedList<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> getChatConversationItems() {
        return chatConversationItems;
    }

    public void setChatConversationItems(LinkedList<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItems) {
        this.chatConversationItems = chatConversationItems;
    }
}
