package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;

public class PreferenceRepository {

    public static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getEditor(@NonNull Context context) {
        return getSharedPreferences(context).edit();
    }

    public static boolean getAutoDarkMode(@NonNull Context context) {
        String key = context.getString(R.string.auto_dark_mode);
        return getSharedPreferences(context).getBoolean(key, false);
    }

    public static String getThemeMode(@NonNull Context context) {
        String key = context.getString(R.string.theme_mode);
        return getSharedPreferences(context).getString(key, "light");
    }

    public static String getActiveUserId(@NonNull Context context) {
        String key = context.getString(R.string.active_user_id);
        return getSharedPreferences(context).getString(key, null);
    }

    public static void setActiveUserId(@NonNull Context context, String userId) {
        String key = context.getString(R.string.active_user_id);
        getEditor(context)
            .putString(key, userId)
            .apply();
    }

    public static int getActiveProjectId(@NonNull Context context) {
        String key = context.getString(R.string.active_project_id);
        return getSharedPreferences(context).getInt(key, -1);
    }

    public static void setActiveProjectId(@NonNull Context context, int projectId) {
        String key = context.getString(R.string.active_project_id);
        getEditor(context)
            .putInt(key, projectId)
            .apply();
    }

    public static boolean isFirstRun(@NonNull Context context) {
        String key = context.getString(R.string.first_run);
        return !getSharedPreferences(context).getBoolean(key, false);
    }

    public static void setFirstRunCompleted(@NonNull Context context) {
        String key = context.getString(R.string.first_run);
        getEditor(context)
                .putBoolean(key, true)
                .apply();
    }
}
