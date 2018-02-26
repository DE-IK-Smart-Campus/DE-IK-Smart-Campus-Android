package hu.unideb.smartcampus.application.settings;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import hu.unideb.smartcampus.pojo.settings.Settings;
import hu.unideb.smartcampus.application.MainApplication;

public class AppSettings extends Settings {

	public static AppSettings getSettings(Activity activity) {
		return getSettings(activity.getApplication());
	}

	public static AppSettings getSettings(Application application) {
		return ((MainApplication) application).settings;
	}

	private final MainApplication application;

	public AppSettings(MainApplication application) {
		this.application = application;
	}

	public void load() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
		load(prefs);
	}

	public void save() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
		save(prefs);
	}

	public void saveDeferred() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(application);
		saveDeferred(prefs);
	}
}
