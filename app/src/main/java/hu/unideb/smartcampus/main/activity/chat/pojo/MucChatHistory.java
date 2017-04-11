package hu.unideb.smartcampus.main.activity.chat.pojo;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class MucChatHistory {

    private String chatName;
    private Map<String, Bitmap> nameAvatarMap;
    private LinkedList<ChatConversationItem> chatConversationItems;

    public MucChatHistory() {
    }

    public MucChatHistory(String chatName, Map<String, Bitmap> nameAvatarMap, LinkedList<ChatConversationItem> chatConversationItems) {
        this.chatName = chatName;
        this.nameAvatarMap = nameAvatarMap;
        this.chatConversationItems = chatConversationItems;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Map<String, Bitmap> getNameAvatarMap() {
        return nameAvatarMap;
    }

    public void setNameAvatarMap(Map<String, Bitmap> nameAvatarMap) {
        this.nameAvatarMap = nameAvatarMap;
    }

    public LinkedList<ChatConversationItem> getChatConversationItems() {
        return chatConversationItems;
    }

    public void setChatConversationItems(LinkedList<ChatConversationItem> chatConversationItems) {
        this.chatConversationItems = chatConversationItems;
    }
}
