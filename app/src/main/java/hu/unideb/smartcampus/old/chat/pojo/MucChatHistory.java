package hu.unideb.smartcampus.old.chat.pojo;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

public class MucChatHistory {

    private String chatName;
    private Map<String, Bitmap> resourceAvatarMap;
    private LinkedList<MucChatConversationItem> chatConversationItems;

    public MucChatHistory() {
    }

    public MucChatHistory(String chatName, Map<String, Bitmap> resourceAvatarMap, LinkedList<MucChatConversationItem> chatConversationItems) {
        this.chatName = chatName;
        this.resourceAvatarMap = resourceAvatarMap;
        this.chatConversationItems = chatConversationItems;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Map<String, Bitmap> getResourceAvatarMap() {
        return resourceAvatarMap;
    }

    public void setResourceAvatarMap(Map<String, Bitmap> resourceAvatarMap) {
        this.resourceAvatarMap = resourceAvatarMap;
    }

    public LinkedList<MucChatConversationItem> getChatConversationItems() {
        return chatConversationItems;
    }

    public void setChatConversationItems(LinkedList<MucChatConversationItem> chatConversationItems) {
        this.chatConversationItems = chatConversationItems;
    }
}
