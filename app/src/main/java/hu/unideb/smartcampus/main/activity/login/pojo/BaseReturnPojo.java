package hu.unideb.smartcampus.main.activity.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Headswitcher on 2017. 12. 15..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseReturnPojo<T> {

    private T returnPojo;
    private Integer responseCode;

    public static <T extends BaseReturnPojo> BaseReturnPojo returnTask(T param){

        switch (param.getResponseCode()){
            case HTTP_OK:
                return new BaseReturnPojo<>(param,param.getResponseCode());
            case HTTP_GATEWAY_TIMEOUT:
                return new BaseReturnPojo<T>(null,param.getResponseCode());
        }
        return new BaseReturnPojo();
    }
}
