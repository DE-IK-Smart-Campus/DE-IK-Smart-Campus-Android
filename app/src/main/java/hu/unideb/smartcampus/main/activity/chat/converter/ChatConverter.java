package hu.unideb.smartcampus.main.activity.chat.converter;

import hu.unideb.smartcampus.main.activity.chat.pojo.ListUserChatsIqRequestPojo;
import hu.unideb.smartcampus.shared.iq.request.ListUserChatsIqRequest;

/**
 * Created by Headswitcher on 2017. 03. 27..
 * //TODO
 */

public class ChatConverter {

    public static ListUserChatsIqRequestPojo convertToListUserChatsIqRequestPojo(ListUserChatsIqRequest listUserChatsIqRequest) {

        ListUserChatsIqRequestPojo listUserChatsIqRequestPojo = new ListUserChatsIqRequestPojo();
        listUserChatsIqRequestPojo.setChatList(listUserChatsIqRequest.getChatList());
        listUserChatsIqRequestPojo.setMucChatList(listUserChatsIqRequest.getMucChatList());

        return listUserChatsIqRequestPojo;
    }
}
