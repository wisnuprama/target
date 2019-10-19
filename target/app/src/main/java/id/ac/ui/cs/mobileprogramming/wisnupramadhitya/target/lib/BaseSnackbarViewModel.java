package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.lib;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

public abstract class BaseSnackbarViewModel {

    private final ObservableField<String> mSnackbarText = new ObservableField<>("");

    private void getSnackbarText() {
        mSnackbarText.get();
    }

    public void setSnackbarText(@NonNull String snackbarText) {
        mSnackbarText.set(snackbarText);
    }
}
