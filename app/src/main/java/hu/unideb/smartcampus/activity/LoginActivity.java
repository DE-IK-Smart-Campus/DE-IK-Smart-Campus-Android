package hu.unideb.smartcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.xmpp.Connection;

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
        if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordNeed, Toast.LENGTH_SHORT).show();
        } else if (!username.getText().toString().equals("admin") || !password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordWrong, Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), R.string.loginSucces, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity_SmartCampus.class);

            new Thread(new Runnable() {
                public void run() {
                    XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                            .setUsernameAndPassword("testuser", "admin")
                            .setServiceName("192.168.1.11")
                            .setHost("192.168.1.11")
                            .setPort(5222)
                            .build();
                    connection = Connection.getInstance();
                    connection.setXMPPTCPConnection(config);
                    connection.getXmpptcpConnection().isConnected();
                }

            }).start();


        }
    }

    public void loginOnClick(View v) {
        login();
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
    }

}

