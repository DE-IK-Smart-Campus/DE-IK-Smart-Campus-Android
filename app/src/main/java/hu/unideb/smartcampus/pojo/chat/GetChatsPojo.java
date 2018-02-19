package hu.unideb.smartcampus.pojo.chat;

import org.jxmpp.jid.Jid;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Headswitcher on 2018. 02. 16..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChatsPojo implements Serializable {

    List<Dialog> chatItemList;

}
