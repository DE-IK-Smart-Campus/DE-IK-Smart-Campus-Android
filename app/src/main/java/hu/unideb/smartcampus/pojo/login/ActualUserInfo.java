package hu.unideb.smartcampus.pojo.login;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualUserInfo {
    String username;
    String xmppPassword;
    List<Role> roles;
}
