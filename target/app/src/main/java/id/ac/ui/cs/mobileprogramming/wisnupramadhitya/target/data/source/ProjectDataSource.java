package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;

public interface ProjectDataSource {
    LiveData<List<Project>> getProjects();

    LiveData<List<Project>> getUserProjects(String userId);

    LiveData<Project> getProject(Integer projectId);
}
