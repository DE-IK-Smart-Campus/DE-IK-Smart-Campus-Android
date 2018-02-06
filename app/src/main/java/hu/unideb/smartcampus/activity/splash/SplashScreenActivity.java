package hu.unideb.smartcampus.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.login.LoginActivity;

import static hu.unideb.smartcampus.container.Container.SPLASH_SCREEN_TIME_OUT;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);

    }
}
