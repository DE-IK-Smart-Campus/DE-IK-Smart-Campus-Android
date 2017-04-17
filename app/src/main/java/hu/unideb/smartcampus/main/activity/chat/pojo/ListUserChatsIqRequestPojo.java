package hu.unideb.smartcampus.main.activity.chat.pojo;

import java.util.List;

import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;

/**
 * Created by Headswitcher on 2017. 04. 11..
 */

public class ListUserChatsIqRequestPojo extends BasePojo{

    private String student;
    private List<String> chatList;
    private List<String> mucChatList;

    public ListUserChatsIqRequestPojo() {
    }

    public ListUserChatsIqRequestPojo(String student, List<String> chatList, List<String> mucChatList) {
        this.student = student;
        this.chatList = chatList;
        this.mucChatList = mucChatList;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public List<String> getChatList() {
        return chatList;
    }

    public void setChatList(List<String> chatList) {
        this.chatList = chatList;
    }

    public List<String> getMucChatList() {
        return mucChatList;
    }

    public void setMucChatList(List<String> mucChatList) {
        this.mucChatList = mucChatList;
    }
}
