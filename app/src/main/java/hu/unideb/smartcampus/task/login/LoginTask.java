package hu.unideb.smartcampus.task.login;

import android.app.Activity;
import android.os.AsyncTask;

import java.net.HttpURLConnection;

import hu.unideb.smartcampus.activity.login.LoginActivity;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.pojo.login.ActualUserInfo;
import hu.unideb.smartcampus.xmpp.Connection;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * LoginTask gets the Username, Password for ejabberd login
 * from restless call
 *
 * @see LoginActivity
 * @see ActualUserInfo
 * <p>
 * <p>
 * UnsafeOkHttpClient TODO
 * <p>
 * Created by Headswitcher on 2017. 03. 16..
 */

public class LoginTask extends AsyncTask<ActualUserInfo, Long, ReturnPojo> {

    private LoadingDialog loadingDialog;

    private Activity activity;

    public LoginTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), "LOADING");
        super.onPreExecute();
    }

    @Override
    protected ReturnPojo doInBackground(ActualUserInfo... params) {

        ActualUserInfo actualUserInfo = params[0];
        BasicAuthReturnPojo authReturnPojo = BasicAuth.basicAuth(actualUserInfo);

        if (authReturnPojo.getStatusCode() == HttpURLConnection.HTTP_OK) {
            ActualUserInfo xmppUserInfo = authReturnPojo.getActualUserInfo();
            if (xmppUserInfo != null) {
                Connection.startConnection(xmppUserInfo);
            }
        } else {
            return new ReturnPojo(authReturnPojo.getStatusCode());
        }
        if (Connection.getInstance().getXmppConnection().isConnected()) {
            return new ReturnPojo(HTTP_OK);
        }
        return new ReturnPojo(HTTP_INTERNAL_ERROR);
    }

    @Override
    protected void onPostExecute(ReturnPojo returnPojo) {
        super.onPostExecute(returnPojo);
        loadingDialog.dismiss();
        // TextView errorTextViewAtCustomNumberDialog = dialogFragment.getDialog().findViewById(R.id.custom_number_input_error_textview);
        switch (returnPojo.getStatusCode()) {
            case HTTP_OK:
                //
                break;

            case HTTP_BAD_REQUEST:
                //TODO SHOW ERROR
                break;

            //  case NO_INTERNET_ERROR:
            //     //TODO SHOW ERROR
            //    break;

            //     case UNKNOW_ERROR:
            //         //TODO SHOW ERROR
            //        break;

            case HTTP_GATEWAY_TIMEOUT:
                //TODO SHOW ERROR
                break;

            case HTTP_CLIENT_TIMEOUT:
                //TODO SHOW ERROR
                break;

            case HTTP_UNAUTHORIZED:
                //TODO SHOW ERROR

            case HTTP_INTERNAL_ERROR:
                //TODO SHOW ERROR
        }
    }
}

