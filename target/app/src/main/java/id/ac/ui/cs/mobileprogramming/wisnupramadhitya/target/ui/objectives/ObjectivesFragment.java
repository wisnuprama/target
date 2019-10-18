package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;

public class ObjectivesFragment extends Fragment {

    private ObjectivesViewModel mViewModel;

    public static ObjectivesFragment newInstance() {
        return new ObjectivesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.objectives_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ObjectivesViewModel.class);
    }
}
