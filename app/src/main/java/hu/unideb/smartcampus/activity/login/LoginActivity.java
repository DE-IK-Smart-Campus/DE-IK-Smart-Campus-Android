package hu.unideb.smartcampus.activity.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.main.MainActivity;
import hu.unideb.smartcampus.pojo.login.ActualUserInfo;
import hu.unideb.smartcampus.task.login.LoginTask;

import static hu.unideb.smartcampus.container.Container.MY_REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_username_edittext)
    EditText username;

    @BindView(R.id.login_password_edittext)
    EditText password;

    @BindView(R.id.login_username_error_image_view)
    ImageView usernameError;

    @BindView(R.id.login_password_error_image_view)
    ImageView passwordError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toasty.info(getApplicationContext(), Locale.getDefault().getLanguage(), Toast.LENGTH_LONG).show();

    }

    private void login() {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), getResources().getString(R.string.usernamePasswordNeed), Toast.LENGTH_LONG).show();
        } else {
            new LoginTask(this).execute(new ActualUserInfo(username.getText().toString().trim(), password.getText().toString().trim(), null));
        }
    }

    @OnClick(R.id.login_button)
    public void loginAction() {
        //TESZT
        Intent mainIntet = new Intent(this, MainActivity.class);
        startActivity(mainIntet);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//            }, MY_REQUEST_CODE);
//            return;
//        }
//
//        login();
    }
}
