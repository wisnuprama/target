package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.navigator.OkrNavigator;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.service.ThemeModeJobService;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.addobjective.AddObjectiveFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.drawer.BottomDrawerViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives.ObjectivesFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives.ObjectivesViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.BuildUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.ThemeUtils;

public class OkrActivity extends AppCompatActivity implements OkrNavigator {

    @BindView(R.id.bottom_app_bar)
    protected Toolbar mBottomAppBar;

    @BindView(R.id.add_objective)
    protected FloatingActionButton mFloatingActionButton;

    private ObjectivesViewModel mObjectivesViewModel;

    private BottomDrawerViewModel mBottomDrawerViewModel;

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ObjectivesFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onResume() {
        setupThemeModeReceiverOnFresh();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        this.mObjectivesViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.objectives_bottom_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mObjectivesViewModel.onBottomAppBarMenuItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public void startAbout() {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    @Override
    public void startLearnOkr() {
        Intent learnOkrIntent = new Intent(this, LearnOkrActivity.class);
        startActivity(learnOkrIntent);
    }

    @Override
    public void showUserProjects() {
        mBottomDrawerViewModel
                .showBottomNavigationDrawerFragment(getSupportFragmentManager(), null);
    }

    @Override
    public void showAddObjective() {
        // activate the form
        final AddObjectiveFragment addObjectiveFragment = AddObjectiveFragment.newInstance();
        mBottomDrawerViewModel
                .showBottomNavigationDrawerFragment(getSupportFragmentManager(), addObjectiveFragment);
    }

    @Override
    public void showSearchProjects() {
        mBottomDrawerViewModel
                .showBottomNavigationDrawerFragment(getSupportFragmentManager(), null);
    }

    private void setupViewModel() {
        mObjectivesViewModel = ViewModelProviders.of(this).get(ObjectivesViewModel.class);
        mObjectivesViewModel.onActivityCreated(this,
                                               Injector.getProjectRepository(getApplicationContext()),
                                               PreferenceRepository.getActiveProjectId(getApplicationContext()));

        mBottomDrawerViewModel = ViewModelProviders.of(this).get(BottomDrawerViewModel.class);
    }

    private void setupButtonListener() {
        mFloatingActionButton.setOnClickListener(mObjectivesViewModel::onFabAddObjectiveClicked);
    }

    private void setupFirstRun() {
        Context context = getApplicationContext();
        try {
            if(PreferenceRepository.isFirstRun(context)) {
                // run in main thread because the data must be ready at first run
                AppDatabase.seedUser(context);
                AppDatabase.seedProject(context);
                PreferenceRepository.setFirstRunCompleted(context);
            }
        } catch (IOException e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Run schedule job for theme mode service or apply theme
     */
    private void setupThemeModeReceiverOnFresh() {
        final boolean isAutoDarkMode = PreferenceRepository.getAutoDarkMode(getApplicationContext());
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
