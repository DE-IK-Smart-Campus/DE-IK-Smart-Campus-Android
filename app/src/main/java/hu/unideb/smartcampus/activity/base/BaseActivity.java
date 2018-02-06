package hu.unideb.smartcampus.activity.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import hu.unideb.smartcampus.dialog.settings.language.helper.LanguageHelper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
