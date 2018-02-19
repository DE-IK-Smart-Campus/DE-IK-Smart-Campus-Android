package hu.unideb.smartcampus.pojo.chat;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Headswitcher on 2018. 02. 17..
 */

public class ChatUser implements IUser {

    private String id;
    private String name;
    private String avatar;
    private boolean online;

    public ChatUser(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}