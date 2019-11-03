package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Objective;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;

@Dao
public interface ObjectiveDao {

    @Insert
    void insertAll(Objective... objectives);

    @Query("SELECT * FROM objective")
    LiveData<List<Objective>> findAllObjectives();

    @Query("SELECT * FROM objective WHERE owner_id = :userId")
    LiveData<List<Objective>> findObjectivesByUserId(String userId);

    @Query("SELECT * FROM objective WHERE project_id = :projectId")
    LiveData<List<Objective>> findObjectivesByProjectId(Integer projectId);

    @Query("SELECT * FROM objective WHERE id = :objectiveId")
    LiveData<Project> findObjectiveById(Integer objectiveId);

    @Transaction
    @Query("SELECT * FROM objective WHERE project_id = :projectId ORDER BY date_created DESC")
    LiveData<List<ObjectiveWithKeyResults>> findObjectiveWithKeyResultsByProjectId(Integer projectId);

    @Transaction
    @Query("SELECT * FROM objective WHERE owner_id = :userId ORDER BY date_created DESC")
    List<ObjectiveWithKeyResults> getObjectiveWithKeyResultsByUserId(String userId);
}
