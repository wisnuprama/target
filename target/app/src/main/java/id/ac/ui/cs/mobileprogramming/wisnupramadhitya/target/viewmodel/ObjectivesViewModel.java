package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel;

import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;

public class ObjectivesViewModel extends ViewModel {

    private ProjectRepository mProjectRepository;

    private ObjectiveRepository mObjectiveRepository;

    public final ObservableInt selectedProjectId = new ObservableInt();

    public LiveData<Project> projectLiveData;

    private MediatorLiveData<List<ObjectiveWithKeyResults>> mObjectiveLiveData = new MediatorLiveData<>();

    public ObjectivesViewModel(ProjectRepository projectRepository, ObjectiveRepository objectiveRepository) {
        mProjectRepository = projectRepository;
        mObjectiveRepository = objectiveRepository;
    }

    public void onActivityCreated() {
        selectedProjectId.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                int projectId = selectedProjectId.get();
                projectLiveData = mProjectRepository.getProject(projectId);
                LiveData<List<ObjectiveWithKeyResults>> objectiveList = mObjectiveRepository.getProjectObjectives(projectId);
                mObjectiveLiveData.addSource(objectiveList, objectives -> mObjectiveLiveData.setValue(objectives));
            }
        });
    }

    public MediatorLiveData<List<ObjectiveWithKeyResults>> getObjectiveLiveData() {
        return mObjectiveLiveData;
    }
}
