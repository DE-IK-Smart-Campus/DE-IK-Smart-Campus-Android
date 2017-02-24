package hu.unideb.smartcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.unideb.smartcampus.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupVariables();
        loginButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               login();
                                           }
                                       }
        );

    }

    private void login() {
        setupVariables();
        if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordNeed, Toast.LENGTH_SHORT).show();
        } else if (!username.getText().toString().equals("admin") || !password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordWrong, Toast.LENGTH_SHORT).show();
        } else if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), R.string.loginSucces, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SmartCampus.class);
            startActivity(intent);
        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
        loginButton = (Button) findViewById(R.id.loginbuttonId);
    }

}

