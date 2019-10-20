package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.os.Build;

public class BuildUtils {

    public static boolean isAndroid10() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.Q;
    }
}
