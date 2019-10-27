package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnOkrActivity extends AppCompatActivity {

    private static final String URL = "https://felipecastro.com/en/okr/what-is-okr/";

    @BindView(R.id.learn_okr_webview)
    protected WebView mLearnOkrWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_okr);
        ButterKnife.bind(this);

        // run webview
        openWebView();
    }

    private void openWebView() {
        mLearnOkrWebview.loadUrl(URL);
    }
}
