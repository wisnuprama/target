package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.content.Context;

import androidx.annotation.NonNull;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.UserRepository;

public class Injector {

    public static UserRepository getUserRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return UserRepository.getInstance(appDatabase.userDao());
    }

    public static ProjectRepository getProjectRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return ProjectRepository.getInstance(appDatabase.projectDao());
    }
}
