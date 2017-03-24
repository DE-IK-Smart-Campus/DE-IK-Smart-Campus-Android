package hu.unideb.smartcampus.main.activity.chat.pojo;

import org.jxmpp.jid.Jid;


/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatItem {

    Jid from;
    String chatName;
    String lastMsg;


    public ChatItem() {
    }

    public ChatItem(Jid from, String chatName, String lastMsg) {

        this.from = from;
        this.chatName = chatName;
        this.lastMsg = lastMsg;
    }

    public Jid getFrom() {
        return from;
    }

    public void setFrom(Jid from) {
        this.from = from;
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
