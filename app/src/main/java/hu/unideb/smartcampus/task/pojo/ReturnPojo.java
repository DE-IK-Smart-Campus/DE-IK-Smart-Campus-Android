package hu.unideb.smartcampus.task.pojo;

/**
 * Created by Headswitcher on 2018. 02. 08..
 */

public class ReturnPojo {
    private Integer statusCode;

    public ReturnPojo(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}


