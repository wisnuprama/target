package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.drawer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

public class BottomDrawerViewModel extends ViewModel {

    private BottomDrawerFragment mBottomDrawerFragment;

    public BottomDrawerFragment showBottomNavigationDrawerFragment(FragmentManager fragmentManager, Fragment childFragment) {
        if(mBottomDrawerFragment == null) {
            mBottomDrawerFragment = BottomDrawerFragment.newInstance();
        }

        if(childFragment != null) {
            mBottomDrawerFragment.setChildFragment(childFragment);
        }
        mBottomDrawerFragment.show(fragmentManager, mBottomDrawerFragment.getTag());
        return mBottomDrawerFragment;
    }
}
