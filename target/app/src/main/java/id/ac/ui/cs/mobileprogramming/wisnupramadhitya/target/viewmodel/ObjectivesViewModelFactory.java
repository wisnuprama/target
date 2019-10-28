package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;

public class ObjectivesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepository mProjectRepository;

    public ObjectivesViewModelFactory(ProjectRepository projectRepository) {
        mProjectRepository = projectRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ObjectivesViewModel(mProjectRepository);
    }
}
