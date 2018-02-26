package hu.unideb.smartcampus.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import java.util.Locale;

import es.dmoral.toasty.Toasty;
import hu.unideb.smartcampus.application.settings.AppSettings;
import hu.unideb.smartcampus.dialog.settings.language.helper.LanguageHelper;

public class MainApplication extends Application {
    public final AppSettings settings = new AppSettings(this);

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        //todo ha nem választ ki semmit akkr a nyelv a telo nyelve ha kiválasztaja akkor az addig míg meg nem változtatja
        settings.load();

        Locale.getDefault().getLanguage();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
        MultiDex.install(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
