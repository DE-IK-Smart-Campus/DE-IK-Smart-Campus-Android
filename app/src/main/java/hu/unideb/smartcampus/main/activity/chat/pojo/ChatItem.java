package hu.unideb.smartcampus.main.activity.chat.pojo;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatItem {

    String chatName;
    String lastMsg;

    public ChatItem(String chatName, String lastMsg) {
        this.chatName = chatName;
        this.lastMsg = lastMsg;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
