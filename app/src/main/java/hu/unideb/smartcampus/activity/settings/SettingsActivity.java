package hu.unideb.smartcampus.activity.settings;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import com.kizitonwose.colorpreference.ColorPreference;

import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;

import static hu.unideb.smartcampus.container.Container.LANGUAGE_LIST_KEY;
import static hu.unideb.smartcampus.container.Container.TIMETABLE_EVENT_CHOOSER_KEY;

public class SettingsActivity extends PreferenceActivity {

    public static void startThisActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        }
//        initActionBar();
    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        private AppSettings settings;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);


            Preference colorPrefs = findPreference("language_list_key");
            colorPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));
                    return true;
                }
            });
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            settings = AppSettings.getSettings(getActivity());

//            Preference serverAddressPrefs = findPreference("server_address");
//            serverAddressPrefs.setSummary(settings.getServerAddress());

            Preference colorPrefs = findPreference("language_list_key");
            colorPrefs.setSummary(settings.getColor());

            ColorPreference colorPreference = (ColorPreference) findPreference("timetable_event_color_chooser_key");
//            colorPreference.setSummary(settings.getC());


//            Preference c = findPreference("timetable_event_color_chooser_key");
//            c.setSummary(settings.getC());

//            Preference colorsPrefs = findPreference("colors");
//            colorsPrefs.setSummary(settings.getColors().toString());
//
//            Preference storePathPrefs = findPreference("store_path");
//            storePathPrefs.setSummary(settings.getStorePath());
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
        }

        private final SharedPreferences.OnSharedPreferenceChangeListener sharedPrefsChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                settings.load();
            }
        };

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acivity);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle(R.string.settings_text);
//        ButterKnife.bind(this);
//
//        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
//
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }


//    public static class MyPreferenceFragment extends PreferenceFragment {
//
//        @Override
//        public void onCreate(final Bundle savedInstanceState) {
//
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.settings);
//
////            findPreference(LANGUAGE_LIST_KEY).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
////                @Override
////                public boolean onPreferenceChange(Preference preference, Object o) {
////                    return false;
////                }
////            });
////
////            findPreference(TIMETABLE_EVENT_CHOOSER_KEY).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
////                @Override
////                public boolean onPreferenceChange(Preference preference, Object o) {
////                    return false;
////                }
////            });
//
//        }
//    }

    private void initActionBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acivity);
//        setActionBar(toolbar);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayShowHomeEnabled(true);
//        getActionBar().setTitle(R.string.settings_text);
//        ActionBar actionBar = get();
//        if (actionBar != null) {
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
    }
}