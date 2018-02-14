package hu.unideb.smartcampus.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import hu.unideb.smartcampus.dialog.settings.language.helper.LanguageHelper;

public class MainApplication extends Application {

    private Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
        MultiDex.install(this);
    }


    public Context getContext() {
        return sContext;
    }
}
