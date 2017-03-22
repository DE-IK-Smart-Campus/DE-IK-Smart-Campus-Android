package hu.unideb.smartcampus.main.activity.login.pojo;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 16..
 */


//TODO roles
public class ActualUserInfo {

    String username;
    String xmppPassword;
    List<Role> roles;

    public ActualUserInfo() {
    }

    public ActualUserInfo(String username, String xmppPassword, List<Role> roles) {
        this.username = username;
        this.xmppPassword = xmppPassword;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXmppPassword() {
        return xmppPassword;
    }

    public void setXmppPassword(String xmppPassword) {
        this.xmppPassword = xmppPassword;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
