package hu.unideb.smartcampus.activity.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.settings_text);
        ButterKnife.bind(this);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        private final String CUSTOM_PICKER_PREF_KEY = "color_pref_lobster";

        @Override
        public void onCreate(final Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

//            findPreference(CUSTOM_PICKER_PREF_KEY).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
////                    showColorDialog(preference);
//
//                    return true;
//                }
//            });


        }

//        private void showColorDialog(final Preference preference) {
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            View colorView = inflater.inflate(R.layout.dialog_color, null);
//
//            int color = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(CUSTOM_PICKER_PREF_KEY, Color.YELLOW);
//            final LobsterPicker lobsterPicker = (LobsterPicker) colorView.findViewById(R.id.lobsterPicker);
//            LobsterShadeSlider shadeSlider = (LobsterShadeSlider) colorView.findViewById(R.id.shadeSlider);
//
//            lobsterPicker.addDecorator(shadeSlider);
//            lobsterPicker.setColorHistoryEnabled(true);
//            lobsterPicker.setHistory(color);
//            lobsterPicker.setColor(color);
//
//            new AlertDialog.Builder(getActivity())
//                    .setView(colorView)
//                    .setTitle("Choose Color")
//                    .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ((ColorPreference) preference).setValue(lobsterPicker.getColor());
//                        }
//                    })
//                    .setNegativeButton("CLOSE", null)
//                    .show();
//        }


    }
}
