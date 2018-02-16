package hu.unideb.smartcampus.pojo.chat;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.Jid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by Headswitcher on 2017. 03. 21..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
