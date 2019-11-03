package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.ProjectDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.ProjectDao;

public class ProjectRepository implements ProjectDataSource {

    private ProjectDao mProjectDao;

    private static ProjectRepository instance;

    private ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }


    @Override
    public LiveData<List<Project>> getProjects() {
        return mProjectDao.findAllProjects();
    }

    @Override
    public LiveData<List<Project>> getUserProjects(String userId) {
        return mProjectDao.findProjectsByUserId(userId);
    }

    @Override
    public LiveData<Project> getProject(Integer projectId) {
        return mProjectDao.findProjectById(projectId);
    }

    public static synchronized ProjectRepository getInstance(@NonNull ProjectDao projectDao) {
        if(instance == null) {
            instance = new ProjectRepository(projectDao);
        }
        return instance;
    }
}
