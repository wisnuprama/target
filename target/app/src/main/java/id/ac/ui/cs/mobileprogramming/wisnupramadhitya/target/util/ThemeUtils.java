package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.content.Context;
import android.os.PowerManager;

import androidx.appcompat.app.AppCompatDelegate;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;

/**
 * Theme utility class
 */
public class ThemeUtils {

    private ThemeUtils() {
    }

    public enum ThemeMode {
            LIGHT,
            DARK,
            BATTERY_SAVER,
    }

    /**
     * Change theme based on themeMode given.
     * @param themeMode
     */
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

    /**
     * Change theme based on String themeNode given.
     * @param themeMode
     */
    public static void applyTheme(String themeMode) {
        switch (themeMode) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "battery_saver":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    /**
     * When get called, it will change the theme based on battery saver activation.
     * If the powersave mode, then change to dark mode.
     * Otherwise, apply current theme based on {@link PreferenceRepository}
     * @param context
     */
    public static void changeThemeOnBatterySaver(Context context) {
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null && pm.isPowerSaveMode()) {
            applyTheme(ThemeMode.DARK);
        } else {
            // otherwise apply user settings
            applyTheme(PreferenceRepository.getThemeMode(context));
        }
    }
}
