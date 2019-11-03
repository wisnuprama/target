package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.addobjective;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

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

    @BindView(R.id.objective_deadline)
    protected MaterialButton mDeadlineBtn;

    @BindView(R.id.objective_deadline_chip)
    protected Chip mDeadlineChip;

    private AddObjectiveViewModel mViewModel;

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
        View view = mBinding.getRoot();
        ButterKnife.bind(this, view);
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
        mBinding.setAddObjectiveViewModel(mViewModel);

        // setup listener
        mTitleTextInputEditText.requestFocus();
        mDeadlineBtn.setOnClickListener(this::showDatePickerDialog);
        mDeadlineChip.setOnCloseIconClickListener(mViewModel::clearDeadline);
    }

    private void showDatePickerDialog(View v) {
        // TODO listener doesn't get called
        //        DialogFragment newFragment = new DatePickerFragment(mViewModel);
        //        newFragment.show(getChildFragmentManager(), DatePickerFragment.TAG_NAME);
        Toast.makeText(getActivity(), R.string.under_development, Toast.LENGTH_LONG)
                .show();
    }

    public static class DatePickerFragment extends DialogFragment {

        AddObjectiveViewModel mAddObjectiveViewModel;

        public static final String TAG_NAME = "deadlineDatePicker";

        DatePickerFragment(AddObjectiveViewModel addObjectiveViewModel) {
            mAddObjectiveViewModel = addObjectiveViewModel;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            OffsetDateTime selectedDate = mAddObjectiveViewModel.deadline.get();
            final Calendar c = Calendar.getInstance();
            if(selectedDate != null) {
                c.set(Calendar.YEAR, selectedDate.getYear());
                c.set(Calendar.MONTH, selectedDate.getMonthValue());
                c.set(Calendar.DAY_OF_MONTH, selectedDate.getDayOfMonth());
            }

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this::setDate, year, month, day);
            datePickerDialog.setOnDateSetListener(this::setDate);
            return datePickerDialog;
        }

        void setDate(DatePicker view, int year, int month, int dayOfMonth) {
            mAddObjectiveViewModel.deadline
                    .set(OffsetDateTime.of(year, month, dayOfMonth,
                                           0, 0, 0, 0, ZoneOffset.UTC));
            this.dismiss();
        }
    }

}
