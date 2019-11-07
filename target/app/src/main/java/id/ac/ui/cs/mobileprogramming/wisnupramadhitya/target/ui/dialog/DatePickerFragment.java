package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModel;

public class DatePickerFragment extends DialogFragment {

    private ObjectiveDetailViewModel mObjectiveDetailViewModel;

    public static final String TAG_NAME = "deadlineDatePicker";

    public DatePickerFragment(ObjectiveDetailViewModel objectiveDetailViewModel) {
        mObjectiveDetailViewModel = objectiveDetailViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        OffsetDateTime selectedDate = mObjectiveDetailViewModel.deadline.get();
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
        mObjectiveDetailViewModel.setDeadline(OffsetDateTime.of(year, month, dayOfMonth,
                                                                0, 0, 0, 0, ZoneOffset.UTC));
        this.dismiss();
    }
}
