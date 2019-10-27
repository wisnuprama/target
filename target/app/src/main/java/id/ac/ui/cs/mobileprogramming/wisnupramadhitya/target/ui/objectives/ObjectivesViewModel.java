package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.navigator.OkrNavigator;

public class ObjectivesViewModel extends ViewModel {

    private OkrNavigator mOkrNavigator;

    private ProjectRepository mProjectRepository;

    private int mCurrentProjectId;

    public LiveData<Project> projectLiveData;

    public void onActivityCreated(@NonNull OkrNavigator okrNavigator,
                                  ProjectRepository projectRepository, int currentProjectId) {
        this.mOkrNavigator = okrNavigator;
        this.mProjectRepository = projectRepository;
        this.mCurrentProjectId = currentProjectId;
        projectLiveData = mProjectRepository.getProject(mCurrentProjectId);
    }

    public void onActivityDestroyed() {
        this.mOkrNavigator = null;
    }

    public void onBottomAppBarMenuItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.objectives_bottom_app_bar_about:
                mOkrNavigator.startAbout();
                break;
            case R.id.objectives_bottom_app_bar_settings:
                mOkrNavigator.startSettings();
                break;
            case R.id.objectives_bottom_app_bar_learn_okr:
                mOkrNavigator.startLearnOkr();
                break;
            case android.R.id.home:
                mOkrNavigator.showUserProjects();
                break;
            case R.id.objectives_bottom_app_bar_search:
                mOkrNavigator.showSearchProjects();
                break;
            default:
        }
    }

    public void onFabAddObjectiveClicked(View view) {
        mOkrNavigator.showAddObjective();
    }
}
