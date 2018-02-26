package hu.unideb.smartcampus.activity.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.kizitonwose.colorpreference.ColorPreference;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.activity.main.MainActivity;
import hu.unideb.smartcampus.application.settings.AppSettings;
import hu.unideb.smartcampus.fragment.calendar.CalendarFragment;
import hu.unideb.smartcampus.interfaces.OnBackPressedListener;

public class SettingsActivity extends BaseActivity {

    public static void startThisActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.settings_text);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();

        }
    }
    public static class MyPreferenceFragment extends PreferenceFragment {
        private AppSettings settings;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);


            Preference colorPrefs = findPreference("language_chooser_key");
            colorPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));
                    return true;
                }
            });
            RingtonePreference colorPrefs1 = (RingtonePreference) findPreference("notification_sound_picker_chooser_key");
            colorPrefs1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(String.valueOf(newValue));

                    return true;
                }
            });

//            Preference serverAddressPrefs = findPreference("server_address");
//            serverAddressPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    preference.setSummary(String.valueOf(newValue));
//                    return true;
//                }
//            });
//
//            Preference colorPrefs = findPreference("color");
//            colorPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    preference.setSummary(String.valueOf(newValue));
//                    return true;
//                }
//            });
//
//            Preference colorsPrefs = findPreference("colors");
//            colorsPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    preference.setSummary(String.valueOf(newValue));
//                    return true;
//                }
//            });
//
//            Preference storePathPrefs = findPreference("store_path");
//            storePathPrefs.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object newValue) {
//                    preference.setSummary(String.valueOf(newValue));
//                    return true;
//                }
//            });
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            settings = AppSettings.getSettings(getActivity());

            Preference colorPrefs = findPreference("language_chooser_key");
            colorPrefs.setSummary(settings.getSelected_language());

            ColorPreference colorPreference = (ColorPreference) findPreference("timetable_event_color_chooser_key");
//            colorPreference.setSummary(settings.getC());

            RingtonePreference rf = (RingtonePreference) findPreference("notification_sound_picker_chooser_key");
            rf.setSummary(settings.getSelected_notification_sound());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent= new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    }
}