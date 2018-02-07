package hu.unideb.smartcampus.old.chat.pojo;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.Jid;


/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatItem {


    public enum Type {
        MUC,
        SINGLE,
    }

    VCard vCard;
    Jid from;
    String chatName;
    ChatItem.Type Type;
    String lastMsg;

    public ChatItem() {
    }

    public ChatItem(VCard vCard, Jid from, String chatName, ChatItem.Type type, String lastMsg) {
        this.vCard = vCard;
        this.from = from;
        this.chatName = chatName;
        Type = type;
        this.lastMsg = lastMsg;
    }

    public VCard getvCard() {
        return vCard;
    }

    public void setvCard(VCard vCard) {
        this.vCard = vCard;
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

    public ChatItem.Type getType() {
        return Type;
    }

    public void setType(ChatItem.Type type) {
        Type = type;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
