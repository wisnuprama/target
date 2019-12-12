package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public class InternetConnNetworkCallback extends ConnectivityManager.NetworkCallback {

    private final Activity mActivity;
    public InternetConnNetworkCallback(@NonNull Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        SnackbarUtils.showSnackbar(mActivity, "Connected");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        SnackbarUtils.showSnackbar(mActivity,
                                   "Need internet connection",
                                   Snackbar.LENGTH_INDEFINITE);
    }

    public static ConnectivityManager.NetworkCallback register(Activity activity, ConnectivityManager connectivityManager) {
        ConnectivityManager.NetworkCallback internetConnNetworkCallback = Injector.provideInternetConnectivityNetworkCallback(activity);
        NetworkRequest.Builder builder = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        connectivityManager.registerNetworkCallback(builder.build(), internetConnNetworkCallback);
        return internetConnNetworkCallback;
    }
}
