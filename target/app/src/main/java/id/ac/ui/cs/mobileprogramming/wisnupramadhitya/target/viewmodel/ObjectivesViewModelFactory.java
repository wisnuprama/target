package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;

public class ObjectivesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepository mProjectRepository;
    private ObjectiveRepository mObjectiveRepository;

    public ObjectivesViewModelFactory(ProjectRepository projectRepository, ObjectiveRepository objectiveRepository) {
        mProjectRepository = projectRepository;
        mObjectiveRepository = objectiveRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ObjectivesViewModel(mProjectRepository, mObjectiveRepository);
    }
}
