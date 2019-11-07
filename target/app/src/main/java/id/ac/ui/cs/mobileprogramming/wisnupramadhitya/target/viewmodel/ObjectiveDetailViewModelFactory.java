package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;

public class ObjectiveDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ObjectiveRepository mObjectiveRepository;

    public ObjectiveDetailViewModelFactory(ObjectiveRepository objectiveRepository) {
        mObjectiveRepository = objectiveRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ObjectiveDetailViewModel(mObjectiveRepository);
    }
}
