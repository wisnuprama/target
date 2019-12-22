package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objective;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.function.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentAddObjectiveBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.dialog.DatePickerFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.drawer.BottomDrawerFragment;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.SnackbarUtils;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModelFactory;

public class AddObjectiveFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "project_id";
    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.edit_text_objective_title)
    protected TextInputEditText mTitleTextInputEditText;

    @BindView(R.id.objective_deadline)
    protected MaterialButton mDeadlineBtn;

    @BindView(R.id.objective_deadline_chip)
    protected Chip mDeadlineChip;

    private ObjectiveDetailViewModel mViewModel;

    private FragmentAddObjectiveBinding mBinding;

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_objective,
                                                               container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setView(this);
        View view = mBinding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(ARG_USER_ID);
        int projectId = getArguments().getInt(ARG_PROJECT_ID);
        ObjectiveDetailViewModelFactory viewModelFactory = Injector.provideAddObjectiveViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(ObjectiveDetailViewModel.class);
        mBinding.setObjectiveDetailViewModel(mViewModel);
        mViewModel.startNewMode(userId, projectId);
        askCalendarPermission();
    }

    private void setupView() {
        if(mViewModel.isNewMode())
            mTitleTextInputEditText.requestFocus();
        mDeadlineBtn.setOnClickListener(this::showDatePickerDialog);
        mDeadlineChip.setOnClickListener(this::showDatePickerDialog);
        mDeadlineChip.setOnCloseIconClickListener(mViewModel::clearDeadline);
    }

    private void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(mViewModel);
        newFragment.show(getChildFragmentManager(), DatePickerFragment.TAG_NAME);
    }

    private static final int PERMISSION_CALENDAR_REQUEST_CODE = 101;
    public void askCalendarPermission() {
        final Activity thisActivity = getActivity();
        if(thisActivity == null) return;
        if(ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
            // request the permission
            // PERMISSION_CALENDAR_REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            mViewModel.permissionGranted.set(false);
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},
                               PERMISSION_CALENDAR_REQUEST_CODE);
        } else {
            setupView();
        }
    }

    private void onRequestCalendarPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        final FragmentActivity thisActivity = getActivity();
        if(thisActivity == null) return;
        if (grantResults.length > 0) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_GRANTED:
                    mViewModel.permissionGranted.set(true);
                    setupView();
                    break;
                case PackageManager.PERMISSION_DENIED:
                default:
                    mViewModel.permissionGranted.set(false);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CALENDAR_REQUEST_CODE:
                this.onRequestCalendarPermission(requestCode, permissions, grantResults);
                break;
        }
    }

    /**
     * as button listener
     * @param view
     */
    public void handleSaveObjective(View view) {
        BottomDrawerFragment.dismissDrawer();
        Activity activity = getActivity();
        new Handler().postDelayed(
                () -> {
                    mViewModel.saveObjective(view);
                    activity.runOnUiThread(
                            () -> {
                                String msg = String.format(activity.getString(R.string.add_objective_success), 1);
                                SnackbarUtils.showSnackbar(activity, msg);
                            });
                },
                500);
    }
}
