package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;

public class PreferenceRepository {

    public static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getAutoDarkMode(@NonNull Context context) {
        String key = context.getString(R.string.auto_dark_mode);
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public static String getThemeMode(@NonNull Context context) {
        String key = context.getString(R.string.theme_mode);
        return getSharedPreferences(context).getString(key, "light");
    }
}
