package hu.unideb.smartcampus.activity.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.jaredrummler.android.colorpicker.ColorPreference;

import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;

import static hu.unideb.smartcampus.container.Container.TIMETABLE_EVENT_COLOR_CHOOSER_KEY;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        final ColorPreference colorPreference = (ColorPreference) findPreference(TIMETABLE_EVENT_COLOR_CHOOSER_KEY);

    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle(R.string.settings_text);
//        ButterKnife.bind(this);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
