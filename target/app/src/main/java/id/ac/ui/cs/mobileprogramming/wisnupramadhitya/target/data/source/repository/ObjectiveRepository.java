package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.ObjectiveDao;

public class ObjectiveRepository implements ObjectiveDataSource {

    private static ObjectiveRepository instance;

    private ObjectiveDao mObjectiveDao;

    public ObjectiveRepository(ObjectiveDao objectiveDao) {
        mObjectiveDao = objectiveDao;
    }

    @Override
    public void createObjective(@NonNull String userId, @NonNull Integer projectId,
                                @NonNull String title, String rational, OffsetDateTime deadline) {
        AsyncTask.execute(() -> {
            Objective objective = new Objective(projectId, userId, title, rational, deadline);
            mObjectiveDao.insertAll(objective);
        });
    }

    @Override
    public LiveData<List<ObjectiveWithKeyResults>> getProjectObjectives(Integer projectId) {
        return mObjectiveDao.getObjectiveWithKeyResultsByProjectId(projectId);
    }

    public static synchronized ObjectiveRepository getInstance(@NonNull ObjectiveDao objectiveDao) {
        if(instance == null) {
            instance = new ObjectiveRepository(objectiveDao);
        }
        return instance;
    }
}