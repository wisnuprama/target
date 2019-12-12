package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Utility class for snackbar
 */
public class SnackbarUtils {

    private SnackbarUtils() {
    }

    /**
     * Show snackbar with the text
     * @param view
     * @param snackbarText
     */
    public static void showSnackbar(View view, String text) {
        if (view == null || text == null) {
            return;
        }
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(Activity activity, String text) {
        View rootView = activity.getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(Activity activity, String text, int length) {
        View rootView = activity.getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
        Snackbar.make(rootView, text, length).show();
    }
}
