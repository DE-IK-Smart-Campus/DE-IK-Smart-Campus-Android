package hu.unideb.smartcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.bosh.BOSHConfiguration;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.HOSTNAME;

public class LoginActivity extends AppCompatActivity {

    private Connection connection;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void login() {
        setupVariables();
     /*   if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordNeed, Toast.LENGTH_SHORT).show();
        } else if (!username.getText().toString().equals("admin") || !password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordWrong, Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
       */
        Toast.makeText(getApplicationContext(), R.string.loginSucces, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity_SmartCampus.class);

        new Thread(new Runnable() {
            public void run() {
                BOSHConfiguration config = BOSHConfiguration.builder()
                        .setUsernameAndPassword("testuser", "admin")
                        .setServiceName(HOSTNAME)
                        .setHost(HOSTNAME)
                        .setPort(80)
                        .setFile("/http-bind/")
                        .build();
                connection = Connection.getInstance();
                connection.setXMPPTCPConnection(config);
                if (connection.getXmppConnection().isConnected()) {
                    Log.d("Conected:", "CONNECTED");
                } else {
                    Log.e("Conected:", "NOT CONNECTED");
                }
            }

        }).start();

        startActivity(intent);

        //     }
    }

    public void loginOnClick(View v) {
        login();
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
    }

}

