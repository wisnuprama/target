package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Transaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.drawer.BottomDrawerFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objective.AddObjectiveFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objective.ObjectivesFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.BuildUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.NotificationUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ThemeUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModelFactory;

public class OkrActivity extends AppCompatActivity {

    @BindView(R.id.bottom_app_bar)
    protected Toolbar mBottomAppBar;

    @BindView(R.id.add_objective)
    protected FloatingActionButton mFloatingActionButton;

    private ObjectivesViewModel mObjectivesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupFirstRun();

        // set the theme to the true one before create
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        // setup UI
        setContentView(R.layout.activity_okr);
        ButterKnife.bind(this, this);
        setSupportActionBar(mBottomAppBar);

        // setup other
        setupViewModel();
        setupButtonListener();

        // notification
        NotificationUtils.createNotificationChannel(getApplicationContext());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_objectives, ObjectivesFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupThemeModeReceiverOnFresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.objectives_bottom_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.objectives_bottom_app_bar_about:
                startAbout();
                break;
            case R.id.objectives_bottom_app_bar_settings:
                startSettings();
                break;
            case R.id.objectives_bottom_app_bar_learn_okr:
                startLearnOkr();
                break;
            case android.R.id.home:
                showUserProjects();
                break;
            case R.id.objectives_bottom_app_bar_search:
                showSearchProjects();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void startSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void startAbout() {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    public void startLearnOkr() {
        Intent learnOkrIntent = new Intent(this, LearnOkrActivity.class);
        startActivity(learnOkrIntent);
    }

    public void showUserProjects() {
        BottomDrawerFragment
                .showDrawer(getSupportFragmentManager(), null);
    }

    public void showAddObjective() {
        // activate the form
        int projectId = mObjectivesViewModel.selectedProjectId.get();
        String userId = PreferenceRepository.getActiveUserId(getApplicationContext());
        final AddObjectiveFragment addObjectiveFragment = AddObjectiveFragment
                .newInstance(userId, projectId);
        BottomDrawerFragment
                .showDrawer(getSupportFragmentManager(), addObjectiveFragment);
    }

    public void showSearchProjects() {
        BottomDrawerFragment
                .showDrawer(getSupportFragmentManager(), null);
    }

    private void setupViewModel() {
        final Context context = getApplicationContext();
        ObjectivesViewModelFactory factory = Injector.provideObjectivesViewModelFactory(context);
        mObjectivesViewModel = ViewModelProviders.of(this, factory).get(ObjectivesViewModel.class);
        // load latest active project
        mObjectivesViewModel.selectedProjectId.set(PreferenceRepository.getActiveProjectId(this));
    }

    private void setupButtonListener() {
        mFloatingActionButton.setOnClickListener(view -> this.showAddObjective());
    }

    private void setupFirstRun() {
        final Context context = getApplicationContext();
        Activity activity = this;
        // run in main thread because the data must be ready at first run
        AsyncTask.execute(new Runnable() {
            @Transaction
            @Override
            public void run() {
                try {
                    if(PreferenceRepository.isFirstRun(context)) {
                        AppDatabase.seedUser(context);
                        AppDatabase.seedProject(context);
                        PreferenceRepository.setFirstRunCompleted(context);
                        runOnUiThread(activity::recreate);
                    }
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Run schedule job for theme mode service or apply theme
     */
    private void setupThemeModeReceiverOnFresh() {
        final boolean isAutoDarkMode = PreferenceRepository.isAutoDarkModeEnabled(getApplicationContext());
        final String themeMode = PreferenceRepository.getThemeMode(getApplicationContext());
        final boolean isAndroid10 = BuildUtils.isAndroid10();

        final boolean isThemeDarkMode = getString(R.string.theme_value_dark).equals(themeMode);
        if(!isAndroid10 && isAutoDarkMode && !isThemeDarkMode) {
            // running only below android 10, because android natively use
            // dark mode on battery saver
            ThemeModeJobService.scheduleJob(getApplicationContext());
        } else if(isThemeDarkMode) {
            ThemeUtils.applyTheme(themeMode);
        }
    }
}
