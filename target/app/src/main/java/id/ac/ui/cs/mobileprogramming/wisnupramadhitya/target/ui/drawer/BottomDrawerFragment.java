package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;


/**
 * A simple {@link BottomSheetDialogFragment} subclass.
 * Use the {@link BottomDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomDrawerFragment extends BottomSheetDialogFragment {

    private Fragment mChildFragment;

    private static BottomDrawerFragment instance;

    public static BottomDrawerFragment newInstance() {
        return new BottomDrawerFragment();
    }

    public static synchronized BottomDrawerFragment getInstance() {
        if(instance == null) {
            instance = newInstance();
        }

        return instance;
    }

    /**
     * Only one content in bottom fragment at a time.
     * @param fragmentManager
     * @param childFragment
     * @return
     */
    public static void showDrawer(FragmentManager fragmentManager, Fragment childFragment) {
        BottomDrawerFragment bottomDrawerFragment = getInstance();
        if(childFragment != null) {
            bottomDrawerFragment.setChildFragment(childFragment);
        }
        bottomDrawerFragment.show(fragmentManager, bottomDrawerFragment.getTag());
    }

    public static void dismissDrawer() {
        getInstance().dismiss();
    }

    public static synchronized void destroy() {
        instance = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_okr_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadChildFragment();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setChildFragment(null);
    }

    public void setChildFragment(Fragment childFragment) {
        mChildFragment = childFragment;
    }

    /**
     * Load child fragment
     */
    private void loadChildFragment() {
        if(mChildFragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom_drawer_container, mChildFragment)
                    .commitNow();
        }
    }
}
