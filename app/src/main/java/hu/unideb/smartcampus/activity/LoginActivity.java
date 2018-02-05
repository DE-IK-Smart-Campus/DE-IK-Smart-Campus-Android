package hu.unideb.smartcampus.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.login.auth.LoginTask;
import hu.unideb.smartcampus.main.activity.login.pojo.ActualUserInfo;
import hu.unideb.smartcampus.sqlite.manager.DatabaseManager;

public class LoginActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 115;
    public static final String LOGIN_DIALOG_TAG = "LOGIN_DIALOG_TAG";

    @BindView(R.id.usernameId)
    EditText username;

    @BindView(R.id.passwordId)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ImageView hunFlagImage = (ImageView) findViewById(R.id.hunFlag);
        ImageView enFlagImage = (ImageView) findViewById(R.id.enFlag);

        hunFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale hunLocale = new Locale("hu");
                changeLocale(hunLocale);
            }
        });
        enFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLocale(Locale.ENGLISH);
            }
        });

    }

    private void login() {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.usernamePasswordNeed, Toast.LENGTH_SHORT).show();
        } else {
                DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
                databaseManager.open();
                new LoginTask(this).execute(new ActualUserInfo(username.getText().toString(), password.getText().toString(), null));
        }
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


    private void changeLocale(final Locale locale) {
        final Resources res = getApplicationContext().getResources();
        final Configuration config = res.getConfiguration();
        config.locale = locale;
        final Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        recreate();
    }
}
