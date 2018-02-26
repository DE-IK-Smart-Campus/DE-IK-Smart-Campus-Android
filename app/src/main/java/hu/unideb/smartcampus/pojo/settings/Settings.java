package hu.unideb.smartcampus.pojo.settings;

import android.content.SharedPreferences;
import android.graphics.Color;

import lombok.Data;

import static hu.unideb.smartcampus.container.Container.SETTINGS_CUSTOM_EVENT_DEFAULT_COLOR_KEY;
import static hu.unideb.smartcampus.container.Container.SETTINGS_LANGUAGE_LIST_KEY;
import static hu.unideb.smartcampus.container.Container.SETTINGS_NOTIFICATION_SOUND_PICKER_KEY;
import static hu.unideb.smartcampus.container.Container.SETTINGS_TIMETABLE_EVENT_COLOR_KEY;

@Data
public class Settings {

    private String selected_language;
    private Integer selected_timetable_event_color;
    private Integer selected_custom_event_default_color;
    private String selected_notification_sound;

    protected void load(SharedPreferences prefs) {
        selected_language = prefs.getString(SETTINGS_LANGUAGE_LIST_KEY, "1");
        selected_timetable_event_color = prefs.getInt(SETTINGS_TIMETABLE_EVENT_COLOR_KEY, Color.parseColor("#003459"));
        selected_custom_event_default_color = prefs.getInt(SETTINGS_CUSTOM_EVENT_DEFAULT_COLOR_KEY, Color.parseColor("#136F63"));
        selected_notification_sound = prefs.getString(SETTINGS_NOTIFICATION_SOUND_PICKER_KEY, "T");
    }

    protected void save(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        save(editor);
        editor.commit();
    }

    protected void saveDeferred(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        save(editor);
        editor.apply();
    }

    private void save(SharedPreferences.Editor editor) {
        editor.putString(SETTINGS_LANGUAGE_LIST_KEY, selected_language);
        editor.putInt(SETTINGS_TIMETABLE_EVENT_COLOR_KEY, selected_timetable_event_color);
        editor.putInt(SETTINGS_CUSTOM_EVENT_DEFAULT_COLOR_KEY, selected_custom_event_default_color);
        editor.putString(SETTINGS_NOTIFICATION_SOUND_PICKER_KEY, selected_notification_sound);
    }
}
