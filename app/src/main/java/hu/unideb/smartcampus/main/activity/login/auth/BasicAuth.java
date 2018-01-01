package hu.unideb.smartcampus.main.activity.login.auth;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.Auth;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;
import hu.unideb.smartcampus.main.activity.login.pojo.AuthReturnPojo;
import hu.unideb.smartcampus.main.activity.login.pojo.BaseReturnPojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;

import static hu.unideb.smartcampus.xmpp.Connection.HTTP_BASIC_AUTH_PATH;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * BasicAuth gets the Username, Password for ejabberd login
 * from restless call
 *
 * @see hu.unideb.smartcampus.activity.LoginActivity
 * @see ActualUserInfo
 * <p>
 * <p>
 * UnsafeOkHttpClient TODO
 * <p>
 * Created by Headswitcher on 2017. 03. 16..
 */

public class BasicAuth extends AsyncTask<ActualUserInfo, Long, ActualUserInfo> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private Integer responseCode;

    @Override
    protected ActualUserInfo doInBackground(ActualUserInfo... params) {
        OkHttpClient httpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        ActualUserInfo actualUserInfo = params[0];
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
            try {
                if (response != null) {
                    response.body().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (responseCode == HTTP_OK) {
            return actualUserInfo;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ActualUserInfo actualUserInfo) {
        super.onPostExecute(actualUserInfo);

        if (actualUserInfo != null) {

        } else {
            //HTTP kódkezelés
        }
    }
}

