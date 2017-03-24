package hu.unideb.smartcampus.main.activity.chat.pojo;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class ChatHistory {

    String chatName;
    List<ChatConversationItem> ChatConversationItem;

    public ChatHistory() {
    }

    public ChatHistory(String chatName, List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItem) {
        this.chatName = chatName;
        ChatConversationItem = chatConversationItem;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> getChatConversationItem() {
        return ChatConversationItem;
    }

    public void setChatConversationItem(List<hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem> chatConversationItem) {
        ChatConversationItem = chatConversationItem;
    }
}
