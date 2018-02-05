package hu.unideb.smartcampus.main.activity.login.auth;

import android.app.Activity;
import android.os.AsyncTask;

import hu.unideb.smartcampus.dialog.LoadingDialog;
import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;
import hu.unideb.smartcampus.xmpp.Connection;

/**
 * LoginTask gets the Username, Password for ejabberd login
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

public class LoginTask extends AsyncTask<ActualUserInfo, Long, Void> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

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
    protected Void doInBackground(ActualUserInfo... params) {

        ActualUserInfo actualUserInfo = params[0];
        ActualUserInfo xmppUserInfo = BasicAuth.basicAuth(actualUserInfo);

        if (xmppUserInfo != null) {
            Connection.startConnection(xmppUserInfo, activity.getApplicationContext());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loadingDialog.dismiss();
    }
}

