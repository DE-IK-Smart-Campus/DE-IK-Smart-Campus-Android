package hu.unideb.smartcampus.main.activity.chat.pojo;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class ChatHistory {

    String chatName;
    List<ChatConversationItem> chatConversationItems;

    public ChatHistory() {
    }

    public ChatHistory(String chatName, List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItems) {
        this.chatName = chatName;
        this.chatConversationItems = chatConversationItems;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> getChatConversationItems() {
        return chatConversationItems;
    }

    public void setChatConversationItems(List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItems) {
        this.chatConversationItems = chatConversationItems;
    }
}
