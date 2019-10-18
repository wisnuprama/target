package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.navigator.OkrNavigator;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives.ObjectivesFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives.ObjectivesViewModel;

public class OkrActivity extends AppCompatActivity implements OkrNavigator {

    @BindView(R.id.bottom_app_bar)
    Toolbar bottomAppBar;

    private ObjectivesViewModel mObjectivesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okr_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.bottom_app_bar));
        setupViewModel();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ObjectivesFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    protected void onDestroy() {
        this.mObjectivesViewModel.onActivityDestroyed();
        super.onDestroy();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.objectives_bottom_appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mObjectivesViewModel.onBottomAppBarMenuItemSelected(item);
        Log.d("ajaja", "hahahaha");
        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {
        mObjectivesViewModel = ViewModelProviders.of(this).get(ObjectivesViewModel.class);
        mObjectivesViewModel.onActivityCreated(this);
    }
}
