package hu.unideb.smartcampus.main.activity.login.pojo;

/**
 * Created by Headswitcher on 2017. 03. 16..
 */


//TODO roles
public class ActualUserInfo {

    String userName;
    String xmppPassword;

    public ActualUserInfo() {
    }

    public ActualUserInfo(String userName, String xmppPassword) {
        this.userName = userName;
        this.xmppPassword = xmppPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getXmppPassword() {
        return xmppPassword;
    }

    public void setXmppPassword(String xmppPassword) {
        this.xmppPassword = xmppPassword;
    }
}
