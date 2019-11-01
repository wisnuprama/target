package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about.AboutFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about.RecentInfoFragment;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_about, AboutFragment.newInstance())
                    .replace(R.id.container_recent_info, RecentInfoFragment.newInstance())
                    .commitNow();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
