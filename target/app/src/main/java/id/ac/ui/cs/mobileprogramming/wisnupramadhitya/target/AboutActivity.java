package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about.AboutFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about.RecentInfoFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.InternetConnNetworkCallback;

public class AboutActivity extends AppCompatActivity {

    private ConnectivityManager.NetworkCallback mInternetCallback;
    private ConnectivityManager mConnectivityManager;

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

        setupUtilities();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConnectivityManager.unregisterNetworkCallback(mInternetCallback);
    }

    private void setupUtilities() {
        // connectivity
        mConnectivityManager = getApplicationContext()
                .getSystemService(ConnectivityManager.class);
        if(mConnectivityManager == null) return;
        mInternetCallback = InternetConnNetworkCallback.register(this, mConnectivityManager);
    }

}
