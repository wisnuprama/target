package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.addobjective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;

public class AddObjectiveFragment extends Fragment {

    private AddObjectiveViewModel mViewModel;

    public static AddObjectiveFragment newInstance() {
        return new AddObjectiveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_objective, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddObjectiveViewModel.class);
        // TODO: Use the ViewModel
    }

}
