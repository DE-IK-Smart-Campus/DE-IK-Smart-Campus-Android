package hu.unideb.smartcampus.pojo.chat;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class MucChatConversationItem {

    String resourceName;
    String msg;

    public MucChatConversationItem() {
    }

    public MucChatConversationItem(String resourceName, String msg) {
        this.resourceName = resourceName;
        this.msg = msg;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
