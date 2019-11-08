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

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentDetailObjectiveBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.dialog.DatePickerFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.SnackbarUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModelFactory;

public class DetailObjectiveFragment extends Fragment {

    @BindView(R.id.objective_deadline_chip)
    protected Chip mDeadlineChip;

    @BindView(R.id.edit_text_objective_title)
    protected TextInputEditText mTitleInput;

    @BindView(R.id.edit_text_objective_reason)
    protected TextInputEditText mRationalInput;

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
        mBinding.setView(this);
        View view = mBinding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup the viewmodel
        ObjectiveDetailViewModelFactory viewModelFactory = Injector.provideAddObjectiveViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ObjectiveDetailViewModel.class);
        mBinding.setObjectiveDetailViewModel(mViewModel);

        Bundle args = getArguments();
        if(args != null) {
            int objectiveId = args.getInt(ARG_OBJECTIVE_ID);
            mViewModel.startUpdateMode(objectiveId);
        }
        setupInput();
    }

    @Override
    public void onDestroyView() {
        if(mViewModel.isDirty())
            mViewModel.saveObjective(getView());
        super.onDestroyView();
    }

    private void setupInput() {
        View.OnFocusChangeListener listener = (v, hasFocus) -> {
            // save the objective when the textinput on blur
            if(!hasFocus) mViewModel.saveObjective(v);
        };
        mTitleInput.setOnFocusChangeListener(listener);
        mRationalInput.setOnFocusChangeListener(listener);

        mDeadlineChip.setOnClickListener(this::showDatePickerDialog);
        mDeadlineChip.setOnCloseIconClickListener(mViewModel::clearDeadline);
    }

    public void updateObjectiveView(int objectiveId) {
        // start the update mode, used in two pane layout
        // where the each item call this
        if(mViewModel.isDirty())
            mViewModel.saveObjective(getView());
        mViewModel.startUpdateMode(objectiveId);
    }

    private void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment(mViewModel);
        // show datepicker
        newFragment.show(getChildFragmentManager(), DatePickerFragment.TAG_NAME);
    }

    public void handleDeleteObjective(View view) {
        // delete objective
        mViewModel.deleteCurrentObjective(view);
        // show delete success
        SnackbarUtils.showSnackbar(getActivity(), String.format(getString(R.string.delete_objective_success), 1));
        // back to previous stack
        getFragmentManager().popBackStackImmediate();
    }
}
