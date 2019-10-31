package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.addobjective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentAddObjectiveBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.AddObjectiveViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.AddObjectiveViewModelFactory;

public class AddObjectiveFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "project_id";
    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.edit_text_objective_title)
    protected TextInputEditText mTitleTextInputEditText;

    private AddObjectiveViewModel mViewModel;

    private FragmentAddObjectiveBinding mFragmentAddObjectiveBinding;

    public static AddObjectiveFragment newInstance(String userId, int projectId) {
        AddObjectiveFragment fragment = new AddObjectiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentAddObjectiveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_objective,
                                                               container, false);
        mFragmentAddObjectiveBinding.setLifecycleOwner(this);
        View view = mFragmentAddObjectiveBinding.getRoot();
        ButterKnife.bind(this, view);
        mTitleTextInputEditText.requestFocus();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(ARG_USER_ID);
        int projectId = getArguments().getInt(ARG_PROJECT_ID);
        AddObjectiveViewModelFactory viewModelFactory = Injector
                .provideAddObjectiveViewModelFactory(getActivity(), userId, projectId);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddObjectiveViewModel.class);
        mFragmentAddObjectiveBinding.setAddObjectiveViewModel(mViewModel);
    }

}
