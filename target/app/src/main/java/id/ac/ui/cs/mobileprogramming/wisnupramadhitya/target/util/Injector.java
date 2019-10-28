package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.content.Context;

import androidx.annotation.NonNull;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModelFactory;

public class Injector {

    private static UserRepository getUserRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return UserRepository.getInstance(appDatabase.userDao());
    }

    private static ProjectRepository getProjectRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return ProjectRepository.getInstance(appDatabase.projectDao());
    }

    public static ObjectivesViewModelFactory provideObjectivesViewModelFactory(@NonNull Context context) {
        ProjectRepository projectRepository = getProjectRepository(context);
        return new ObjectivesViewModelFactory(projectRepository);
    }
}
