package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.addobjective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.AddObjectiveViewModel;

public class AddObjectiveFragment extends Fragment {

    @BindView(R.id.edit_text_objective_title)
    protected TextInputEditText mTitleTextInputEditText;

    private AddObjectiveViewModel mViewModel;

    public static AddObjectiveFragment newInstance() {
        return new AddObjectiveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_objective, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddObjectiveViewModel.class);

        mTitleTextInputEditText.requestFocus();
    }

}
