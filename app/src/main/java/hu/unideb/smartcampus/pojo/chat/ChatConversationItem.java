package hu.unideb.smartcampus.pojo.chat;

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
public class ChatConversationItem {

    Jid fromUserJid;
    String msg;

}
