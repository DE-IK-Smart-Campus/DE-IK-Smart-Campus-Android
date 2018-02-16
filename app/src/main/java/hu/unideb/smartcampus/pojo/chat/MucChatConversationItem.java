package hu.unideb.smartcampus.pojo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MucChatConversationItem {

    String resourceName;
    String msg;

}
