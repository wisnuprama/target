package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.AppDatabase;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.remote.RecentInfoRemoteDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ObjectiveRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.ProjectRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.RecentInfoRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.UserRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.volley.WebServiceRequestQueue;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectiveDetailViewModelFactory;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModelFactory;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.RecentInfoViewModelFactory;

/**
 * Dependency injection
 */
public class Injector {

    private Injector() {
    }

    private static UserRepository getUserRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return UserRepository.getInstance(appDatabase.userDao());
    }

    private static ProjectRepository getProjectRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return ProjectRepository.getInstance(appDatabase.projectDao());
    }

    private static ObjectiveRepository getObjectiveRepository(@NonNull Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return ObjectiveRepository.getInstance(appDatabase.objectiveDao());
    }

    private static RecentInfoRepository getRecentInfoRepository(@NonNull Context context) {
        RecentInfoRemoteDataSource dataSource = RecentInfoRemoteDataSource
                .getInstance(WebServiceRequestQueue.getInstance(context));
        return RecentInfoRepository.getInstance(dataSource);
    }

    public static ObjectivesViewModelFactory provideObjectivesViewModelFactory(@NonNull Context context) {
        ProjectRepository projectRepository = getProjectRepository(context);
        ObjectiveRepository objectiveRepository = getObjectiveRepository(context);
        return new ObjectivesViewModelFactory(projectRepository, objectiveRepository);
    }

    public static ObjectiveDetailViewModelFactory provideAddObjectiveViewModelFactory(@NonNull Context context) {
        ObjectiveRepository objectiveRepository = getObjectiveRepository(context);
        return new ObjectiveDetailViewModelFactory(objectiveRepository);
    }

    public static RecentInfoViewModelFactory provideRecentInfoViewModelFactory(@NonNull Context context) {
        return new RecentInfoViewModelFactory(getRecentInfoRepository(context));
    }


    public static ConnectivityManager.NetworkCallback provideInternetConnectivityNetworkCallback(@NonNull Activity activity) {
        return new InternetConnNetworkCallback(activity);
    }
}
