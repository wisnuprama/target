package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import java.time.OffsetDateTime;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.SnackbarUtils;

public class AddObjectiveViewModel extends ViewModel {

    private ObjectiveRepository mObjectiveRepository;
    private int mProjectId;
    private String mUserId;

    public final ObservableField<String> title = new ObservableField<>("");
    public final ObservableField<String> rational = new ObservableField<>("");
    public final ObservableField<OffsetDateTime> deadline =  new ObservableField<>();

    public AddObjectiveViewModel(ObjectiveRepository objectiveRepository, String userId, int projectId) {
        mObjectiveRepository = objectiveRepository;
        mUserId = userId;
        mProjectId = projectId;
    }

    public void setProjectId(int projectId) {
        mProjectId = projectId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public void addObjective(View view) {
        mObjectiveRepository.createObjective(mUserId, mProjectId, title.get(), rational.get(), deadline.get());
        SnackbarUtils.showSnackbar(view, "Created");
    }

    public void clearDeadline(View view) {
        deadline.set(null);
    }
}
