package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.InternetConnNetworkCallback;

public class LearnOkrActivity extends AppCompatActivity {

    private static final String URL = "https://felipecastro.com/en/okr/what-is-okr/";

    @BindView(R.id.learn_okr_webview)
    protected WebView mLearnOkrWebview;

    private ConnectivityManager.NetworkCallback mInternetCallback;
    private ConnectivityManager mConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_okr);
        ButterKnife.bind(this);

        setupUtilities();

        // run webview
        openWebView();
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

    private void openWebView() {
        mLearnOkrWebview.loadUrl(URL);
    }
}
