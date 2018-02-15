package hu.unideb.smartcampus.dialog.settings.language;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.dialog.settings.language.model.Language;

public class LanguageChooserDialog extends DialogFragment {
    private ArrayList<Language> langAndLocalesList = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.setContentView(R.layout.dialog_language_chooser);
//        ButterKnife.bind(this, dialog);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//        ArrayList<String> tets = new ArrayList<>();
//        tets.add("Magyar");
//        tets.add("Angol");
//        tets.add("Alapértelmezett");
//
//        loadLanguageList();
//
//        ListView listView = dialog.findViewById(R.id.list);
//
//        ListAdapter listAdapter = new hu.unideb.smatcampus.activity.settings.ListAdapter(getActivity(),langAndLocalesList );
//
//
//        listView.setAdapter(listAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Language l1 = (Language) listView.getItemAtPosition(i);
//                Toast.makeText(getActivity(), l1.getLanguageName(), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
        return dialog;
    }

//    private void loadLanguageList() {
//        for (String langCode : getResources().getStringArray(R.array.ln)) {
//            // based on language code get language name to display in same locale
//            Locale languageLocale = new Locale(langCode);
//            String languageName = languageLocale.getDisplayLanguage(languageLocale);
//            // based on language code get language name to display in english locale
//            Locale englishLocale = new Locale(getString(R.string.en));
//            String languageNameInEnglishLocale = languageLocale.getDisplayLanguage(englishLocale);
//            Language langAndLocale = new Language(langCode, languageName, languageNameInEnglishLocale);
//            langAndLocalesList.add(langAndLocale);
//        }

//    }

}
