package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.navigator.OkrNavigator;

public class ObjectivesViewModel extends ViewModel {

    private OkrNavigator mOkrNavigator;



    public void onActivityCreated(@NonNull  OkrNavigator okrNavigator) {
        this.mOkrNavigator = okrNavigator;
    }

    public void onActivityDestroyed() {
        this.mOkrNavigator = null;
    }

    public void onBottomAppBarMenuItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.objectives_bottom_app_bar_about:
                mOkrNavigator.startAbout();
                break;
            case R.id.objectives_bottom_app_bar_settings:
                mOkrNavigator.startSettings();
                break;
            case R.id.objectives_bottom_app_bar_search:
            default:
        }
    }
}
