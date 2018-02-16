package hu.unideb.smartcampus.pojo.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by Headswitcher on 2017. 04. 11..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUserChatsIqRequestPojo {

    private String student;
    private List<String> chatList;
    private List<String> mucChatList;

}
