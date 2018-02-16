package hu.unideb.smartcampus.pojo.chat;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {

    private String chatName;
    private LinkedList<ChatConversationItem> chatConversationItems;

}
