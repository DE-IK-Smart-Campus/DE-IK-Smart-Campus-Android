package hu.unideb.smartcampus.main.activity.login.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Headswitcher on 2017. 03. 16..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualUserInfo {

    String username;
    String xmppPassword;
    List<Role> roles;

}
