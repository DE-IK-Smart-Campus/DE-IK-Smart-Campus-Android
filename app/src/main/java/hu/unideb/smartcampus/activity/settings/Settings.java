package hu.unideb.smartcampus.activity.settings;

import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Set;

public class Settings {

	private static final String COLOR_KEY = "language_list_key";
	private static final String COLORS_KEY = "timetable_event_color_chooser_key";

	private String color;
	private Integer c;

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	public void load(SharedPreferences prefs) {
		color = prefs.getString(COLOR_KEY, "1");
		c = prefs.getInt(COLORS_KEY, 0);
	}

	public void save(SharedPreferences prefs) {
		SharedPreferences.Editor editor = prefs.edit();
		save(editor);
		editor.commit();
	}

	public void saveDeferred(SharedPreferences prefs) {
		SharedPreferences.Editor editor = prefs.edit();
		save(editor);
		editor.apply();
	}

	public void save(SharedPreferences.Editor editor) {
		editor.putString(COLOR_KEY, color);
		editor.putInt(COLORS_KEY, c);
	}
}
