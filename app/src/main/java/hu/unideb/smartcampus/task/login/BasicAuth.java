package hu.unideb.smartcampus.task.login;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

import hu.unideb.smartcampus.pojo.login.ActualUserInfo;
import hu.unideb.smartcampus.unsafe.Unsafe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static hu.unideb.smartcampus.container.Container.AUTHORIZATION_HEADER;
import static hu.unideb.smartcampus.xmpp.Connection.HTTP_BASIC_AUTH_PATH;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

class BasicAuth {

    static BasicAuthReturnPojo basicAuth(ActualUserInfo paramActualUserInfo) {

        Integer responseCode;

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
            if (response.body() != null) {
                actualUserInfo = objectMapper.readValue(response.body().string(), ActualUserInfo.class);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return new BasicAuthReturnPojo(HTTP_INTERNAL_ERROR, null);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new BasicAuthReturnPojo(responseCode, actualUserInfo);
    }

}

