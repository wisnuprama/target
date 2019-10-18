package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {

    public enum ThemeMode {
            LIGHT,
            DARK,
            BATTERY_SAVER,
    }

    public static void applyTheme(ThemeMode themeMode) {
        switch (themeMode) {
            case LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case BATTERY_SAVER:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
