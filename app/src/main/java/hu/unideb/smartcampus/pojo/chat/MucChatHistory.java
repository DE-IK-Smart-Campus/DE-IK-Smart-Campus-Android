package hu.unideb.smartcampus.pojo.chat;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Headswitcher on 2017. 03. 23..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MucChatHistory {

    private String chatName;
    private Map<String, Bitmap> resourceAvatarMap;
    private LinkedList<MucChatConversationItem> chatConversationItems;

}
