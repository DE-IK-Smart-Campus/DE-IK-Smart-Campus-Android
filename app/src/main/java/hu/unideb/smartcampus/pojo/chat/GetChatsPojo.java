package hu.unideb.smartcampus.pojo.chat;

import org.jxmpp.jid.Jid;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Headswitcher on 2018. 02. 16..
 */

public class GetChatsPojo implements Serializable {


    List<ChatItem> chatItemList;

    public GetChatsPojo() {
    }

    public GetChatsPojo(List<ChatItem> chatItemList) {
        this.chatItemList = chatItemList;
    }

    public List<ChatItem> getChatItemList() {
        return chatItemList;
    }

    public void setChatItemList(List<ChatItem> chatItemList) {
        this.chatItemList = chatItemList;
    }
}
