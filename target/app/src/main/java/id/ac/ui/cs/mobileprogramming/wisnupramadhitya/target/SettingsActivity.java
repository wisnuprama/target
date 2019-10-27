package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.BuildUtils;

public class SettingsActivity extends AppCompatActivity {

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
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            disabledAutoDarkModeWhenOnDarkMode();
        }

        public void disabledAutoDarkModeWhenOnDarkMode() {
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