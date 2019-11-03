package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.BuildUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.DataExporter;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.GoodMorningUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ThemeUtils;

public class SettingsActivity extends AppCompatActivity {

    @BindString(R.string.theme_mode)
    protected String mKeyThemeMode;

    @BindString(R.string.auto_dark_mode)
    protected String mKeyAutoDarkMode;

    @BindString(R.string.good_morning)
    protected String mKeyGoodMorning;

    @BindView(R.id.progressBarHolder)
    protected FrameLayout mProgressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment.newInstance(this))
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
     */
    void onPreferenceChange(SharedPreferences sp, String key) {
        Context context = getApplicationContext();
        if(mKeyThemeMode.equals(key)) {
            ThemeUtils.applyTheme(PreferenceRepository.getThemeMode(context));
        } else if(mKeyAutoDarkMode.equals(key)) {
            ThemeModeJobService.scheduleJob(getApplicationContext());
        } else if(mKeyGoodMorning.equals(key)) {
            boolean isEnabled = PreferenceRepository.isGoodMorningActive(context);
            GoodMorningUtils.setReceiverEnabled(context, isEnabled);
            if(isEnabled) {
                GoodMorningUtils.startGoodMorning(context);
            } else {
                GoodMorningUtils.cancelGoodMorning(context);
            }
        }
    }

    public void showProgressbar() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        mProgressBarHolder.setAnimation(inAnimation);
        mProgressBarHolder.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        mProgressBarHolder.setAnimation(outAnimation);
        mProgressBarHolder.setVisibility(View.GONE);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        public static SettingsFragment newInstance(SettingsActivity settingsActivity) {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setSettingsActivity(settingsActivity);
            return settingsFragment;
        }

        SettingsActivity mSettingsActivity;
        public void setSettingsActivity(SettingsActivity settingsActivity) {
            mSettingsActivity = settingsActivity;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            disabledAutoDarkModeWhenOnDarkMode();
            setupExportButton();
        }

        void setupExportButton() {
            Preference exportBtn = findPreference(getString(R.string.backup));
            exportBtn.setOnPreferenceClickListener((preference -> {
                mSettingsActivity.showProgressbar();
                Handler handler = new Handler();
                // delay for 1s before start the back up process
                // for better use experience (not blinked)
                handler.postDelayed(() -> AsyncTask.execute(() -> {
                    new DataExporter(getContext())
                            .write()
                            .buildIntent()
                            .share(getActivity());
                    getActivity().runOnUiThread(mSettingsActivity::hideProgressBar);
                }), 1000);
                return true;
            }));
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

