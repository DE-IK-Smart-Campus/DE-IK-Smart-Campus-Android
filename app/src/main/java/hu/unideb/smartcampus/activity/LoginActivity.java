package hu.unideb.smartcampus.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.main.activity.login.auth.BasicAuth;
import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.HOSTNAME;

public class LoginActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 115;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void login() {
        setupVariables();
        ActualUserInfo actualUserInfo = null;

        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordNeed, Toast.LENGTH_SHORT).show();
        } else {

            try {
                actualUserInfo = new BasicAuth().execute(new ActualUserInfo
                        (username.getText().toString(), password.getText().toString(), null))
                        .get(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                Toast.makeText(getApplicationContext(), R.string.login_try_again, Toast.LENGTH_SHORT).show();
            }
            if (actualUserInfo.getUsername() == null) {
                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_SHORT).show();
            } else {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_login, new LoadingDialogFragment());
                fragmentTransaction.commitAllowingStateLoss();
                Toast.makeText(getApplicationContext(), R.string.login_succes, Toast.LENGTH_SHORT).show();

                final ActualUserInfo finalActualUserInfo = actualUserInfo;
                new Thread(new Runnable() {
                    public void run() {
                        BOSHConfiguration config = null;
                        try {
                            config = BOSHConfiguration.builder()
                                    .setUsernameAndPassword(finalActualUserInfo.getUsername(), finalActualUserInfo.getXmppPassword())
                                    .setXmppDomain(HOSTNAME)
                                    .setHost(HOSTNAME)
                                    .setPort(80)
                                    .setFile("/http-bind/")
                                    .setResource("Smartcampus")
                                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                                    .setDebuggerEnabled(false)
                                    .build();
                        } catch (XmppStringprepException e) {
                            e.printStackTrace();
                        }
                        Connection.getInstance().startBoshConnection(config, getApplicationContext());
                    }
                }).start();
            }}
    }

    public void loginOnClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_REQUEST_CODE);
            return;
        }

        login();
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
    }

}
