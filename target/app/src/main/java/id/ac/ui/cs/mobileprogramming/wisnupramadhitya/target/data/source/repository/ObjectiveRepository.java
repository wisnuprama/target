package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.time.OffsetDateTime;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.ObjectiveDataSource;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao.ObjectiveDao;

public class ObjectiveRepository implements ObjectiveDataSource {

    private static ObjectiveRepository instance;

    private ObjectiveDao mObjectiveDao;

    public ObjectiveRepository(ObjectiveDao objectiveDao) {
        mObjectiveDao = objectiveDao;
    }

    @Override
    public void createObjective(@NonNull String userId, @NonNull Integer projectId,
                                String title, String rational, OffsetDateTime deadline) {
        AsyncTask.execute(() -> {
            Objective objective = new Objective(projectId, userId, title, rational, deadline);
            mObjectiveDao.insertObjective(objective);
        });
    }

    @Override
    public void updateObjective(@NonNull Objective objective) {
        AsyncTask.execute(() -> {
            Objective oldObjective = mObjectiveDao.getObjective(objective.getId());
            objective.setDateCreated(oldObjective.getDateCreated());
            mObjectiveDao.updateObjective(objective);
        });
    }

    @Override
    public void deleteObjective(@NonNull Integer objectiveId) {
        AsyncTask.execute(() -> mObjectiveDao.deleteObjective(objectiveId));
    }

    @Override
    public LiveData<ObjectiveWithKeyResults> getObjective(@NonNull Integer objectiveId) {
        return mObjectiveDao.findObjectiveById(objectiveId);
    }

    @Override
    public LiveData<List<ObjectiveWithKeyResults>> getProjectObjectives(Integer projectId) {
        return mObjectiveDao.findObjectiveWithKeyResultsByProjectId(projectId);
    }

    public static synchronized ObjectiveRepository getInstance(@NonNull ObjectiveDao objectiveDao) {
        if(instance == null) {
            instance = new ObjectiveRepository(objectiveDao);
        }
        return instance;
    }
}
