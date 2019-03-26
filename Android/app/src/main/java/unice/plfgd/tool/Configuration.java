package unice.plfgd.tool;

import android.content.SharedPreferences;

public class Configuration {
	private static Configuration ourInstance;
	private final SharedPreferences preferences;

	private Configuration(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public static void init(SharedPreferences prefs) {
		ourInstance = new Configuration(prefs);
	}

	public static boolean isInit() {
		return ourInstance != null;
	}

	public static Configuration getInstance() {
		return ourInstance;
	}

	public boolean has(String key) {
		return preferences.contains(key);
	}

	public String getOrNull(String key) {
		return getOrDefault(key, null);
	}

	public String getOrDefault(String key, String def) {
		return preferences.getString(key, def);
	}

	public void set(String key, String value) {
		preferences.edit().putString(key, value).apply();
	}
}
