package hu.unideb.smartcampus.main.activity.login.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;
import hu.unideb.smartcampus.unsafe.Unsafe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static hu.unideb.smartcampus.xmpp.Connection.HTTP_BASIC_AUTH_PATH;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Headswitcher on 2018. 02. 05..
 */

public class BasicAuth {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    static ActualUserInfo basicAuth(ActualUserInfo paramActualUserInfo) {

        Integer responseCode = null;

        OkHttpClient httpClient = Unsafe.getUnsafeOkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        ActualUserInfo actualUserInfo = paramActualUserInfo;
        String toBase64 = actualUserInfo.getUsername() + ":" + actualUserInfo.getXmppPassword();

        byte[] encodedUsernameAndPassword = Base64.encodeBase64(toBase64.getBytes());
        Request request = new Request.Builder()
                .url(HTTP_BASIC_AUTH_PATH)
                .header(AUTHORIZATION_HEADER, "Basic " + new String(encodedUsernameAndPassword))
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            responseCode = response.code();
            actualUserInfo = objectMapper.readValue(response.body().string(), ActualUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (response != null) {
                response.body().close();
            }
        }

        if (responseCode != null && responseCode == HTTP_OK) {
            return actualUserInfo;
        } else {
            return null;
        }
    }

}

