package hu.unideb.smartcampus.task.login;

import hu.unideb.smartcampus.pojo.login.ActualUserInfo;

/**
 * Created by Headswitcher on 2018. 02. 08..
 */

public class BasicAuthReturnPojo extends ReturnPojo {

    private ActualUserInfo actualUserInfo;

    public BasicAuthReturnPojo(Integer statusCode, ActualUserInfo actualUserInfo) {
        super(statusCode);
        this.actualUserInfo = actualUserInfo;
    }

    public ActualUserInfo getActualUserInfo() {
        return actualUserInfo;
    }

    public void setActualUserInfo(ActualUserInfo actualUserInfo) {
        this.actualUserInfo = actualUserInfo;
    }
}
