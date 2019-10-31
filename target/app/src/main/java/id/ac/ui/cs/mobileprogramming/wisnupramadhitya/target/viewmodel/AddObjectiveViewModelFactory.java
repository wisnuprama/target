package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;

public class AddObjectiveViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ObjectiveRepository mObjectiveRepository;
    private String mUserId;
    private Integer mProjectId;

    public AddObjectiveViewModelFactory(ObjectiveRepository objectiveRepository,
                                        String userId, Integer projectId) {
        mObjectiveRepository = objectiveRepository;
        mUserId = userId;
        mProjectId = projectId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddObjectiveViewModel(mObjectiveRepository, mUserId, mProjectId);
    }
}
