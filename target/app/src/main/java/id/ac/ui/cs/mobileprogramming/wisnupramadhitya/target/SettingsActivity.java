package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import butterknife.BindString;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.BuildUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ThemeUtils;

public class SettingsActivity extends AppCompatActivity {

    @BindString(R.string.theme_mode)
    protected String mKeyThemeMode;

    @BindString(R.string.auto_dark_mode)
    protected String mKeyAutoDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        subscribeToPreference();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceRepository.getSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(this::onPreferenceChange);
    }

    private void subscribeToPreference() {
        SharedPreferences preferences = PreferenceRepository
                .getSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(this::onPreferenceChange);
    }

    /**
     * If theme mode change, call {@link ThemeUtils} to apply new mode. Sometimes it is buggy.
     *
     * If auto dark mode on
     * start job to register {@link id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.broadcastreceiver.ThemeModeReceiver}
     * in the background.
     *
     * TODO implement good morning in preference
     * TODO implement backup/export button in preference
     */
    void onPreferenceChange(SharedPreferences sp, String key) {
        Context context = getApplicationContext();
        if(mKeyThemeMode.equals(key)) {
            ThemeUtils.applyTheme(PreferenceRepository.getThemeMode(context));
        } else if(mKeyAutoDarkMode.equals(key)) {
            ThemeModeJobService.scheduleJob(getApplicationContext());
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            disabledAutoDarkModeWhenOnDarkMode();
        }

        void disabledAutoDarkModeWhenOnDarkMode() {
            Context context = getContext();
            if(context != null) {
                boolean isThemeModeDark = context.getString(R.string.theme_value_dark).equals(PreferenceRepository.getThemeMode(context));
                if(!BuildUtils.isAndroid10() || isThemeModeDark) {
                    String autoDarkModeKey = getString(R.string.auto_dark_mode);
                    SwitchPreferenceCompat autoDarkModePreference = findPreference(autoDarkModeKey);
                    autoDarkModePreference.setEnabled(false);
                }
            }
        }
    }
}