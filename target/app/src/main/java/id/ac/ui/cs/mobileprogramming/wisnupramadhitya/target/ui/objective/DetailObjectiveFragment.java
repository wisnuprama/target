package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentDetailObjectiveBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.dialog.DatePickerFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModelFactory;

public class DetailObjectiveFragment extends Fragment {

    @BindView(R.id.objective_deadline_chip)
    protected Chip mDeadlineChip;

    @BindView(R.id.edit_text_objective_title)
    protected TextInputEditText mTitleInput;

    @BindView(R.id.edit_text_objective_reason)
    protected TextInputEditText mRationalInput;

    @BindView(R.id.objective_deadline)
    protected MaterialButton mDeadlineBtn;

    private ObjectiveDetailViewModel mViewModel;
    private FragmentDetailObjectiveBinding mBinding;

    private static final String ARG_OBJECTIVE_ID = "objective_id";

    public static DetailObjectiveFragment newInstance(int objectiveId) {
        DetailObjectiveFragment fragment = new DetailObjectiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OBJECTIVE_ID, objectiveId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_objective,
                                           container, false);
        mBinding.setLifecycleOwner(this);
        View view = mBinding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ObjectiveDetailViewModelFactory viewModelFactory = Injector.provideAddObjectiveViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ObjectiveDetailViewModel.class);
        mBinding.setObjectiveDetailViewModel(mViewModel);
        setupInput();
    }

    private void setupInput() {
        mDeadlineChip.setOnCloseIconClickListener(mViewModel::clearDeadline);
        View.OnFocusChangeListener listener = (v, hasFocus) -> {
            if(!hasFocus) mViewModel.saveObjective(v);
        };
        mTitleInput.setOnFocusChangeListener(listener);
        mRationalInput.setOnFocusChangeListener(listener);

        mDeadlineBtn.setOnClickListener(this::showDatePickerDialog);
        mDeadlineChip.setOnCloseIconClickListener(mViewModel::clearDeadline);
    }

    public void updateObjectiveView(int objectiveId) {
        mViewModel.startUpdateMode(objectiveId);
    }

    private void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(mViewModel);
        newFragment.show(getChildFragmentManager(), DatePickerFragment.TAG_NAME);
    }
}
