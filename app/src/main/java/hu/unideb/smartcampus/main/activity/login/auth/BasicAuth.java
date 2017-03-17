package hu.unideb.smartcampus.main.activity.login.auth;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;

import static android.content.ContentValues.TAG;

/**
 * Created by Headswitcher on 2017. 03. 16..
 */

public class BasicAuth extends AsyncTask<ActualUserInfo, Long, ActualUserInfo> {

    @Override
    protected ActualUserInfo doInBackground(ActualUserInfo... params) {
        ObjectMapper objectMapper = new ObjectMapper();
        ActualUserInfo actualUserInfo = params[0];
        OkHttpClient httpClient = new OkHttpClient();

        String toBase64 = actualUserInfo.getUserName() + ":" + actualUserInfo.getXmppPassword();
        byte[] encodedBytes = Base64.encodeBase64(toBase64.getBytes());
        Request request = new Request.Builder()
                .url("https://wt2.inf.unideb.hu/basicauth")
                .header("Authorization", new String(encodedBytes))
                .build();

        Response response = null;
        try {
            //          response = httpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
         String fromJson = "{\"userName\":\"testuser\",\"xmppPassword\":\"admin\"}";
      //  String fromJson = "{\"userName\":\"\",\"xmppPassword\":\"\"}";
        try {
            actualUserInfo = objectMapper.readValue(fromJson, ActualUserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualUserInfo;
    }


    protected void onPostExecute(Long result) {
        Log.e(TAG, "onPostExecute: finished");
    }
}
