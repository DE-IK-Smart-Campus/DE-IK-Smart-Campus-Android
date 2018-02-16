package hu.unideb.smartcampus.pojo.chat;

import org.jxmpp.jid.Jid;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatConversationItem {

    Jid fromUserJid;
    String msg;

    public ChatConversationItem() {
    }

    public ChatConversationItem(Jid fromUserJid, String msg) {
        this.fromUserJid = fromUserJid;
        this.msg = msg;
    }

    public Jid getFromUserJid() {
        return fromUserJid;
    }

    public void setFromUserJid(Jid fromUserJid) {
        this.fromUserJid = fromUserJid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
